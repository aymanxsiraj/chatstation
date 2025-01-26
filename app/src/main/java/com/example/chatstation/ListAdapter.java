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

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.Holder> {
    private final Context context;
    private final ArrayList<ChatList> list;
    private onUserClickListener listener;

    public ListAdapter(Context context,ArrayList<ChatList> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_adapter_items,parent,false);
        return new Holder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        ChatList chatList = list.get(position);
        holder.name.setText(chatList.name);
        holder.lastMsg.setText(chatList.lastMsg);
        holder.time.setText(chatList.time);
        Picasso.get().load(chatList.url).into(holder.image);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{
        public TextView name;
        public ImageView image;
        public TextView lastMsg;
        public TextView time;
        public Holder(@NonNull View itemView,onUserClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.person_name);
            image = itemView.findViewById(R.id.person_img);
            lastMsg = itemView.findViewById(R.id.person_number);
            time = itemView.findViewById(R.id.c_time);
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
