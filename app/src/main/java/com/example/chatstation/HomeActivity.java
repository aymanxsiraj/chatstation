package com.example.chatstation;


import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatstation.databinding.ActivityHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationView;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String img_url_edit;
    private String phone_number_edit;
    private String user_name_edit;
    private String bio_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            startService(new Intent(getBaseContext(),MyService.class));

            FirebaseAuth auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();
            reference = FirebaseDatabase
                    .getInstance("https://chat-station-8d5a0-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference().child("users").child(user.getUid());

            FirebaseMessaging.getInstance().subscribeToTopic(user.getUid());





            ActivityHomeBinding binding = ActivityHomeBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            setSupportActionBar(binding.appBarHome.toolbar);
            binding.appBarHome.fap.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this,UserActivity.class);
                startActivity(intent);
            });
            DrawerLayout drawer = binding.drawerLayout;
            navigationView = binding.navView;
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                    .setOpenableLayout(drawer)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);


            //set user state
            updateUserStatus("online");

            // get user phone number from firebase
            View header = navigationView.getHeaderView(0);
            TextView UPN = header.findViewById(R.id.UPN);
            UPN.setText(getCurrentUser());

            // get user name from database
            getUserDisplayName();

            // get user profile image
            getProfileImageUrl();

            // get user phone number
            getPhoneNumber();

            // get user bio
            getUserBio();



            //on click img view in nav header
            ImageView imageView = header.findViewById(R.id.profile_img);
            imageView.setOnClickListener(v -> {
             Intent intent = new Intent(HomeActivity.this,EditActivity.class);
             intent.putExtra("url",img_url_edit);
             intent.putExtra("name",user_name_edit);
             intent.putExtra("bio",bio_edit);
             intent.putExtra("number",phone_number_edit);
             startActivity(intent);
            });

        }
        catch (Exception exception){
            Toast.makeText(getApplicationContext(),exception.getMessage(),Toast.LENGTH_LONG).show();
        }


    }

    // this
    private String getCurrentUser(){

        if(user != null){
            return user.getPhoneNumber();
        }
        return "null";
    }

    private void updateUserStatus(String state){
        if(user != null){

            Map<String,Object> userState = new HashMap<>();
            userState.put("state", state);

            reference.child("state").updateChildren(userState);
        }
        else {
            Toast.makeText(getBaseContext(),"0024",Toast.LENGTH_LONG).show();
        }
    }


    public void getPhoneNumber(){
        if(user!=null){
            phone_number_edit = user.getPhoneNumber();
        }
    }


    private void getUserBio() {

        if (user != null) {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    bio_edit = Objects.requireNonNull(snapshot.child("Bio").getValue()).toString();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }

    // this
    private void getUserDisplayName() {

        if (user != null) {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String USER_NAME = Objects.requireNonNull(snapshot.child("username").getValue()).toString();
                    user_name_edit = USER_NAME;
                    // get user name from firebase
                    View header = navigationView.getHeaderView(0);
                    TextView UND = header.findViewById(R.id.UND);
                    UND.setText(USER_NAME);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }


    // this
    private void getProfileImageUrl(){
        if(user != null){
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String URL = Objects.requireNonNull(snapshot.child("photoUrl").getValue()).toString();
                    img_url_edit = URL;
                    // get profile img


                    if(!URL.isEmpty()){
                        View header = navigationView.getHeaderView(0);
                        ImageView profilePhoto = header.findViewById(R.id.profile_img);
                        Picasso.get().load(URL).into(profilePhoto);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }
    }


    public void openDialog(){
        AlertDialogProvider provider = new AlertDialogProvider();
        provider.show(getSupportFragmentManager(),"alert dialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //return super.onOptionsItemSelected(item);

      if(item.getItemId() == R.id.action_logout){
          openDialog();
          return true;
      }
      else if(item.getItemId() == R.id.action_about){
          Toast.makeText(getBaseContext(),"action about",Toast.LENGTH_LONG).show();
          return true;
      }
      return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateUserStatus("online");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        updateUserStatus("offline");
    }


}