package com.example.dogsapp.viewmodel;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogsapp.R;
import com.example.dogsapp.databinding.DogItemBinding;
import com.example.dogsapp.model.DogBreed;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.DogViewHolder> implements Filterable {
    private ArrayList<DogBreed> dogList;
    private ArrayList<DogBreed> dogListCopy;
    private Context context;
    public DogsAdapter(ArrayList<DogBreed> dogBreedList) {
        this.dogList = dogBreedList;
        this.dogListCopy = dogBreedList;
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
        if (dogList.get(position).isFav()) {
            holder.binding.ivFav.setImageResource(R.drawable.clicked_heart);
        } else {
            holder.binding.ivFav.setImageResource(R.drawable.heart);
        }
    }

    @Override
    public int getItemCount() {
        return dogList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String input = constraint.toString().toLowerCase();
                List<DogBreed> filteredList = new ArrayList<DogBreed>();
                if(input.isEmpty()){
                    filteredList.addAll(dogListCopy);
                }else{
                    for(DogBreed dog : dogListCopy){
                        if(dog.getName().toLowerCase().contains(input)){
                            filteredList.add(dog);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dogList = new ArrayList<DogBreed>();
                dogList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
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

            binding.ivFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DogBreed selectedDog = dogList.get(getAdapterPosition());
                    selectedDog.setFav(!selectedDog.isFav());
                    if (selectedDog.isFav()) {
                        binding.ivFav.setImageResource(R.drawable.clicked_heart);
                    } else {
                        binding.ivFav.setImageResource(R.drawable.heart);
                    }
                }
            });

            itemView.setOnTouchListener(new OnSwipeTouchListener(){
                @Override
                public void onSwipeLeft() {
                    if(binding.itemLayout1.getVisibility() == View.GONE){
                        binding.itemLayout1.setVisibility(View.VISIBLE);
                        binding.itemLayout2.setVisibility(View.GONE);
                    }
                    else{
                        binding.itemLayout1.setVisibility(View.GONE);
                        binding.itemLayout2.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onSwipeRight() {
                }
            });

        }
    }


    public class OnSwipeTouchListener implements View.OnTouchListener {

        private GestureDetector gestureDetector = new GestureDetector(new GestureListener());

        public boolean onTouch(final View view, final MotionEvent motionEvent) {
            return gestureDetector.onTouchEvent(motionEvent);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            // Determines the fling velocity and then fires the appropriate swipe event accordingly
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                        }
                    } else {
                        if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffY > 0) {
                                onSwipeDown();
                            } else {
                                onSwipeUp();
                            }
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        public void onSwipeRight() {
        }

        public void onSwipeLeft() {
        }

        public void onSwipeUp() {
        }

        public void onSwipeDown() {
        }
    }

}
