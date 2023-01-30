package com.example.schat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ChatActivity extends AppCompatActivity {

    Toolbar toolbar;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView imageView=findViewById(R.id.userImg);
        TextView userName=findViewById(R.id.userName);
        TextView onlineStatus=findViewById(R.id.onlineStatus);

        user= (User) getIntent().getSerializableExtra("user");

        userName.setText(user.getUserName());

    }
}