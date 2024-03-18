package com.example.dogsapp.view;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dogsapp.R;
import com.example.dogsapp.databinding.FragmentDetailsBinding;
import com.example.dogsapp.model.DogBreed;

public class DetailsFragment extends Fragment {
    private DogBreed dogBreed;
    private FragmentDetailsBinding binding;
    public DetailsFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dogBreed = (DogBreed) getArguments().getSerializable("selected_dog");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);
        binding.setDog(dogBreed);
        View viewRoot = binding.getRoot();
        return viewRoot;
    }
}