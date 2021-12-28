package com.example.ballotblock.Authentication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ballotblock.R;

public class Register1 extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

//        for back button, we can also customize back btn - to customize see link bookmarked
        getSupportActionBar().setTitle("BallotBlock");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void GoToNext(View view) {
        Intent intent = new Intent(this, Register2.class);
        startActivity(intent);
    }
}