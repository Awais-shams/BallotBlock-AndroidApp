package com.example.ballotblock.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ballotblock.R;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {
    TextView signUpLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        signUpLink = findViewById(R.id.signupLinkTxtView);
        signUpLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, Register1.class);
        startActivity(intent);
    }
}