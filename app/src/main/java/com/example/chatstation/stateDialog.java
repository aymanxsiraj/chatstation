package com.example.chatstation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class stateDialog {
    public void updateUserStatus(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        DatabaseReference reference = FirebaseDatabase
                .getInstance("https://chat-station-8d5a0-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference();

        Map<String,Object> userState = new HashMap<>();
        userState.put("state", "offline");

        assert user != null;
        reference.child("users").child(user.getUid())
                .child("state").updateChildren(userState);
    }


}
