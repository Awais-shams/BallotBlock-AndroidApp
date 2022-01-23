package com.example.ballotblock.Authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.ballotblock.R;

public class Register2 extends AppCompatActivity {
    Toolbar toolbar;
    EditText fName, lName, cnic, dob, address;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("BallotBlock");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fName = findViewById(R.id.fNameTxt);
        lName = findViewById(R.id.lNameTxt);
        cnic = findViewById(R.id.cnicTxt);
        dob = findViewById(R.id.dobTxt);
        address = findViewById(R.id.addrressTxt);

        sharedPreferences = getSharedPreferences("MyFile",0);
    }

    public void GoToNext2(View view) {

//        Getting Data via Intent from First Activity
        Intent intent = getIntent();
        String newEmail = intent.getStringExtra("newEmail");
        String newPass = intent.getStringExtra("newPass");

//        Parsing Data from Edit Text Fields
        String fName = Register2.this.fName.getText().toString();
        String lName = Register2.this.lName.getText().toString();
        String cnic = Register2.this.cnic.getText().toString();
        String dob = Register2.this.dob.getText().toString();
        String address = Register2.this.address.getText().toString();

//      Saving First Activity "Intent" Data to sharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("K1", newEmail);
        editor.putString("K2", newPass);
//      Saving Second Activity "Current" Data to sharedPreferences
        editor.putString("K3", fName);
        editor.putString("K4", lName);
        editor.putString("K5", cnic);
        editor.putString("K6", dob);
        editor.putString("K7", address);

        editor.apply();
//        Snackbar.make(view, "Saved",Snackbar.LENGTH_LONG).show();

        Intent intent1 = new Intent(this, Register3.class);
        startActivity(intent1);
    }
}