package com.example.contactapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private String phoneNumber;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private String email;

    public Contact(String name, String phoneNumber, String email) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.email = email;
    }
    protected Contact(Parcel in) {
        id = in.readInt();
        phoneNumber = in.readString();
        name = in.readString();
        email = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.name);
        dest.writeString(this.email);
    }
    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
