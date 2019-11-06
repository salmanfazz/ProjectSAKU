package com.example.mockupsai;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mockupsai.Finance.Finance;
import com.example.mockupsai.Home.Home;
import com.example.mockupsai.Message.Messages;
import com.example.mockupsai.Retrofit.Token;
import com.example.mockupsai.Schedule.Schedule;
import com.example.mockupsai.User.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MenuMain extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);

        loadFragment(new Home());
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Schedule()).commit();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.navigation_homes:
                fragment = new Home();
                break;

            case R.id.navigation_finance:
                fragment = new Finance();
                break;

            case R.id.navigation_user:
                fragment = new User();
                break;

            case R.id.navigation_message:
                fragment = new Messages();
                break;

            case R.id.navigation_schedule:
                fragment = new Schedule();
                break;
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            Intent intent = getIntent();
            String message = intent.getStringExtra("Token");
            Bundle bundle = new Bundle();
            bundle.putString("Token", message);

            fragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
