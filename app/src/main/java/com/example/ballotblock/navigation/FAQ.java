package com.example.ballotblock.navigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ballotblock.R;

public class FAQ extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
//        for back button, we can also customize back btn - to customize see link bookmarked
        getSupportActionBar().setTitle("BallotBlock");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void FAQ1(View view) {
        Intent intent = new Intent(this, FAQShow.class);
        intent.putExtra("link", "https://en.wikipedia.org/wiki/Election");
        startActivity(intent);
    }

    public void FAQ2(View view) {
        Intent intent = new Intent(this, FAQShow.class);
        intent.putExtra("link", "https://en.wikipedia.org/wiki/Voting");
        startActivity(intent);
    }
}