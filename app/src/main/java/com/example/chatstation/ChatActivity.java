package com.example.chatstation;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ChatActivity extends AppCompatActivity {


    private EditText typing;
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerOptions<Message> options;
    private FirebaseRecyclerAdapter<Message,MessageHolder> adapter;
    private FirebaseUser user;
    private String user_name;
    private String url;
    private final String link = "https://fcm.googleapis.com/fcm/send";

    private RequestQueue requestQueue;


    private String thisUserName;
    private String thisUserUrl;
    private String thisUserBio;
    private String thisUserPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Objects.requireNonNull(getSupportActionBar()).hide();
        try{




            requestQueue = Volley.newRequestQueue(this);

            FirebaseAuth auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();

            getThisUser();


            user_name = getIntent().getStringExtra("name");
            TextView name = findViewById(R.id.chat_name);
            name.setText(user_name);

            url = getIntent().getStringExtra("url");
            ImageView image = findViewById(R.id.chat_profile);
            Picasso.get().load(url).into(image);
            showUserState();



            recyclerView = findViewById(R.id.recycle_chat);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            mDatabase = FirebaseDatabase
                    .getInstance("https://chat-station-8d5a0-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();




            typing = findViewById(R.id.chat_type_msg);

            ImageButton send_msg = findViewById(R.id.chat_send_msg);
            send_msg.setOnClickListener(v -> sendMsg());
            LoadMessage();


            image.setOnClickListener(v -> {
                Intent intent = new Intent(ChatActivity.this,UserProfileActivity.class);
                intent.putExtra("name",user_name);
                intent.putExtra("url",url);
                intent.putExtra("phone",getIntent().getStringExtra("phone"));
                intent.putExtra("bio",getIntent().getStringExtra("bio"));
                startActivity(intent);
            });
            
            
            
            
            
            

        }
        catch (Exception exception){
            Toast.makeText(getBaseContext(),exception.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void getThisUser(){
        DatabaseReference reference = FirebaseDatabase
                .getInstance("https://chat-station-8d5a0-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("users").child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                thisUserName = Objects.requireNonNull(snapshot.child("username").getValue()).toString();
                thisUserUrl = Objects.requireNonNull(snapshot.child("photoUrl").getValue()).toString();
                thisUserPhone = Objects.requireNonNull(snapshot.child("phone").getValue()).toString();
                thisUserBio = Objects.requireNonNull(snapshot.child("Bio").getValue()).toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void LoadMessage() {
        String receiverId = getIntent().getStringExtra("uid");
        if(user != null){
            options = new FirebaseRecyclerOptions
                    .Builder<Message>()
                    .setQuery(mDatabase.child("message").child(user.getUid())
                            .child(receiverId),Message.class).build();
            adapter = new FirebaseRecyclerAdapter<Message, MessageHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull MessageHolder holder, int position, @NonNull Message model) {

                    if(model.getUserId().equals(user.getUid())){

                        holder.user_1.setVisibility(View.GONE);
                        holder.user_1_time.setVisibility(View.GONE);

                        holder.user_2.setVisibility(View.VISIBLE);
                        holder.user_2_time.setVisibility(View.VISIBLE);

                        holder.user_2.setText(model.getMsg());
                        holder.user_2_time.setText(model.getTime());
                    }
                    else{
                        holder.user_1.setVisibility(View.VISIBLE);
                        holder.user_1_time.setVisibility(View.VISIBLE);
                        holder.user_2.setVisibility(View.GONE);
                        holder.user_2_time.setVisibility(View.GONE);

                        holder.user_1.setText(model.getMsg());
                        holder.user_1_time.setText(model.getTime());
                    }
                }

                @NonNull
                @Override
                public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_container,parent,false);
                    return new MessageHolder(view);
                }
            };
        }
        adapter.startListening();
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void sendMsg() {
        String receiverId = getIntent().getStringExtra("uid");
        String msg = typing.getText().toString();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(new Date());

        if(msg.isEmpty()){
            Log.d("msg","empty");
        }
        else {
            if(user != null){
                final HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("msg",msg);
                hashMap.put("status","unseen");
                hashMap.put("userId",user.getUid());
                hashMap.put("time",time);
                mDatabase.child("message").child(receiverId)
                        .child(user.getUid())
                        .push().updateChildren(hashMap)
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                mDatabase.child("message").child(user.getUid())
                                         .child(receiverId).push().updateChildren(hashMap)
                                         .addOnCompleteListener(task1 -> {
                                             if(task1.isSuccessful()){
                                                 sendNotification(receiverId,msg);
                                               typing.setText(null);
                                               myChatList(receiverId,msg,time);
                                               userChatList(receiverId,msg,time);
                                               Toast.makeText(getBaseContext(),"sms send",Toast.LENGTH_LONG).show();
                                   }
                             });
                         }
                 });
            }
        }
    }


    public void sendNotification(String id,String body){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("to","/topics/"+id);
            JSONObject jsonObject1 = new JSONObject();


            jsonObject1.put("title","Message from "+thisUserName);
            jsonObject1.put("body",body);

            jsonObject.put("notification",jsonObject1);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, link, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> map = new HashMap<>();
                    map.put("content-type","application/json");
                    map.put("authorization","key=AAAARiR535k:APA91bGz-JPZ5S2tkHTcdPk_i5dLcZ7SB7shNZrw0Z2AQNl0J6qN4zqth7rUWzKT4GDlQ5-f2At1OY0Q4KALez_2m0aMjzkpkFoWx6dta7J3rjgEHhYaW3XnnpQP9TFLdgXrs6gREMHP");
                    return map;
                }
            };
            requestQueue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }










    public void myChatList(String receiverId,String msg,String time){

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("id",receiverId);
        hashMap.put("name",user_name);
        hashMap.put("url",url);
        hashMap.put("lastMsg",msg);
        hashMap.put("phone",getIntent().getStringExtra("phone"));
        hashMap.put("bio",getIntent().getStringExtra("bio"));
        hashMap.put("time",time);


        mDatabase.child("users").child(user.getUid()).child("list").child(user_name).updateChildren(hashMap);
    }

    public void userChatList(String receiverId,String msg,String time){


        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("id",user.getUid());
        hashMap.put("name",thisUserName);
        hashMap.put("url",thisUserUrl);
        hashMap.put("lastMsg",msg);
        hashMap.put("phone",thisUserPhone);
        hashMap.put("bio",thisUserBio);
        hashMap.put("time",time);

        mDatabase.child("users").child(receiverId).child("list").child(thisUserName).updateChildren(hashMap);
    }


    public void setOnline(String user_state){
        Map<String,Object> userState = new HashMap<>();
        userState.put("state",user_state);

        assert user != null;
        mDatabase.child("users").child(user.getUid())
                .child("state").updateChildren(userState);
    }

    private void showUserState(){
        String uid = getIntent().getStringExtra("uid");
        DatabaseReference reference = FirebaseDatabase
                .getInstance("https://chat-station-8d5a0-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("users").child(uid).child("state");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String state = Objects.requireNonNull(snapshot.child("state").getValue()).toString();
                TextView status = findViewById(R.id.status);
                if(state.equals("offline")){
                    status.setTextColor(Color.RED);
                }
                else{
                    status.setTextColor(Color.GREEN);
                }
                status.setText(state);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        setOnline("online");
    }

}