package com.example.chatstation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class UpdateUserNameActivity extends AppCompatActivity {

    private TextInputEditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_name);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getIntent().getStringExtra("name"));

        name = findViewById(R.id.user_update_name);
        name.setText(getIntent().getStringExtra("name"));
    }

    public void updateName(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            String id = getIntent().getStringExtra("id");
            DatabaseReference reference = FirebaseDatabase
                    .getInstance("https://chat-station-8d5a0-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference().child("users").child(id);
            reference.child("username").setValue(Objects.requireNonNull(name.getText()).toString());
            Toast.makeText(getBaseContext(),"update done",Toast.LENGTH_LONG).show();
            finish();
        }
        else {
            Toast.makeText(getBaseContext(),"no internet connection",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.update, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //return super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.update){
            updateName();
            return true;
        }
        return false;
    }
}