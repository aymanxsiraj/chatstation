package com.example.chatstation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ImgActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);
        Objects.requireNonNull(getSupportActionBar()).hide();

        String url = getIntent().getStringExtra("url");
        ImageView imageView = findViewById(R.id.show_profile_img);
        Picasso.get().load(url).into(imageView);
    }
}