package com.example.contactapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.jetbrains.annotations.Async;

public class DetailContactActivity extends AppCompatActivity {
    private AppDatabase db;
    private ContactDao contactDao;
    private TextView tvTitle;
    private TextView tvName;
    private TextView tvPhoneNumber;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);
        Intent receivedIntent = getIntent();
        tvTitle = findViewById(R.id.tv_title);
        tvName = findViewById(R.id.tv_name);
        tvPhoneNumber = findViewById(R.id.tv_phone_number);
        if(receivedIntent != null) {
            id = receivedIntent.getStringExtra("id");
            loadDetailContact(id);
        }

        ImageButton btnEdit = findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailContactActivity.this, EditContactActivity.class);
                intent.putExtra("id", id);
                startActivityForResult(intent, 1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            loadDetailContact(id);
        }
    }
    private void loadDetailContact(String id){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                db = AppDatabase.getInstance(getApplicationContext());
                contactDao = db.contactDao();
                Contact contact = contactDao.searchContact(id);
                tvTitle.setText(contact.getName());
                tvName.setText(contact.getName());
                tvPhoneNumber.setText(contact.getPhoneNumber());
            }
        });
    }

}