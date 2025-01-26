package com.example.chatstation;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.chatstation.databinding.ActivityProfileBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private ProgressDialog progressDialog;
    private StorageReference storageReference;
    private Uri imageUri;
    private String URL;
    private TextInputEditText user_name;
    private TextInputEditText user_bio;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        mDatabase = FirebaseDatabase
                .getInstance("https://chat-station-8d5a0-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("users");

        reference = FirebaseDatabase
                .getInstance("https://chat-station-8d5a0-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("users").child(user.getUid());

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getUserPhoneNumber());






        user_name = findViewById(R.id.user_name);
        user_bio = findViewById(R.id.bio);



        // select image from the device
        FloatingActionButton fab = binding.fap;


        fab.setOnClickListener( v -> selectImage());

        // get user phone number from firebase
        TextInputEditText user_phone_number = findViewById(R.id.user_phone_number);
        user_phone_number.setText(getUserPhoneNumber());

        testCurrentUser();









        Button save = findViewById(R.id.save_info);
        save.setOnClickListener(v -> {


            if(binding.profilePhoto.getDrawable() == null){
                Snackbar.make(v, "profile photo most required !", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            else {
                if(Objects.requireNonNull(user_name.getText()).length()==0 && Objects.requireNonNull(user_bio.getText()).length() ==0){
                    user_name.setError("user name required !");
                    user_bio.setError("bio required !");
                }
                else if(Objects.requireNonNull(user_name.getText()).length()==0){
                    user_name.setError("user name required !");
                }
                else if(Objects.requireNonNull(user_bio.getText()).length() ==0){
                    user_bio.setError("bio required !");
                }
                else{
                    uploadImage();
                    uploadFullUserInformation();
                }

            }

        });

    }

    // get user phone number from firebase auth
    private String getUserPhoneNumber(){
        if(user != null){
            return user.getPhoneNumber();
        }
        return "null";
    }


    private String getUserId(){
        if(user != null){
            return user.getUid();
        }
        return "null";
    }


    // test current user exists in firebase database or not
    private void testCurrentUser(){
        assert user != null;
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String u_id = dataSnapshot.getKey(); // <- USER_ID TEST

                    assert u_id != null;
                    if(u_id.equals(user.getUid())){
                        Toast.makeText(getBaseContext(),"exists",Toast.LENGTH_LONG).show();
                        getProfileImageUrl();
                        getUserDisplayName();
                        getUserBio();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



// get profile image url that was stored in real time database
    private void getProfileImageUrl(){

        if(user != null){

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    URL = Objects.requireNonNull(snapshot.child("photoUrl").getValue()).toString();

                    // get profile img


                    if(!URL.isEmpty()){
                        Picasso.get().load(URL).into(binding.profilePhoto);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }
    }


    // get user name from real time database
    private void getUserDisplayName(){

        try{

            if(user != null){
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String USER_NAME = Objects.requireNonNull(snapshot.child("username").getValue()).toString();

                        // get user name

                        if(USER_NAME.isEmpty()){
                            Toast.makeText(getBaseContext(),"new user",Toast.LENGTH_LONG).show();
                        }
                        else{
                            TextInputEditText user_name = findViewById(R.id.user_name);
                            user_name.setText(USER_NAME);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        }
        catch (Exception e){
            Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    // get the user bio from database
    private void getUserBio(){

        try{
            if(user != null){
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String USER_BIO = Objects.requireNonNull(snapshot.child("Bio").getValue()).toString();

                        // get user bio

                        if(USER_BIO.isEmpty()){
                            Toast.makeText(getBaseContext(),"new user",Toast.LENGTH_LONG).show();
                        }
                        else{
                            TextInputEditText user_bio = findViewById(R.id.bio);
                            user_bio.setText(USER_BIO);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        }
        catch (Exception e){
            Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

// select image from device
    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityFromChild(this,intent,100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null){

            imageUri = data.getData();
            binding.profilePhoto.setImageURI(imageUri);
        }
    }

    // upload selected image to firebase storage
    @SuppressLint("SetTextI18n")
    private void uploadImage() {

        try{

            storageReference = FirebaseStorage.getInstance().getReference("userProfiles/" + getUserPhoneNumber());

            if(imageUri!=null){
                progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading File....");
                progressDialog.show();

                storageReference.putFile(imageUri)
                        .addOnSuccessListener(taskSnapshot -> {

                            binding.profilePhoto.setImageURI(null);
                            Toast.makeText(ProfileActivity.this,"Successfully Uploaded",Toast.LENGTH_SHORT).show();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();

                               Button button = findViewById(R.id.go_to_home);
                               button.setVisibility(View.VISIBLE);
                               button.setOnClickListener(v -> {
                                   Intent intent = new Intent(ProfileActivity.this,HomeActivity.class);
                                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                   startActivity(intent);
                               });

                        }).addOnFailureListener(e -> {


                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Toast.makeText(ProfileActivity.this,"Failed to Upload",Toast.LENGTH_SHORT).show();
                });
            }

            else {
                    Intent intent = new Intent(ProfileActivity.this,HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
            }


        }catch (Exception exception){
            Toast.makeText(getBaseContext(),"uploadImage() "+exception.getMessage(),Toast.LENGTH_LONG).show();
        }


    }

    // from firebase storage get url from user image
    // we need to upload url to real time database
    // we upload a holy user data by calling setUserBasicInformation and we call this method if the user try to select new image form device , or if we don,t url in profile activity

    private void uploadFullUserInformation(){
        try{
            final StorageReference ref = storageReference.child("userProfiles/"+getUserPhoneNumber());

            if(imageUri!=null){
                UploadTask uploadTask = ref.putFile(imageUri);



                uploadTask.continueWithTask(task -> {
                    if (!task.isSuccessful()) {
                        throw Objects.requireNonNull(task.getException());
                    }

                    // Continue with the task to get the download URL
                    return ref.getDownloadUrl();
                }).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String PHOTO_URL = downloadUri.toString();

                            setUserBasicInformation(
                                    Objects.requireNonNull(user_name.getText()).toString(),
                                    getUserPhoneNumber(),
                                    PHOTO_URL,
                                    Objects.requireNonNull(user_bio.getText()).toString(),
                                    getUserId()
                            );



                    } else {
                        // Handle failures
                        // ...
                        Toast.makeText(getBaseContext(),"failures",Toast.LENGTH_LONG).show();
                    }
                });
            }
            else {

                    setUserBasicInformation(
                            Objects.requireNonNull(user_name.getText()).toString(),
                            getUserPhoneNumber(),
                            URL,
                            Objects.requireNonNull(user_bio.getText()).toString(),
                            getUserId()
                    );
            }



        }
        catch (Exception exception){
            Toast.makeText(getBaseContext(),"uploadFullUserInformation "+exception.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    // this function to upload user data to firebase real time database
    private void setUserBasicInformation(String user_name,String phone,String url,String bio,String uid){

            if(user != null){
                User current_user = new User(user_name,phone,url,bio,uid);
                mDatabase.child(user.getUid()).child("username").setValue(current_user.username);
                mDatabase.child(user.getUid()).child("phone").setValue(current_user.phone);
                mDatabase.child(user.getUid()).child("photoUrl").setValue(current_user.photoUrl);
                mDatabase.child(user.getUid()).child("Bio").setValue(current_user.Bio);
                mDatabase.child(user.getUid()).child("UID").setValue(current_user.UID);
            }
            else
            {
                Toast.makeText(getBaseContext(),"null",Toast.LENGTH_LONG).show();
            }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Toast.makeText(getBaseContext(),"back press",Toast.LENGTH_LONG).show();
        FirebaseAuth.getInstance().signOut();
        finish();
    }
}