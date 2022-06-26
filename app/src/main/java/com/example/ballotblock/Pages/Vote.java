package com.example.ballotblock.Pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ballotblock.Authentication.LoginScreen;
import com.example.ballotblock.R;
import com.example.ballotblock.RestAPI.ElectionModel;
import com.example.ballotblock.RestAPI.MyRetrofit;
import com.example.ballotblock.RestAPI.MyRetrofitInterface;
import com.example.ballotblock.RestAPI.VoteCandidatesModel;
import com.example.ballotblock.navigation.MapsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Vote extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<VoteCandidatesModel> myList;
    VoteCandidatesAdapter myAdapter;
    MyRetrofitInterface apiInterface;
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
        setContentView(R.layout.activity_vote2);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
//        for back button, we can also customize back btn - to customize see link bookmarked
        getSupportActionBar().setTitle("Cast Vote");


        recyclerView = findViewById(R.id.voteCandidateRecyclerView);
        myList = new ArrayList<>();
        apiInterface = MyRetrofit.getRetrofit().create(MyRetrofitInterface.class);
        sharedPreferences = getSharedPreferences("MyFile",0);

        GetCandidates();
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
            editor.apply();
            Intent intentLogout = new Intent(getApplicationContext(), LoginScreen.class);
            startActivity(intentLogout);
            finish();
        }
        return true;
    }

    public void GetCandidates() {

        String accessToken = sharedPreferences.getString("accessToken","");
//        Log.d("tagg", "Access Token in Election Type: " + accessToken);

//        apiInterface.getCandidates(accessToken).enqueue(new Callback<ArrayList<VoteCandidatesModel>>() {
//            @Override
//            public void onResponse(Call<ArrayList<VoteCandidatesModel>> call, Response<ArrayList<VoteCandidatesModel>> response) {
//                if (response.isSuccessful()) {
//                    if(response.body().size() > 0) {
//                        Toast.makeText(Vote.this, "Showing List of Candidates.", Toast.LENGTH_SHORT).show();
//                        GenerateCandidatesData(response.body());
//                    }
//                    else {
//                        Toast.makeText(Vote.this, "There are no candidates for this Election.", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                else {
//                    Toast.makeText(Vote.this, "Problem in getCandidates() API format. Error in API response, response not successful.", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<VoteCandidatesModel>> call, Throwable t) {
//                Toast.makeText(Vote.this, "Error in fetching API...", Toast.LENGTH_SHORT).show();
//            }
//        });

//        get filtered candidates
        Intent intent = getIntent();
        String electionUuid = intent.getStringExtra("electionUuid");
        String electionContractAddress = intent.getStringExtra("contractAddress");


        apiInterface.getFilteredCandidates(accessToken, electionUuid).enqueue(new Callback<ArrayList<VoteCandidatesModel>>() {
            @Override
            public void onResponse(Call<ArrayList<VoteCandidatesModel>> call, Response<ArrayList<VoteCandidatesModel>> response) {
                if (response.isSuccessful()) {
                    if(response.body().size() > 0) {
                        Toast.makeText(Vote.this, "Showing List of Candidates.", Toast.LENGTH_SHORT).show();
                         GenerateCandidatesData(response.body(), electionContractAddress);
                    }
                    else {
                        Toast.makeText(Vote.this, "There are no candidates for this Election.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(Vote.this, "Problem in getCandidates() API format. Error in API response, response not successful.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<VoteCandidatesModel>> call, Throwable t) {
                Toast.makeText(Vote.this, "Error in fetching API...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void GenerateCandidatesData(ArrayList<VoteCandidatesModel> candidatesData, String electionContractAddress) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Vote.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        myAdapter = new VoteCandidatesAdapter(candidatesData, getApplicationContext(), electionContractAddress);
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }
}