package com.example.contactapp.viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactapp.databinding.ContactRowItemBinding;
import com.example.contactapp.model.Contact;
import com.example.contactapp.R;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>  {

    private List<Contact> contactList;

    public ContactAdapter(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        private ContactRowItemBinding binding;
        public ContactViewHolder(ContactRowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    @NonNull
    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContactRowItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.contact_row_item, parent, false);
        return new ContactViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactViewHolder holder, int position) {
        holder.binding.setContact(contactList.get(position));
    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }
    public void setContactList(List<Contact> contactList){
        this.contactList = contactList;
        notifyDataSetChanged();
    }
    public List<Contact> getContactList(){
        return contactList;
    }
}
