package com.example.schat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    Button login;
    TextInputEditText userName,userPassword;
    TextView btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login=findViewById(R.id.Login);
        userName=findViewById(R.id.userName);
        userPassword=findViewById(R.id.userPassword);
        btnRegister=findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(view -> {
            startActivity(new Intent(this,RegisterActivity.class));
        });

        login.setOnClickListener(view -> {
            String name=userName.getText().toString();
            String password=userPassword.getText().toString();

            if(name.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
            }
        });
    }
}