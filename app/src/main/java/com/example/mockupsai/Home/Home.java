package com.example.mockupsai.Home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mockupsai.R;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {
    RecyclerView recyclerView;
    List<Homes> homesList;
    HomeRecyclerViewHorizontalAdapter homeRecyclerViewHorizontalAdapter;

    String[] title = {"Matematika", "Bahasa Inggris", "Bahasa Jepang"};
    String[] date = {"10 Oct 2019", "12 Oct 2019", "14 Oct 2019"};
    String[] time = {"07:00", "10:00", "14:00"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        LayoutInflater If = getActivity().getLayoutInflater();
        View view = If.inflate(R.layout.activity_home, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) getView().findViewById(R.id.listToDo);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
        }

        homesList = new ArrayList<>();

        for (int i = 0; i < title.length; i++) {
            Homes homes = new Homes(title[i], date[i], time[i]);
            homesList.add(homes);
        }

        homeRecyclerViewHorizontalAdapter = new HomeRecyclerViewHorizontalAdapter(homesList);
        recyclerView.setAdapter(homeRecyclerViewHorizontalAdapter);
    }
}
