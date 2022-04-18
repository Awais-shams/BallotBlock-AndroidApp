package com.example.ballotblock.Pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ballotblock.R;
import com.example.ballotblock.navigation.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
//        for back button, we can also customize back btn - to customize see link bookmarked
        getSupportActionBar().setTitle("Home Page");

//        Bottom Navigation Bar
        BottomNavigationView bottomNavigationView =  findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.home_page);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home_page:
                        Intent intent = new Intent(getApplicationContext(), HomePage.class);
                        startActivity(intent);
//                        Toast.makeText(getApplicationContext(), "Home page", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onNavigationItemSelected: Home Page");
                        return true;
                    case R.id.election:
//                        Toast.makeText(getApplicationContext(), "Elections", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onNavigationItemSelected: Elections");
                        Intent intent1 = new Intent(getApplicationContext(), ElectionType.class);
                        startActivity(intent1);
                        return true;
                    case  R.id.wallet:
//                        Toast.makeText(getApplicationContext(), "Wallet", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onNavigationItemSelected: Wallet");
                        Intent intent2 = new Intent(getApplicationContext(), Wallet.class);
                        startActivity(intent2);
                        return true;
                    case R.id.profile:
//                        Toast.makeText(getApplicationContext(), "Profile", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onNavigationItemSelected: Profile");
                        Intent intent3 = new Intent(getApplicationContext(), Profile.class);
                        startActivity(intent3);
                        return true;
                    case R.id.Map:
//                        Toast.makeText(getApplicationContext(), "Map", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onNavigationItemSelected: Map");
                        Intent intent4 = new Intent(getApplicationContext(), MapsActivity.class);
                        startActivity(intent4);
                        return true;
                }
                return false;
            }
        });


    }

    public void GoToElection(View view) {
        Intent intent = new Intent(getApplicationContext(), ElectionType.class);
        startActivity(intent);
    }
}