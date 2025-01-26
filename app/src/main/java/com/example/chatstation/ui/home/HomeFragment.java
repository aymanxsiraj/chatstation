package com.example.chatstation.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatstation.ChatActivity;
import com.example.chatstation.ChatList;
import com.example.chatstation.ListAdapter;

import com.example.chatstation.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;



public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);




        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();


        RecyclerView recyclerView = binding.list;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<ChatList> lists = new ArrayList<>();

        if(user != null){
            DatabaseReference mDatabase = FirebaseDatabase
                    .getInstance("https://chat-station-8d5a0-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference().child("users").child(user.getUid()).child("list");



            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    lists.clear();
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        ChatList chatList = dataSnapshot.getValue(ChatList.class);
                        lists.add(chatList);
                    }
                    ListAdapter listAdapter = new ListAdapter(getContext(), lists);
                    recyclerView.setAdapter(listAdapter);
                    textView.setVisibility(View.GONE);
                    listAdapter.setOnUserClickListener(position -> {

                        String name = lists.get(position).name;
                        String url = lists.get(position).url;
                        String id = lists.get(position).id;
                        String phone = lists.get(position).phone;
                        String bio = lists.get(position).bio;



                        Intent intent = new Intent(getActivity(), ChatActivity.class);
                        intent.putExtra("name",name);
                        intent.putExtra("url",url);
                        intent.putExtra("uid",id);
                        intent.putExtra("phone",phone);
                        intent.putExtra("bio",bio);
                        startActivity(intent);
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}