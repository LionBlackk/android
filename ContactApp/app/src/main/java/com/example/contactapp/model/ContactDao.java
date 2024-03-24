package com.example.contactapp.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM Contact")
    LiveData<List<Contact>> getAll();
    @Insert
    void insertAll(Contact... contacts);

    @Query("SELECT * FROM Contact WHERE name LIKE '%' || :searchQuery || '%'")
    LiveData<List<Contact>> searchContacts(String searchQuery);

    @Query("SELECT * FROM Contact WHERE id = :id")
    LiveData<Contact> searchContact(String id);

    @Update
    void update(Contact contact);
}
