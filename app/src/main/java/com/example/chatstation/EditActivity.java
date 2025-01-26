package com.example.chatstation;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatstation.databinding.ActivityEditBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class EditActivity extends AppCompatActivity {

    private Uri imageUri;
    private ImageView imageView;
    private FloatingActionButton upload;
    private FirebaseUser user;
    private StorageReference storageReference;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.chatstation.databinding.ActivityEditBinding binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getIntent().getStringExtra("name"));

        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        upload = binding.uploadImgProfile;

        storageReference = FirebaseStorage.getInstance().getReference("userProfiles/" + getUserPhoneNumber());

        mDatabase = FirebaseDatabase
                .getInstance("https://chat-station-8d5a0-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("users").child(user.getUid());

        TextView name = findViewById(R.id.user_name_edit);
        TextView bio = findViewById(R.id.user_bio_edit);
        TextView number = findViewById(R.id.user_phone_number_edit);
        String url = getIntent().getStringExtra("url");

        name.setText(getIntent().getStringExtra("name"));
        bio.setText(getIntent().getStringExtra("bio"));
        number.setText(getIntent().getStringExtra("number"));


        // rul
        number.setOnClickListener(v -> Toast.makeText(getBaseContext(),"phone number is static",Toast.LENGTH_LONG).show());

        // go to update name activity
        name.setOnClickListener(v -> {
            Intent intent = new Intent(EditActivity.this,UpdateUserNameActivity.class);
            intent.putExtra("name",getIntent().getStringExtra("name"));
            intent.putExtra("id",user.getUid());
            startActivity(intent);
        });

        // go to update bio activity
        bio.setOnClickListener(v -> {
            Intent intent = new Intent(EditActivity.this,UpdateUserBioActivity.class);
            intent.putExtra("name",getIntent().getStringExtra("name"));
            intent.putExtra("bio",getIntent().getStringExtra("bio"));
            intent.putExtra("id",user.getUid());
            startActivity(intent);
        });


        imageView = binding.editImg;
        Picasso.get().load(url).into(imageView);

        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(EditActivity.this,ImgActivity.class);
            intent.putExtra("url",url);
            startActivity(intent);
        });

        FloatingActionButton fab = binding.editImgProfile;
        fab.setOnClickListener(v -> selectImage());

        upload.setOnClickListener(v -> uploadImage());


    }

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
            imageView.setImageURI(imageUri);
            upload.setVisibility(View.VISIBLE);
        }
    }




    private String getUserPhoneNumber(){
        if(user != null){
            return user.getPhoneNumber();
        }
        return "null";
    }

    @SuppressLint("SetTextI18n")
    private void uploadImage() {

        try{

            if(imageUri!=null){
                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading File....");
                progressDialog.show();

                storageReference.putFile(imageUri)
                        .addOnSuccessListener(taskSnapshot -> {

                            imageView.setImageURI(null);
                            Toast.makeText(EditActivity.this,"Successfully Uploaded",Toast.LENGTH_SHORT).show();
                            if (progressDialog.isShowing())
                            getUrlFromStorage();
                            progressDialog.dismiss();
                        }).addOnFailureListener(e -> {


                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Toast.makeText(EditActivity.this,"Failed to Upload",Toast.LENGTH_SHORT).show();
                });
            }

        }catch (Exception exception){
            Toast.makeText(getBaseContext(),"uploadImage() "+exception.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void getUrlFromStorage(){
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
                        if(!PHOTO_URL.isEmpty()){
                            updateUrl(PHOTO_URL);
                            Picasso.get().load(PHOTO_URL).into(imageView);
                        }
                        else {
                            Toast.makeText(getBaseContext(),"null",Toast.LENGTH_LONG).show();
                        }

                    } else {
                        // Handle failures
                        // ...
                        Toast.makeText(getBaseContext(),"failures",Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        catch (Exception exception){
            Toast.makeText(getBaseContext(),"getUrlFromStorage "+exception.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void updateUrl(String url){
        mDatabase.child("photoUrl").setValue(url);
        Toast.makeText(getBaseContext(),"Successfully",Toast.LENGTH_LONG).show();
    }


}