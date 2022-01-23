package com.example.ballotblock.Authentication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.ballotblock.R;

public class Register1 extends AppCompatActivity {
    Toolbar toolbar;
    EditText email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
//        for back button, we can also customize back btn - to customize see link bookmarked
        getSupportActionBar().setTitle("BallotBlock");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        email = findViewById(R.id.newEmailTxt);
        pass = findViewById(R.id.newPassTxt);

    }

    public void GoToNext(View view) {
        String newEmail = email.getText().toString();
        String newPass = pass.getText().toString();

        Intent intent = new Intent(this, Register2.class);
        intent.putExtra("newEmail", newEmail);
        intent.putExtra("newPass", newPass);
        startActivity(intent);
    }
}