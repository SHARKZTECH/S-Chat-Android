package com.example.schat;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UsersFragment extends Fragment {

    RecyclerView recyclerView;
    FirebaseAuth mAuth;
    FirebaseDatabase fDb;
    FirebaseUser user;
    DatabaseReference dbRef;
    List<User> userList;
    UsersAdapter usersAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_users, container, false);

       recyclerView=view.findViewById(R.id.recycler);

       mAuth=FirebaseAuth.getInstance();
       fDb=FirebaseDatabase.getInstance();
       dbRef=fDb.getReference().child("users");
       user=mAuth.getCurrentUser();
       userList=new ArrayList<>();

         usersAdapter=new UsersAdapter(userList,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(usersAdapter);

        getUsers();

        return view;
    }

    public void getUsers(){
        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                List<User> users=new ArrayList<>();
                users.add(snapshot.getValue(User.class));
                for (User user1:users) {
                    if(!user1.getEmail().equals(user.getEmail())){
                        userList.add(user1);
                    }
                }

//                userList.add(snapshot.getValue(User.class));
                usersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                usersAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                usersAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                usersAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}