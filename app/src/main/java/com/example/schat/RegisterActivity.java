package com.example.schat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {
    Button register;
    TextInputEditText userName,userPassword,cUserPassword;
    TextView btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register=findViewById(R.id.register);
        userName=findViewById(R.id.userName);
        userPassword=findViewById(R.id.userPassword);
        cUserPassword=findViewById(R.id.userCPassword);
        btnLogin=findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(view -> {
            startActivity(new Intent(this,LoginActivity.class));
        });

        register.setOnClickListener(view -> {
            String name=userName.getText().toString();
            String password=userPassword.getText().toString();
            String cPassword=cUserPassword.getText().toString();

            if(name.isEmpty() || password.isEmpty() || cPassword.isEmpty()){
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(cPassword)) {
                Toast.makeText(this, "passwords don't match!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
            }
        });
    }
}