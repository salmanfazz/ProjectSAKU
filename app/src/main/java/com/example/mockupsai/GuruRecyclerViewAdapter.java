package com.example.mockupsai;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class GuruRecyclerViewAdapter extends RecyclerView.Adapter<GuruRecyclerViewAdapter.ViewHolder>{

    List<Guru> guruList;
    Context context;

    public GuruRecyclerViewAdapter(List<Guru> guruList, Context context) {
        this.guruList = guruList;
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
        Guru guru = guruList.get(position);
        holder.nameGuru.setText((String.valueOf(guru.getNama())));
        holder.telpGuru.setText((String.valueOf(guru.getTelp())));
        holder.imageGuru.setImageResource(guru.getGambar());
    }

    @Override
    public int getItemCount() {
        return guruList.size();
    }

    public GuruRecyclerViewAdapter(List<Guru> guruList) {
        this.guruList = guruList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView imageGuru;
        TextView telpGuru, nameGuru;

        public ViewHolder(View view) {
            super(view);
            imageGuru = view.findViewById(R.id.imageGuru);
            nameGuru = view.findViewById(R.id.nameGuru);
            telpGuru = view.findViewById(R.id.telpGuru);
        }
    }
}
