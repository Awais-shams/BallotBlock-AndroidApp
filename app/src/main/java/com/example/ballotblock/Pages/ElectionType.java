package com.example.ballotblock.Pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ballotblock.Authentication.LoginScreen;
import com.example.ballotblock.Authentication.Register3;
import com.example.ballotblock.R;
import com.example.ballotblock.RestAPI.ElectionModel;
import com.example.ballotblock.RestAPI.MyRetrofit;
import com.example.ballotblock.RestAPI.MyRetrofitInterface;
import com.example.ballotblock.navigation.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElectionType extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<ElectionModel> myList;
    ElectionTypeAdapter myAdapter;
    MyRetrofitInterface apiInterface;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_election_type);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("BallotBlock");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //        Bottom Navigation Bar
        BottomNavigationView bottomNavigationView =  findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.election);
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

        recyclerView = findViewById(R.id.electionTypeRecyclerView);
        myList = new ArrayList<>();
        apiInterface = MyRetrofit.getRetrofit().create(MyRetrofitInterface.class);
        sharedPreferences = getSharedPreferences("MyFile",0);

//        DisplayData();
        GetElection();
    }

    public void GetElection() {

        String accessToken = sharedPreferences.getString("accessToken","");
//        Log.d("tagg", "Access Token in Election Type: " + accessToken);

        apiInterface.getElection(accessToken).enqueue(new Callback<ArrayList<ElectionModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ElectionModel>> call, Response<ArrayList<ElectionModel>> response) {
                if (response.isSuccessful()) {
                    if(response.body().size() > 0) {
                        GenerateElectionData(response.body());
                    }
                    else {
                        Toast.makeText(ElectionType.this, "Cannot Show List of Elections. API Response body is null", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(ElectionType.this, "Problem in GetElections API format. Error in API response, response not successful.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ElectionModel>> call, Throwable t) {
                Toast.makeText(ElectionType.this, "Error in fetching API!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void GenerateElectionData(ArrayList<ElectionModel> electionData) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ElectionType.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        myAdapter = new ElectionTypeAdapter(electionData, getApplicationContext());
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

}