package com.example.schat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FirebaseDatabase fDb;
    DatabaseReference dbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView=findViewById(R.id.btmNavigation);

        fDb=FirebaseDatabase.getInstance();
        dbRef=fDb.getReference("users").child(FirebaseAuth.getInstance().getUid());


        UsersFragment usersFragment=new UsersFragment();
        SettingsFragment settingsFragment=new SettingsFragment();
        CallFragment callFragment=new CallFragment();
        HomeFragment homeFragment=new HomeFragment();

        bottomNavigationView.setSelectedItemId(R.id.people);
        getSupportFragmentManager().beginTransaction().add(R.id.container,usersFragment).commit();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                        return true;
                    case R.id.people:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,usersFragment).commit();
                        return true;
                    case R.id.call:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,callFragment).commit();
                        return true;
                    case R.id.settings:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,settingsFragment).commit();
                        return true;
                }
                return false;
            }
        });
        online();
    }

    @Override
    protected void onStart() {
        super.onStart();
        online();
    }

    public void online(){
        HashMap<String,Object> map=new HashMap<>();
        map.put("status","online");
        dbRef.updateChildren(map);
    }
    public void offline(){
        HashMap<String,Object> map=new HashMap<>();
        map.put("status","offline");
        dbRef.updateChildren(map);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        offline();
    }
}