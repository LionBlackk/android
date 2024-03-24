package com.example.contactapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.contactapp.databinding.ActivityAddContactBinding;
import com.example.contactapp.model.AppDatabase;
import com.example.contactapp.model.Contact;
import com.example.contactapp.model.ContactDao;
import com.example.contactapp.R;
import com.example.contactapp.viewmodel.ContactViewModel;

public class AddContactActivity extends AppCompatActivity {
    private ActivityAddContactBinding binding;
    private ContactViewModel contactViewModel;
    private Contact contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        contactViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ContactViewModel.class);

        setSupportActionBar(binding.addActivityToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
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
        int id = item.getItemId();
        if (id == R.id.action_save) {
            if(PhoneNumberUtils.isGlobalPhoneNumber(binding.etPhoneNumber.getText().toString())){
                contactViewModel.insertContact(new Contact(binding.etName.getText().toString(), binding.etPhoneNumber.getText().toString(), binding.etEmail.getText().toString()));
                finish();
            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(AddContactActivity.this).create();
                alertDialog.setTitle("Lỗi nhập thông tin");
                alertDialog.setMessage("Số điện thoại không hợp lệ");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        (dialog, which) -> dialog.dismiss());
                alertDialog.show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
