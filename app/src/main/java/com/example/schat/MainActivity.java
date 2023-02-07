package com.example.schat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.AdapterView;
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

public class MainActivity extends AppCompatActivity implements HomeFragment.HomeFrag{
    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;
    FirebaseDatabase fDb;
    DatabaseReference dbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView=findViewById(R.id.btmNavigation);
        viewPager=findViewById(R.id.pager);

        fDb=FirebaseDatabase.getInstance();
        dbRef=fDb.getReference("users").child(FirebaseAuth.getInstance().getUid());


//        UsersFragment usersFragment=new UsersFragment();
//        SettingsFragment settingsFragment=new SettingsFragment();
//        CallFragment callFragment=new CallFragment();
//        HomeFragment homeFragment=new HomeFragment();

//        bottomNavigationView.setSelectedItemId(R.id.home);
//        getSupportFragmentManager().beginTransaction().add(R.id.container,homeFragment).commit();

        BottomPagerAdapter adapter=new BottomPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        viewPager.setCurrentItem(0);
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                        return true;
                    case R.id.people:
                        viewPager.setCurrentItem(1);
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container,usersFragment).commit();
                        return true;
                    case R.id.call:
                        viewPager.setCurrentItem(2);
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container,callFragment).commit();
                        return true;
                    case R.id.settings:
                        viewPager.setCurrentItem(3);
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container,settingsFragment).commit();
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

    @Override
    public void onClick() {
//        UsersFragment usersFragment=new UsersFragment();
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.container,usersFragment).commit();
        viewPager.setCurrentItem(1);
        bottomNavigationView.setSelectedItemId(R.id.people);
    }

    public class BottomPagerAdapter  extends FragmentPagerAdapter {

        public BottomPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return new HomeFragment();
                case 1:return new UsersFragment();
                case 2:return new CallFragment();
                case 3:return new SettingsFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}