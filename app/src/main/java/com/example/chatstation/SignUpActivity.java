package com.example.chatstation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Objects.requireNonNull(getSupportActionBar()).hide();

        TextInputEditText code = findViewById(R.id.code);
        TextInputEditText phone = findViewById(R.id.phone_edit_text);


        findViewById(R.id.next).setOnClickListener(v -> {

            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                if(Objects.requireNonNull(phone.getText()).length() != 0){

                    // 012-345-6789
                    if(phone.getText().length() != 9){
                        Toast.makeText(getBaseContext(),"invalid phone number !",Toast.LENGTH_LONG).show();
                        phone.setError("invalid phone number !");
                    }
                    else {
                        String phone_number = code.getText()+""+phone.getText();
                        Intent intent = new Intent(this,VerifyActivity.class);
                        intent.putExtra("phone_number",phone_number);
                        startActivity(intent);
                    }
                }
                else {
                    Toast.makeText(getBaseContext(),"phone number required !",Toast.LENGTH_LONG).show();
                    phone.setError("phone number required !");
                }

            }
            else{
                     Toast.makeText(getBaseContext(),"check internet connection !",Toast.LENGTH_LONG).show();
            }



        });
    }
}