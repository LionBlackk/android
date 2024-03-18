package com.example.dogsapp.viewmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dogsapp.R;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dog_item, parent, false);
        context = parent.getContext();
        return new DogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DogViewHolder holder, int position) {
        holder.getTvName().setText(dogList.get(position).getName());
        holder.getTvOrigin().setText(dogList.get(position).getOrigin());
        Picasso.with(context).load(dogList.get(position).getUrl()).into(holder.getIvDog());
    }

    @Override
    public int getItemCount() {
        return dogList.size();
    }

    public class DogViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvOrigin;
        private final ImageView ivDog;
        public DogViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvOrigin = itemView.findViewById(R.id.tv_origin);
            ivDog = itemView.findViewById(R.id.iv_dog);
        }
        public TextView getTvName() {
            return tvName;
        }
        public TextView getTvOrigin() {
            return tvOrigin;
        }
        public ImageView getIvDog() {
            return ivDog;
        }
    }
}
