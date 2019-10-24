package com.example.mockupsai.Message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mockupsai.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<MessageRecyclerViewAdapter.ViewHolder>{

    List<Message> messageList;
    Context context;

    public MessageRecyclerViewAdapter(List<Message> messageList, Context context) {
        this.messageList = messageList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_guru, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.contentName.setText((String.valueOf(message.getTitle())));
        holder.contentDescription.setText((String.valueOf(message.getDescription())));
        holder.imageMessage.setImageResource(message.getImage());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public MessageRecyclerViewAdapter (List<Message> messageList) {
        this.messageList = messageList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView imageMessage;
        TextView contentName, contentDescription;

        public ViewHolder(View view) {
            super(view);
            imageMessage = view.findViewById(R.id.imageMessage);
            contentDescription = view.findViewById(R.id.contentDescription);
            contentName = view.findViewById(R.id.contentName);
        }
    }
}
