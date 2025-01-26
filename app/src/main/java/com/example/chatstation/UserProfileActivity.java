package com.example.chatstation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Objects.requireNonNull(getSupportActionBar()).hide();

        TextView textView = findViewById(R.id.userO);
        textView.setText(getIntent().getStringExtra("name"));

        TextView phone = findViewById(R.id.phoneO);
        TextView bio = findViewById(R.id.bioO);

        phone.setText(getIntent().getStringExtra("phone"));
        bio.setText(getIntent().getStringExtra("bio"));

        String url = getIntent().getStringExtra("url");
        ImageView imageView = findViewById(R.id.other_user_img);
        Picasso.get().load(url).into(imageView);

        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this,ImgActivity.class);
            intent.putExtra("url",url);
            startActivity(intent);
        });

    }
}