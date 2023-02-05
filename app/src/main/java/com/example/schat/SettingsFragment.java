package com.example.schat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class SettingsFragment extends Fragment {

    FirebaseAuth mAuth;
    FirebaseDatabase fDb;
    FirebaseStorage storage;
    ImageView userImg;
    TextView userName,userAbout;
    Button update;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_settings, container, false);

        userImg=view.findViewById(R.id.userImg);
        userName=view.findViewById(R.id.userName);
        userAbout=view.findViewById(R.id.userAbout);
        update=view.findViewById(R.id.updateProf);

        mAuth=FirebaseAuth.getInstance();
        fDb=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();

        fDb.getReference().child("users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                userName.setText(user.getUserName());

                if(!user.getProfilePic().isEmpty()){
                    Picasso.get()
                            .load(user.getProfilePic()).into(userImg);
                }
                userAbout.setText(user.getUserAbout());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        userImg.setOnClickListener(view1 -> {
            Intent intent=new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent,25);
        });

        update.setOnClickListener(view1 -> {
            String name=userName.getText().toString();
            String about=userAbout.getText().toString();

            HashMap<String,Object> map=new HashMap<>();
            map.put("userName",name);
            map.put("userAbout",about);
            if(name.isEmpty()){
                Toast.makeText(getContext(), "UserName Required!", Toast.LENGTH_SHORT).show();
            }else{
                fDb.getReference().child("users").child(FirebaseAuth.getInstance().getUid()).updateChildren(map);
                Toast.makeText(getContext(), "Profile updated", Toast.LENGTH_SHORT).show();
            }

        });

        return  view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getData() != null){
            Uri uri=data.getData();
            userImg.setImageURI(uri);

            final StorageReference reference=storage.getReference()
                    .child("profile_pic")
                    .child(FirebaseAuth.getInstance().getUid());

            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fDb.getReference().child("users")
                            .child(FirebaseAuth.getInstance().getUid()).child("profilePic")
                            .setValue(uri.toString());
                }
            });
        }
    }
}