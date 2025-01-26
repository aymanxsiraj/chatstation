package com.example.chatstation;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageHolder extends RecyclerView.ViewHolder {
    public TextView user_1;
    public TextView user_2;
    public TextView user_1_time;
    public TextView user_2_time;
    public MessageHolder(@NonNull View itemView) {
        super(itemView);
        user_1 = itemView.findViewById(R.id.user_1);
        user_2 = itemView.findViewById(R.id.user_2);
        user_1_time = itemView.findViewById(R.id.user_1_time);
        user_2_time = itemView.findViewById(R.id.user_2_time);
    }
}
