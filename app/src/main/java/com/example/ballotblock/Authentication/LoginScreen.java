package com.example.ballotblock.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ballotblock.Pages.HomePage;
import com.example.ballotblock.Pages.Profile;
import com.example.ballotblock.R;
import com.example.ballotblock.RestAPI.AccessToken;
import com.example.ballotblock.RestAPI.GetVoterDetailsModel;
import com.example.ballotblock.RestAPI.MyRESTAPIModel;
import com.example.ballotblock.RestAPI.MyRetrofit;
import com.example.ballotblock.RestAPI.MyRetrofitInterface;
import com.example.ballotblock.navigation.Home;
import com.example.ballotblock.navigation.HomeScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

import jnr.constants.platform.Access;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {
    TextView signUpLink;
    EditText email, pass;
    MyRetrofitInterface apiInterface;
    SharedPreferences sharedPreferences;
    boolean isPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        signUpLink = findViewById(R.id.signupLinkTxtView);
        signUpLink.setOnClickListener(this);

        email = findViewById(R.id.emailTxt);
        pass = findViewById(R.id.passTxt);

        apiInterface = MyRetrofit.getRetrofit().create(MyRetrofitInterface.class);

        sharedPreferences = getSharedPreferences("MyFile",0);

    }

    @Override
    public void onBackPressed() {

        if (isPressed) {
//            clear all activity
            finishAffinity();
//            exit app
            System.exit(0);
        }
        else {
            Toast.makeText(this, "Press Back Again to exit.", Toast.LENGTH_SHORT).show();
            isPressed = true;
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                isPressed = false;
            }
        };
        new Handler().postDelayed(runnable, 2000);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, Register1.class);
        startActivity(intent);
    }

    public void LogIn(View view) {
        String emailS = email.getText().toString().trim();
        String passwordS = pass.getText().toString().trim();

        if (emailS.isEmpty()) {
            email.setError("Email is Required!");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailS).matches()) {
            email.setError("Please enter valid email!");
            email.requestFocus();
            return;
        }
        if(passwordS.isEmpty()) {
            pass.setError("Password is Required!");
            pass.requestFocus();
            return;
        }
        if(passwordS.length() < 6) {
            pass.setError("Min password length should be 6 character!");
            pass.requestFocus();
            return;
        }

        GetCredentials(emailS, passwordS);
    }

    public void GetCredentials(String _email, String _pass) {

        MyRESTAPIModel cred = new MyRESTAPIModel(_email, _pass);
        Call<AccessToken> myPost = apiInterface.getCredentials(cred);
        myPost.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    if(response.body() != null) {
                        AccessToken accessToken = response.body();
                        Log.d("tagg", "message: " + accessToken.getMessage());
                        Log.d("tagg", "accessToken: " + accessToken.getAccessToken());
                        Log.d("tagg", "uuid: " + accessToken.getUuid());

                        String firstPart = "access-token=";
                        String accessTokenStr = accessToken.getAccessToken();
                        accessTokenStr = firstPart + accessTokenStr;

                        String walletDirectory = sharedPreferences.getString(_email,null);

//                        Saving AccessToken and voterId in shared preferences
//                        also email for "applying to election" purposes
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("accessToken", accessTokenStr);
                        editor.putString("voterUuid", accessToken.getUuid());
                        editor.putString("voterEmail", _email);
                        editor.putString("walletDir", walletDirectory);
                        editor.apply();



//                        get voter details voter/:uuid api and store wallet address from it
                        getVoterDetails();

                        Toast.makeText(LoginScreen.this, "Login Successful...", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginScreen.this, HomePage.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(LoginScreen.this, "No Such Username Password exist.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(LoginScreen.this, "Wrong UserName Password.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Toast.makeText(LoginScreen.this, "Error in fetching API!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getVoterDetails() {
        sharedPreferences = getSharedPreferences("MyFile",0);
        String accessToken = sharedPreferences.getString("accessToken","");
        String voterUuid = sharedPreferences.getString("voterUuid",null);

        apiInterface.getVoterDetails(accessToken, voterUuid).enqueue(new Callback<ArrayList<GetVoterDetailsModel>>() {
            @Override
            public void onResponse(Call<ArrayList<GetVoterDetailsModel>> call, Response<ArrayList<GetVoterDetailsModel>> response) {
                if (response.isSuccessful()) {
                    if(response.body().size() > 0) {
                        ArrayList<GetVoterDetailsModel> voterDetails = response.body();
                        String walletAddress = voterDetails.get(0).getWalletAddress();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("ethAddress", walletAddress);
                        editor.apply();
                    }
                    else {
                        Toast.makeText(LoginScreen.this, "No details found for voter.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(LoginScreen.this, "Problem in GetVoterDetails API format. Error in API response, response not successful.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetVoterDetailsModel>> call, Throwable t) {
                Toast.makeText(LoginScreen.this, "Error in fetching API!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    Toast.makeText(LoginScreen.this, "Error in fetching API!!!", Toast.LENGTH_SHORT).show();

    @Override
    protected void onStart() {
        super.onStart();
//        updateUI();
    }

    //Change UI according to user data.
    public void updateUI(){
        if(true) {
            Toast.makeText(this,"You Signed In successfully",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginScreen.this, HomePage.class);
            startActivity(intent);
        }
        else {
//            Toast.makeText(this,"Authentication Failed.",Toast.LENGTH_LONG).show();
        }
    }


    public void forgotPass(View view) {
        Intent intent = new Intent(LoginScreen.this, ForgotPassword.class);
        startActivity(intent);
    }
}