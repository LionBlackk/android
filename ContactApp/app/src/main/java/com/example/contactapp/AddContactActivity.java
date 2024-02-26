package com.example.contactapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddContactActivity extends AppCompatActivity {
    private AppDatabase db;
    private ContactDao contactDao;
    private EditText etName;
    private EditText etPhoneNumber;
    private EditText etEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);



        Toolbar toolbar = findViewById(R.id.toolbar);
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

                    etName = findViewById(R.id.et_name);
                    etPhoneNumber = findViewById(R.id.et_phone_number);
                    etEmail = findViewById(R.id.et_email);



                    contactDao.insertAll(new Contact(etName.getText().toString(), etPhoneNumber.getText().toString(), etEmail.getText().toString()));

                    Log.d("etName", etName.getText().toString());
                    Log.d("etPhoneNumber", etPhoneNumber.getText().toString());
                    Log.d("etEmail", etEmail.getText().toString());
                    Intent intent = getIntent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        return super.onOptionsItemSelected(item);
    }
}
