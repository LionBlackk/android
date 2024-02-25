package com.example.contactapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

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

        contactList.add(new Contact("Nguyen Van A", "123456789", "a@gmail.com"));
        contactAdapter.notifyDataSetChanged();
        insertContactAndLoadData();
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                db = AppDatabase.getInstance(getApplicationContext());
//                contactDao = db.contactDao();
//                contactDao.insertAll(new Contact("Vo Phuoc Hoang", "123456789", "vophuochoang93@gmail.com"));
//
//                contactList = new ArrayList<Contact>(contactDao.getAll());
//                contactAdapter.notifyDataSetChanged();
//            }
//        });


    }
    private void insertContactAndLoadData() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                db = AppDatabase.getInstance(getApplicationContext());
                contactDao = db.contactDao();
                contactDao.insertAll(new Contact("Vo Phuoc Hoang", "123456789", "vophuochoang93@gmail.com"));
                loadContacts();
            }
        });
    }

    private void loadContacts() {
        contactList.clear();
        contactList.addAll(contactDao.getAll());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                contactAdapter.notifyDataSetChanged();
            }
        });
    }
}
