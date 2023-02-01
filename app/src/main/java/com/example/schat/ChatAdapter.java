package com.example.schat;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ChatAdapter extends RecyclerView.Adapter{

    private int SENDER_VIEW_TYPE=1;
    private int RECEIVER_VIEW_TYPE=2;

    Context context;
    ArrayList<Message> messages;

    public ChatAdapter(Context context, ArrayList<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == SENDER_VIEW_TYPE){
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_sender,parent,false);
            return new SenderViewHolder(view);
        }else {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_receiver,parent,false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message=messages.get(position);
        if (holder.getClass() == SenderViewHolder.class){
            ((SenderViewHolder) holder).senderMsg.setText(message.getText());
//            String formatedDate= DateFormat.getDateTimeInstance().format(message.getCreatedAt());
            String niceDateStr = (String) DateUtils.getRelativeTimeSpanString(message.getCreatedAt(), Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS);
            ((SenderViewHolder) holder).timeSend.setText(niceDateStr);
        }else{
            ((ReceiverViewHolder) holder).receiverMsg.setText(message.getText());
            String niceDateStr = (String) DateUtils.getRelativeTimeSpanString(message.getCreatedAt(), Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS);
            ((ReceiverViewHolder) holder).timeRe.setText(niceDateStr);
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())){
            return SENDER_VIEW_TYPE;
        }else {
            return RECEIVER_VIEW_TYPE;
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder{
        TextView senderMsg,timeSend;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMsg=itemView.findViewById(R.id.msgSend);
            timeSend=itemView.findViewById(R.id.timSend);
        }
    }
    public class ReceiverViewHolder extends RecyclerView.ViewHolder{
        TextView receiverMsg,timeRe;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverMsg=itemView.findViewById(R.id.msgReceived);
            timeRe=itemView.findViewById(R.id.timReceived);
        }
    }
}
