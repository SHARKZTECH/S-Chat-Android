package com.example.schat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    Button register;
    TextInputEditText userName,userPassword,cUserPassword;
    TextView btnLogin;

    FirebaseAuth mAuth;
    FirebaseDatabase fDb;
    DatabaseReference dbRef;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register=findViewById(R.id.register);
        userName=findViewById(R.id.userName);
        userPassword=findViewById(R.id.userPassword);
        cUserPassword=findViewById(R.id.userCPassword);
        btnLogin=findViewById(R.id.btnLogin);
        mAuth=FirebaseAuth.getInstance();
        fDb=FirebaseDatabase.getInstance();
        dbRef=fDb.getReference();

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
                mAuth.createUserWithEmailAndPassword(name,password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    user=mAuth.getCurrentUser();
                                    String userId=user.getUid();
                                    User user1=new User(user.getEmail(),user.getEmail(),"","");
                                    dbRef.child("users").child(userId).setValue(user1);

                                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                    Toast.makeText(RegisterActivity.this, "Account created successful!", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(RegisterActivity.this, "failed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}