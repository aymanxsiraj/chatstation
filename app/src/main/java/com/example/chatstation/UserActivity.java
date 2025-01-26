package com.example.chatstation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class UserActivity extends AppCompatActivity {

    private UserAdapter userAdapter;
    private ArrayList<User> users;
    private RecyclerView recyclerView;
    private FirebaseUser user;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Objects.requireNonNull(getSupportActionBar()).setTitle("search");



        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        imageView = findViewById(R.id.imageView);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        users = new ArrayList<>();


    }

    public void filter(String s){
        if(user != null){
            DatabaseReference mDatabase = FirebaseDatabase
                    .getInstance("https://chat-station-8d5a0-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference().child("users");


            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    users.clear();
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        User user1 = dataSnapshot.getValue(User.class);

                        assert user1 != null;
                        if(!user1.phone.equals(user.getPhoneNumber()) && user1.username.toLowerCase().contains(s.toLowerCase())){
                            users.add(user1);
                        }

                    }
                    userAdapter = new UserAdapter(getBaseContext(), users);
                    recyclerView.setAdapter(userAdapter);
                    imageView.setVisibility(View.GONE);
                    userAdapter.setOnUserClickListener(position -> {
                        String name = users.get(position).username;
                        String url = users.get(position).photoUrl;
                        String bio = users.get(position).Bio;
                        String phone = users.get(position).phone;
                        String uid = users.get(position).UID;
                        Intent intent = new Intent(UserActivity.this, ChatActivity.class);
                        intent.putExtra("name",name);
                        intent.putExtra("url",url);
                        intent.putExtra("bio",bio);
                        intent.putExtra("phone",phone);
                        intent.putExtra("uid",uid);
                        startActivity(intent);
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getBaseContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.users, menu);

        MenuItem item = menu.findItem(R.id.app_bar_search_users);

        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }

        });


        searchView.setOnCloseListener(() -> {
            users.clear();
            imageView.setVisibility(View.VISIBLE);
            return false;
        });





        return super.onCreateOptionsMenu(menu);
    }


}