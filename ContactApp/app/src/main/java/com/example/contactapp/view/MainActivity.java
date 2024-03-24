package com.example.contactapp.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.contactapp.databinding.ActivityMainBinding;
import com.example.contactapp.model.AppDatabase;
import com.example.contactapp.model.Contact;
import com.example.contactapp.viewmodel.ContactAdapter;
import com.example.contactapp.model.ContactDao;
import com.example.contactapp.R;
import com.example.contactapp.viewmodel.ContactViewModel;
import com.example.contactapp.viewmodel.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ContactAdapter contactAdapter;
    private ContactViewModel contactViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.rvContact.setLayoutManager(new LinearLayoutManager(this));
        contactAdapter = new ContactAdapter(new ArrayList<>());
        binding.rvContact.setAdapter(contactAdapter);

        contactViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(MainActivity.this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                if(contacts != null){
                    contactAdapter.setContactList(contacts);
                }
            }
        });

        binding.btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                startActivity(intent);
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
                        intent.putExtra("contact", contactAdapter.getContactList().get(position));
                        startActivity(intent);
                    }

//                    @Override
//                    public void onLongItemClick(View view, int position) {
//                        // do whatever
//                    }
                })
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d("DEBUG", "onOptionsItemSelected: " + item.getItemId());
        if (item.getItemId() == R.id.action_search) {
            SearchView searchView = (SearchView) item.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    contactViewModel.searchContacts(query).observe(MainActivity.this, new Observer<List<Contact>>() {
                        @Override
                        public void onChanged(List<Contact> contacts) {
                            contactAdapter.setContactList(contacts);
                        }
                    });
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    contactViewModel.searchContacts(newText).observe(MainActivity.this, new Observer<List<Contact>>() {
                        @Override
                        public void onChanged(List<Contact> contacts) {
                            contactAdapter.setContactList(contacts);
                        }
                    });
                    return true;
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}
