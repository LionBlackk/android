package com.example.contactapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class EditContactActivity extends AppCompatActivity {
    private AppDatabase db;
    private ContactDao contactDao;
    private EditText etName;
    private EditText etPhoneNumber;
    private EditText etEmail;
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        etName = findViewById(R.id.et_name);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        etEmail = findViewById(R.id.et_email);

        Intent intent = getIntent();
        String contactId = intent.getStringExtra("id");

        loadContactData(contactId);

        Toolbar toolbar = findViewById(R.id.edit_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageView btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                db = AppDatabase.getInstance(getApplicationContext());
                contactDao = db.contactDao();
                int id = item.getItemId();
                if (id == R.id.action_save) {

                    contact.setName(etName.getText().toString());
                    contact.setEmail(etEmail.getText().toString());
                    contact.setPhoneNumber(etPhoneNumber.getText().toString());
                    contactDao.update(contact);
                    Intent intent = getIntent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        return super.onOptionsItemSelected(item);
    }

    private void loadContactData(String contactId){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                db = AppDatabase.getInstance(getApplicationContext());
                contactDao = db.contactDao();
                contact = contactDao.searchContact(contactId);
                etName.setText(contact.getName());
                etEmail.setText(contact.getEmail());
                etPhoneNumber.setText(contact.getPhoneNumber());
            }
        });
    }

}