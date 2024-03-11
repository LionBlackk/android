package com.example.contactapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.contactapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<Contact> contactList;
    private ContactAdapter contactAdapter;
    private AppDatabase db;
    private ContactDao contactDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.rvContact.setLayoutManager(new LinearLayoutManager(this));

        contactList = new ArrayList<Contact>();
        contactAdapter = new ContactAdapter(contactList);
        binding.rvContact.setAdapter(contactAdapter);
        contactAdapter.notifyDataSetChanged();

        loadContacts();

        binding.btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                startActivityIfNeeded(intent, 1);
            }
        });

        Toolbar toolbar = binding.mainToolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.rvContact.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), binding.rvContact ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this, DetailContactActivity.class);
                        intent.putExtra("id", String.valueOf(contactList.get(position).getId()));
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

    }

    private void loadContacts(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                db = AppDatabase.getInstance(getApplicationContext());
                contactDao = db.contactDao();
                final ArrayList<Contact> loadedContacts = new ArrayList<>();
                loadedContacts.addAll(contactDao.getAll());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        contactList.clear();
                        contactList.addAll(loadedContacts);
                        contactAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            contactList.clear();
            loadContacts();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d("item.getItemId()", String.valueOf(item.getItemId()));
        Log.d("item.getItemId()", String.valueOf(R.id.action_search));
        if (item.getItemId() == R.id.action_search) {
            // Hiển thị ô tìm kiếm
            SearchView searchView = (SearchView) item.getActionView();
            Log.d("searchView", searchView.toString());
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // Xử lý khi người dùng nhấn enter hoặc nút tìm kiếm
                    searchContacts(query);
                    Log.d("query:", query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // Xử lý khi người dùng thay đổi văn bản trong ô tìm kiếm
                    searchContacts(newText);
                    Log.d("newText:", newText);
                    return true;
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    private void searchContacts(final String query) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                db = AppDatabase.getInstance(getApplicationContext());
                contactDao = db.contactDao();
                final ArrayList<Contact> searchResults = new ArrayList<>();
                searchResults.addAll(contactDao.searchContacts(query));
                Log.d("searchResults: ", String.valueOf(searchResults.size()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        contactList.clear();
                        contactList.addAll(searchResults);
                        contactAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

}
