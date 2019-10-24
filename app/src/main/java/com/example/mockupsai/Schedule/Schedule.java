package com.example.mockupsai.Schedule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mockupsai.R;
import com.google.android.material.tabs.TabLayout;

public class Schedule extends Fragment implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        LayoutInflater If = getActivity().getLayoutInflater();
        View view = If.inflate(R.layout.activity_schedule, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tabLayout = (TabLayout) getView().findViewById(R.id.tabs);
        viewPager = (ViewPager) getView().findViewById(R.id.view_pager);
        adapter = new ViewPagerAdapter(getFragmentManager());

        //Add Fragment List
        adapter.AddFragment(new Senin(), "Senin ");
        adapter.AddFragment(new Senin(), "Selasa ");
        adapter.AddFragment(new Senin(), "Rabu ");
        adapter.AddFragment(new Senin(), "Kamis");
        adapter.AddFragment(new Senin(), "Jumat ");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onClick(View view) {

    }
}
