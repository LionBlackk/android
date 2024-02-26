package com.example.contactapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.example.contactapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<Contact> contactList;
    private ContactAdapter contactAdapter;
    private AppDatabase db;
    private ContactDao contactDao;
    private static final int REQUEST_CODE_ADD_CONTACT = 1; // Định nghĩa một request code

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
                startActivityForResult(intent, 1);
            }
        });

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
}
