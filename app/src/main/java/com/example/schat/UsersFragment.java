package com.example.schat;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class UsersFragment extends Fragment {

    RecyclerView recyclerView;
    Button button,login;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_users, container, false);

        button=view.findViewById(R.id.chatBtn);
        login=view.findViewById(R.id.LoginBtn);
       recyclerView=view.findViewById(R.id.recycler);


        UsersAdapter usersAdapter=new UsersAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(usersAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),ChatActivity.class));
            }
        });
        login.setOnClickListener(view1 -> {
            startActivity(new Intent(getContext(),LoginActivity.class));
        });

        return view;
    }
}