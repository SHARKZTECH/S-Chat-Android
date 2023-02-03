package com.example.schat;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class HomeFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        Button button=view.findViewById(R.id.btn);
        button.setOnClickListener(view1 -> {
            UsersFragment usersFragment=new UsersFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,usersFragment).commit();
        });

        return view;
    }
}