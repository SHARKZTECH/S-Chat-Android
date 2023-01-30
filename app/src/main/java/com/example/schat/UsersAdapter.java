package com.example.schat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView userName,lastMsg,msgCount,time,onlineStatus;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.userName);
            lastMsg=itemView.findViewById(R.id.lastMassage);
            msgCount=itemView.findViewById(R.id.msgCount);
            time=itemView.findViewById(R.id.time);
            onlineStatus=itemView.findViewById(R.id.onlineStatus);
            imageView=itemView.findViewById(R.id.userImg);
        }
    }
}
