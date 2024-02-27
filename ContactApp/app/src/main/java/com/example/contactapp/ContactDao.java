package com.example.contactapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM Contact")
    List<Contact> getAll();
    @Insert
    void insertAll(Contact... contacts);

    @Query("SELECT * FROM Contact WHERE name LIKE :searchQuery || '%'")
    List<Contact> searchContacts(String searchQuery);
}
