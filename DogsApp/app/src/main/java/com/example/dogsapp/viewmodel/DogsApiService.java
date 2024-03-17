package com.example.dogsapp.viewmodel;

import com.example.dogsapp.model.DogBreed;
import com.example.dogsapp.model.DogsApi;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DogsApiService {
    public static final String BASE_URL = "https://raw.githubusercontent.com/";
    private DogsApi api;
    public DogsApiService(){
        if(api == null){
            api = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build()
                    .create(DogsApi.class);
        }
    }
    public Single<List<DogBreed>> getDogs(){
        return api.getDogs();
    }
}
