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
import android.widget.EditText;
import android.widget.Toast;

import com.example.ballotblock.Authentication.LoginScreen;
import com.example.ballotblock.R;
import com.example.ballotblock.RestAPI.GetVoterDetailsModel;
import com.example.ballotblock.RestAPI.MyRetrofit;
import com.example.ballotblock.RestAPI.MyRetrofitInterface;
import com.example.ballotblock.RestAPI.VoteCandidatesModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {
    Toolbar toolbar;
    EditText fullName, email, cnic, address;
    SharedPreferences sharedPreferences;
    MyRetrofitInterface apiInterface;

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
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        //        for back button, we can also customize back btn - to customize see link bookmarked
        getSupportActionBar().setTitle("BallotBlock");

        fullName = findViewById(R.id.updNameEdtTxt);
        fullName.setEnabled(false);
        email = findViewById(R.id.updEmailEdtTxt);
        email.setEnabled(false);
        cnic = findViewById(R.id.cnicEdtTxt);
        cnic.setEnabled(false);
        address = findViewById(R.id.addressEdtTxt);
        address.setEnabled(false);

        //        Bottom Navigation Bar
        BottomNavigationView bottomNavigationView =  findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
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
        apiInterface = MyRetrofit.getRetrofit().create(MyRetrofitInterface.class);
        showData();
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

    @Override
    public void onBackPressed() {
        return;
    }

    public void showData() {
        String accessToken = sharedPreferences.getString("accessToken","");
//        Log.d("tagg", "Access Token in Election Type: " + accessToken);
        String voterUuid = sharedPreferences.getString("voterUuid",null);

        apiInterface.getVoterDetails(accessToken, voterUuid).enqueue(new Callback<ArrayList<GetVoterDetailsModel>>() {
            @Override
            public void onResponse(Call<ArrayList<GetVoterDetailsModel>> call, Response<ArrayList<GetVoterDetailsModel>> response) {
                if (response.isSuccessful()) {
                    if(response.body().size() > 0) {
                        SetDetails(response.body());
                    }
                    else {
                        Toast.makeText(Profile.this, "No details found for voter.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(Profile.this, "Problem in GetVoterDetails API format. Error in API response, response not successful.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetVoterDetailsModel>> call, Throwable t) {
                Toast.makeText(Profile.this, "Error in fetching API!!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void SetDetails(ArrayList<GetVoterDetailsModel> voterDetails) {
//        Toast.makeText(Profile.this, voterDetails.get(0).getFirstname(), Toast.LENGTH_SHORT).show();

        String firstName = voterDetails.get(0).getFirstname();
        String lastName = voterDetails.get(0).getLastname();
        String fullnameS = firstName+" "+lastName;
        fullName.setText(fullnameS);

        String emailS = voterDetails.get(0).getEmail();
        email.setText(emailS);

        String cnicS = voterDetails.get(0).getCnic();
        cnic.setText(cnicS);

        String addressS = voterDetails.get(0).getPermanentAddress();
        address.setText(addressS);
    }
}