package com.example.schat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    Toolbar toolbar;
    User user;
    FirebaseAuth mAuth;
    FirebaseDatabase fDB;
    DatabaseReference dbRef;

    ArrayList<Message> messages;
    ChatAdapter chatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.arrow_back));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        ImageView imageView=findViewById(R.id.userImg);
        TextView userName=findViewById(R.id.userName);
        TextView onlineStatus=findViewById(R.id.onlineStatus);
        TextInputEditText text=findViewById(R.id.msgInput);
        ImageView sendBtn=findViewById(R.id.sendBtn);
        RecyclerView recyclerView=findViewById(R.id.recycler);

        mAuth=FirebaseAuth.getInstance();
        fDB=FirebaseDatabase.getInstance();
        dbRef=fDB.getReference().child("chats");
        messages=new ArrayList<>();

        chatAdapter=new ChatAdapter(this,messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatAdapter);

        user= (User) getIntent().getSerializableExtra("user");
        userName.setText(user.getUserName());

        if(user.getStatus().equals("online")){
            onlineStatus.setText(user.getStatus());
        }else {
            onlineStatus.setText("offline");
            onlineStatus.setTextColor(getResources().getColor(R.color.bg));
        }


        String senderId=mAuth.getUid();
        String receiverId=user.getUserId();
        final String senderRoom=senderId + receiverId;
        final String receiverRoom=receiverId + senderId;

        dbRef.child(senderRoom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Message message=dataSnapshot.getValue(Message.class);
                    messages.add(message);
                }
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendBtn.setOnClickListener(view -> {
            String msg=text.getText().toString();
            Message message=new Message(senderId,msg);
            message.setCreatedAt(new Date().getTime());
            text.setText("");

            dbRef.child(senderRoom)
                    .push()
                    .setValue(message)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            dbRef.child(receiverRoom)
                                    .push()
                                    .setValue(message)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                        }
                                    });
                        }
                    });
        });

    }
}