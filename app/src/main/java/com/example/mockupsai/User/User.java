package com.example.mockupsai.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mockupsai.Guru.ContactGuru;
import com.example.mockupsai.MainActivity;
import com.example.mockupsai.R;

public class User extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        LayoutInflater If = getActivity().getLayoutInflater();
        View view = If.inflate(R.layout.activity_user, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView edit = (TextView) getView().findViewById(R.id.edit);
        edit.setOnClickListener(this);

        Button logout = (Button) getView().findViewById(R.id.btnlogout);
        logout.setOnClickListener(this);

        LinearLayout contact = (LinearLayout) getView().findViewById(R.id.contact);
        contact.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;

        switch (view.getId()) {
            case R.id.btnlogout:
                Intent logout = new Intent(getActivity().getApplication(), MainActivity.class);
                startActivity(logout);
                break;
            case R.id.edit:
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new EditUser());
                fr.commit();
                break;
            case R.id.contact:
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new ContactGuru());
                fragmentTransaction.commit();
                break;
        }
    }
}
