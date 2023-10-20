package com.example.schat;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    ToolDotProgress progress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_users, container, false);

       recyclerView=view.findViewById(R.id.recycler);
       progress=view.findViewById(R.id.dots_progress);

       progress.setVisibility(View.VISIBLE);

       mAuth=FirebaseAuth.getInstance();
       fDb=FirebaseDatabase.getInstance();
       dbRef=fDb.getReference().child("users");
       user=mAuth.getCurrentUser();
       userList=new ArrayList<>();

        getUsers();

        usersAdapter=new UsersAdapter(userList,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(usersAdapter);

        return view;
    }


    public void getUsers(){
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                progress.setVisibility(View.GONE);
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user1 = dataSnapshot.getValue(User.class);
                    if (user1 != null && user != null) {
                        user1.setUserId(dataSnapshot.getKey());
                        if (user.getEmail() != null && !user.getEmail().equalsIgnoreCase(user1.getEmail())) {
                            userList.add(user1);
                        }else{
                            Log.d("user", "onDataChange: "+user1.getEmail()+" "+user.getEmail()+" "+userList.get(0).getEmail());
                        }
                    }
                }
                usersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error here
            }
        });
    }

}