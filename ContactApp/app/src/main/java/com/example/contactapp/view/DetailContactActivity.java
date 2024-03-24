package com.example.contactapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.contactapp.databinding.ActivityDetailContactBinding;
import com.example.contactapp.model.Contact;
import com.example.contactapp.viewmodel.ContactViewModel;

public class DetailContactActivity extends AppCompatActivity {
    private ActivityDetailContactBinding binding;
    private Contact contact;
    private ContactViewModel contactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent receivedIntent = getIntent();
        contact = receivedIntent.getParcelableExtra("contact");

        contactViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ContactViewModel.class);
        contactViewModel.searchContact(String.valueOf(contact.getId())).observe(this, new Observer<Contact>() {
            @Override
            public void onChanged(Contact contactItem) {
                if(contactItem != null){
                    binding.setContact(contactItem);
                }
            }
        });
        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailContactActivity.this, EditContactActivity.class);
                intent.putExtra("contact", contact);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        contactViewModel.searchContact(String.valueOf(contact.getId())).observe(this, new Observer<Contact>() {
            @Override
            public void onChanged(Contact contact) {
                if(contact != null) {
                    binding.setContact(contact);
                }
            }
        });
    }
}