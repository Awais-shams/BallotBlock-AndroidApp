package com.example.ballotblock.Pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ballotblock.Authentication.LoginScreen;
import com.example.ballotblock.R;
import com.example.ballotblock.navigation.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {
    Toolbar toolbar;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("MyFile",0);
//            check if user is logged in.
        String accessToken = sharedPreferences.getString("accessToken",null);
        if(accessToken == null) {
            Toast.makeText(getApplicationContext(), "Not Logged In.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
            startActivity(intent);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
//        for back button, we can also customize back btn - to customize see link bookmarked
        getSupportActionBar().setTitle("BallotBlock");

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
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.logout) {
            sharedPreferences = getSharedPreferences("MyFile",0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("accessToken");
            editor.remove("ethAddress");
            editor.remove("voterUuid");
            editor.remove("voterEmail");
            editor.remove("walletDir");
            editor.apply();
            Intent intentLogout = new Intent(getApplicationContext(), LoginScreen.class);
            startActivity(intentLogout);
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        return;
    }

    public void GoToElection(View view) {
        Intent intent = new Intent(getApplicationContext(), ElectionType.class);
        startActivity(intent);
    }

//    public void Logout(View view) {
//        sharedPreferences = getSharedPreferences("MyFile",0);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.remove("accessToken");
//        editor.apply();
//        Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
//        startActivity(intent);
//        finish();
//    }

    public void GoToWallet(View view) {
        Intent intent = new Intent(getApplicationContext(), Wallet.class);
        startActivity(intent);
    }

    public void GoToProfile(View view) {
        Intent intent = new Intent(getApplicationContext(), Profile.class);
        startActivity(intent);
    }
}