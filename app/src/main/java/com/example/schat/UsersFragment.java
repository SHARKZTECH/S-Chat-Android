package com.example.schat;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UsersFragment extends Fragment {

    RecyclerView recyclerView;
    Button button;
    FirebaseAuth mAuth;
    FirebaseDatabase fDb;
    FirebaseUser user;
    DatabaseReference dbRef;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_users, container, false);

        button=view.findViewById(R.id.chatBtn);
       recyclerView=view.findViewById(R.id.recycler);

       mAuth=FirebaseAuth.getInstance();
       fDb=FirebaseDatabase.getInstance();
       dbRef=fDb.getReference().child("Users");

       user=mAuth.getCurrentUser();


        UsersAdapter usersAdapter=new UsersAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(usersAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),ChatActivity.class));
            }
        });

        getUsers();

        return view;
    }

    public void getUsers(){
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Users", String.valueOf(snapshot.getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}