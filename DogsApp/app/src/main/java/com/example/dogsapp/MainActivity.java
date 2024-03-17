package com.example.dogsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.dogsapp.model.DogBreed;
import com.example.dogsapp.viewmodel.DogsApiService;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private DogsApiService dogsApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dogsApiService = new DogsApiService();
        dogsApiService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                    @Override
                    public void onSuccess(@NonNull List<DogBreed> dogBreeds) {
                        Log.d("DEBUG", "SUCCESS");
                        for(DogBreed dogBreed : dogBreeds){
                            Log.d("DEBUG", "Name Dog: " + dogBreed.getName());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("DEBUG", "ERROR: " + e.getMessage());
                    }
                });
    }
}