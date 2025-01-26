package com.example.chatstation;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class VerifyActivity extends AppCompatActivity {

    private String verificationID;
    private FirebaseAuth firebaseAuth;
    private EditText editText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        try {
            Objects.requireNonNull(getSupportActionBar()).hide();

            String phone_number = getIntent().getStringExtra("phone_number");

            TextView OTP_NUMBER_VIEW = findViewById(R.id.otp_textview);
            OTP_NUMBER_VIEW.setText(String.format("waiting to detect an SMS sent to %s", phone_number));



            progressBar = findViewById(R.id.spin_kit);
            Sprite doubleBounce = new DoubleBounce();
            progressBar.setIndeterminateDrawable(doubleBounce);



            firebaseAuth = FirebaseAuth.getInstance();


            sendVerificationCode(firebaseAuth,phone_number);

            editText = findViewById(R.id.OTP);

            findViewById(R.id.next).setOnClickListener(v -> {

                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    String code = editText.getText().toString().trim();

                    if(code.isEmpty() || code.length() < 6){
                        editText.setError("code required !");
                        editText.requestFocus();
                        return;
                    }
                    verifyCode(code);
                }
                else{
                    Toast.makeText(getBaseContext(),"check internet connection !",Toast.LENGTH_LONG).show();
                }


            });
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }


    }

    private void sendVerificationCode(FirebaseAuth mAuth,String phoneNumber){
        try {
            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber(phoneNumber)       // Phone number to verify
                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                            .setActivity(this)                 // Activity (for callback binding)
                            .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                            .build();
            PhoneAuthProvider.verifyPhoneNumber(options);
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }


    }

    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, code);
        signInWithCredential(credential);
        /*if(code.equals(ACTUAL_CODE)){
            progressBar.setVisibility(View.VISIBLE);
            signInWithCredential(credential);
        }
        else{
            Toast.makeText(getBaseContext(),"wrong verification code !",Toast.LENGTH_LONG).show();
        }**/
    }

   private void signInWithCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Intent intent = new Intent(VerifyActivity.this,ProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks
    mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationID = s;

            if(verificationID.isEmpty()){
                Toast.makeText(getBaseContext(),"something went wrong !",Toast.LENGTH_LONG).show();
            }
            else {
                progressBar.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String ACTUAL_CODE = phoneAuthCredential.getSmsCode();

            if(ACTUAL_CODE != null){
                editText.setText(ACTUAL_CODE);
            }
            // on verification completed
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Log.w(TAG, "onVerificationFailed", e);

            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                Toast.makeText(getApplicationContext(),"Invalid request",Toast.LENGTH_LONG).show();
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Toast.makeText(getApplicationContext(),"The SMS quota for the project has been exceeded",Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        //progressBar.setVisibility(View.INVISIBLE);
    }
}