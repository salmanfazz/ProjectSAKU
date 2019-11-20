package com.example.mockupsai.Home;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mockupsai.R;

import java.util.List;

public class HomeRecyclerViewScheduleAdapter extends RecyclerView.Adapter<HomeRecyclerViewScheduleAdapter.ViewHolder> {
    private List<Homes> homesList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_home_schedule, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Homes homes = homesList.get(position);
        holder.txtTitle.setText((String.valueOf((homes.getTitle()))));
        String color = String.valueOf(homes.getColor());
        holder.contentBackground.setBackgroundColor(Color.parseColor("" + color));
        holder.txtTime.setText((String.valueOf((homes.getTime()))));
    }

    @Override
    public int getItemCount() {
        return homesList.size();
    }

    public HomeRecyclerViewScheduleAdapter(List<Homes> homesList) {
        this.homesList = homesList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTitle, txtTime, txtGuru;
        public LinearLayout contentBackground;

        public ViewHolder(@NonNull View v) {
            super(v);
            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            txtTime = (TextView) v.findViewById(R.id.txtTime);
            txtGuru = (TextView) v.findViewById(R.id.txtGuru);
            contentBackground = (LinearLayout) v.findViewById(R.id.contentBackground);
        }
    }
}
