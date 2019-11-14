package com.example.mockupsai.Home;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mockupsai.R;

import java.util.List;

public class HomeRecyclerViewHorizontalAdapter extends RecyclerView.Adapter<HomeRecyclerViewHorizontalAdapter.ViewHolder> {
    private List<Homes> homesList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_home_horizontal, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Homes homes = homesList.get(position);
        holder.textTitle.setText((String.valueOf((homes.getTitle()))));
        holder.textDate.setText((String.valueOf((homes.getDate()))));
        String color = String.valueOf(homes.getColor());
        holder.textTitle.setBackgroundColor(Color.parseColor("" + color));
//        holder.textTime.setText((String.valueOf((homes.getTime()))));
    }

    @Override
    public int getItemCount() {
        return homesList.size();
    }

    public HomeRecyclerViewHorizontalAdapter(List<Homes> homesList) {
        this.homesList = homesList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textTitle, textDate, textTime;

        public ViewHolder(@NonNull View v) {
            super(v);
            textTitle = (TextView) v.findViewById(R.id.textTitle);
            textDate = (TextView) v.findViewById(R.id.textDate);
            textTime = (TextView) v.findViewById(R.id.textTime);
        }
    }
}
