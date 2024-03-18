package com.example.dogsapp.viewmodel;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogsapp.R;
import com.example.dogsapp.databinding.DogItemBinding;
import com.example.dogsapp.model.DogBreed;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.DogViewHolder>{
    private ArrayList<DogBreed> dogList;
    private Context context;
    public DogsAdapter(ArrayList<DogBreed> dogList) {
        this.dogList = dogList;
    }
    @Override
    public DogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        DogItemBinding binding = DogItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DogViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(DogViewHolder holder, int position) {
        holder.binding.setDog(dogList.get(position));
        Picasso.with(context).load(dogList.get(position).getUrl()).into(holder.binding.ivDog);
    }

    @Override
    public int getItemCount() {
        return dogList.size();
    }

    public class DogViewHolder extends RecyclerView.ViewHolder {
        private DogItemBinding binding;
        public DogViewHolder(DogItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.ivDog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DogBreed selectedDog = dogList.get(getAdapterPosition());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("selected_dog", selectedDog);
                    Navigation.findNavController(v).navigate(R.id.detailsFragment, bundle);
                }
            });
        }
    }
}
