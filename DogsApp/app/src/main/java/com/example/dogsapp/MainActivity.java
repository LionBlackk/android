package com.example.dogsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.dogsapp.view.ListFragment;
import com.example.dogsapp.viewmodel.DogsApiService;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}