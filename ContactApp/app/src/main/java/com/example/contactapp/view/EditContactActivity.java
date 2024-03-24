package com.example.contactapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.contactapp.databinding.ActivityEditContactBinding;
import com.example.contactapp.model.AppDatabase;
import com.example.contactapp.model.Contact;
import com.example.contactapp.model.ContactDao;
import com.example.contactapp.R;
import com.example.contactapp.viewmodel.ContactViewModel;

import java.util.concurrent.Executors;

public class EditContactActivity extends AppCompatActivity {
    private ActivityEditContactBinding binding;
    private Contact contact;
    private ContactViewModel contactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent receivedIntent = getIntent();
        contact = receivedIntent.getParcelableExtra("contact");

        contactViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ContactViewModel.class);
        contactViewModel.searchContact(String.valueOf(contact.getId())).observe(EditContactActivity.this, new Observer<Contact>() {
            @Override
            public void onChanged(Contact contact) {
                if(contact != null) {
                    binding.setContact(contact);
                }
            }
        });

        setSupportActionBar(binding.editToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnEditContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contact.setName(binding.etName.getText().toString());
                contact.setEmail(binding.etEmail.getText().toString());
                contact.setPhoneNumber(binding.etPhoneNumber.getText().toString());
                contactViewModel.updateContact(contact);
                finish();
            }
        });
    }
}