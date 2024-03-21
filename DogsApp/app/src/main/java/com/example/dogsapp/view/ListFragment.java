package com.example.dogsapp.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.dogsapp.R;
import com.example.dogsapp.model.DogBreed;
import com.example.dogsapp.viewmodel.DogsAdapter;
import com.example.dogsapp.viewmodel.DogsApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ListFragment extends Fragment {
    private DogsApiService dogsApiService;
    private DogsAdapter dogsAdapter;
    private ArrayList<DogBreed> dogList;
    private RecyclerView rvDogs;
    private Toolbar toolbar;
    public ListFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    @SuppressLint("CheckResult")
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.my_toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        dogList = new ArrayList<DogBreed>();
        dogsAdapter = new DogsAdapter(dogList);
        rvDogs = view.findViewById(R.id.rv_dogs);
        rvDogs.setAdapter(dogsAdapter);
        rvDogs.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        dogsApiService = new DogsApiService();
        dogsApiService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<DogBreed> dogBreeds) {
                        for(DogBreed dog : dogBreeds) {
                            dogList.add(dog);
                            dogsAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("DEBUG", "ERROR: " + e.getMessage());
                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mi_search) {
            SearchView searchView = (SearchView) item.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
                @Override
                public boolean onQueryTextSubmit(String query) {
                    dogsAdapter.getFilter().filter(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    dogsAdapter.getFilter().filter(newText);
                    return false;
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}