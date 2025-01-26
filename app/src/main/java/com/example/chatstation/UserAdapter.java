package com.example.chatstation;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Holder> {
    private final Context context;
    private final ArrayList<User> users;
    private onUserClickListener listener;

    public UserAdapter(Context context,ArrayList<User> users){
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.find_new_user_layout,parent,false);
        return new Holder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        User user = users.get(position);
        holder.name.setText(user.username);
        holder.number.setText(user.phone);
        Picasso.get().load(user.photoUrl).into(holder.image);
    }




    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView number;
        public ImageView image;
        public Holder(@NonNull View itemView,onUserClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.find_person_name);
            number = itemView.findViewById(R.id.find_person_number);
            image = itemView.findViewById(R.id.find_person_img);

            itemView.setOnClickListener(v -> {
                if(listener != null){
                    int position = getAbsoluteAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onUserClick(position);
                    }
                }
            });
        }
    }

    public interface onUserClickListener{
        void onUserClick(int position);
    }

    public void setOnUserClickListener(onUserClickListener listener){
        this.listener = listener;
    }
}
