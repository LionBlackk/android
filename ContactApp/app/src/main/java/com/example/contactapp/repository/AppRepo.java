package com.example.contactapp.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.contactapp.model.AppDatabase;
import com.example.contactapp.model.Contact;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepo {
    private AppDatabase db;
    private Executor executor = Executors.newSingleThreadExecutor();

    public AppRepo(Context context){
        db = AppDatabase.getInstance(context);
    }
    public LiveData<List<Contact>> getAllContacts(){
        return db.contactDao().getAll();
    }
    public void insertContact(Contact contact){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.contactDao().insertAll(contact);
            }
        });
    }
    public void updateContact(Contact contact){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.contactDao().update(contact);
            }
        });
    }
    public LiveData<Contact> searchContact(String id){
        return db.contactDao().searchContact(id);
    }
    public LiveData<List<Contact>> searchContacts(String query){
        return db.contactDao().searchContacts(query);
    }
}
