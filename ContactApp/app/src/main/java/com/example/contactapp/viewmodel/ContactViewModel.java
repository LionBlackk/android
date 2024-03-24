package com.example.contactapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.contactapp.model.Contact;
import com.example.contactapp.repository.AppRepo;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private AppRepo appRepo;
    public ContactViewModel(@NonNull Application application) {
        super(application);
        appRepo = new AppRepo(application);
    }
    public LiveData<List<Contact>> getAllContacts(){
        return appRepo.getAllContacts();
    }
    public void insertContact(Contact contact){
        appRepo.insertContact(contact);
    }
    public void updateContact(Contact contact){
        appRepo.updateContact(contact);
    }
    public LiveData<Contact> searchContact(String id){
        return appRepo.searchContact(id);
    }
    public LiveData<List<Contact>> searchContacts(String query){
        return appRepo.searchContacts(query);
    }
}
