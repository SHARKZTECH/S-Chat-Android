package com.example.schat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {

    List<User> userList;
    Context context;

    public UsersAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user=userList.get(position);
        holder.userName.setText(user.getUserName());

        if(!user.getProfilePic().isEmpty()){
            Uri uri=Uri.parse(user.getProfilePic());
            Picasso.get().load(uri).into(holder.imageView);
        }else{
            holder.imageView.setImageResource(R.drawable.person);
        }

        if(user.getStatus().equals("online")){
            holder.onlineStatus.setBackground(context.getResources().getDrawable(R.drawable.bg_green));
        }else{
            holder.onlineStatus.setBackground(context.getResources().getDrawable(R.drawable.bg_white));
        }

        FirebaseDatabase.getInstance().getReference("chats")
                .child(FirebaseAuth.getInstance().getUid()+ user.getUserId())
                        .orderByChild("createdAt").limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChildren()){
                            for (DataSnapshot snapshot1:snapshot.getChildren()) {
                                Message message=snapshot1.getValue(Message.class);
                                holder.lastMsg.setText(message.getText());
                                if(message.getuId().equals(FirebaseAuth.getInstance().getUid()) && user.getStatus().equals("online")){
                                    holder.lastMsgIcon.setVisibility(View.VISIBLE);
                                }
                                notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        holder.itemView.setOnClickListener(view -> {
            Intent intent=new Intent(context,ChatActivity.class);
            intent.putExtra("user",user);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView userName,lastMsg,msgCount,time,onlineStatus;
        ImageView imageView,lastMsgIcon;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.userName);
            lastMsg=itemView.findViewById(R.id.lastMassage);
            msgCount=itemView.findViewById(R.id.msgCount);
            time=itemView.findViewById(R.id.time);
            onlineStatus=itemView.findViewById(R.id.onlineStatus);
            imageView=itemView.findViewById(R.id.userImg);
            lastMsgIcon=itemView.findViewById(R.id.lastMassageIcon);
        }
    }
}
