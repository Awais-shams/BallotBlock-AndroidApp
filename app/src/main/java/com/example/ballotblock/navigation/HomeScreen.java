package com.example.ballotblock.navigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ballotblock.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeScreen extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    BottomNavigationView btmView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        btmView = findViewById(R.id.bottomNavigation);
        Home home = new Home();
        VoteNow vn = new VoteNow();
        Main_Profile profile= new Main_Profile();
        MapsFragment mapsFragment = new MapsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, home).commit();
        Toast.makeText(getApplicationContext(), "Home page", Toast.LENGTH_SHORT).show();
        Log.d("TAG", "onNavigationItemSelected: Home Page");
        btmView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home_page:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, home).commit();
                        Toast.makeText(getApplicationContext(), "Home page", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onNavigationItemSelected: Home Page");
                        return true;
                    case R.id.election:
                        Toast.makeText(getApplicationContext(), "Now Vote", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onNavigationItemSelected: Now Vote");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, vn).commit();
                        return true;
                    case  R.id.wallet:
                        Toast.makeText(getApplicationContext(), "Candidate", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onNavigationItemSelected: Cadidate");
//                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, cad).commit();
                        return true;
                    case R.id.profile:
                        Toast.makeText(getApplicationContext(), "Profile", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onNavigationItemSelected: Profile");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, profile).commit();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}