package com.example.ballotblock.Authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.ballotblock.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register2 extends AppCompatActivity {
    Toolbar toolbar;
    EditText edtfName, edtlName, edtcnic, edtdob, edtaddress;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("BallotBlock");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtfName = findViewById(R.id.fNameTxt);
        edtlName = findViewById(R.id.lNameTxt);
        edtcnic = findViewById(R.id.cnicTxt);
        edtdob = findViewById(R.id.dobTxt);
        edtaddress = findViewById(R.id.addrressTxt);

        sharedPreferences = getSharedPreferences("MyFile",0);
    }

    public void GoToNext2(View view) {

//        Getting Data via Intent from First Activity
        Intent intent = getIntent();
        String newEmail = intent.getStringExtra("newEmail");
        String newPass = intent.getStringExtra("newPass");

//        Parsing Data from Edit Text Fields
        String fName = Register2.this.edtfName.getText().toString().trim();
        String lName = Register2.this.edtlName.getText().toString().trim();
        String cnic = Register2.this.edtcnic.getText().toString().trim();
        String dob = Register2.this.edtdob.getText().toString().trim();
        String address = Register2.this.edtaddress.getText().toString().trim();


        if(fName.isEmpty())
        {
            edtfName.setError("First Name is Required!");
            edtfName.requestFocus();
            return;
        }
        if(!fName.matches("^[A-Za-z ]+$"))
        {
            edtfName.setError("Name should contain only Alphabet");
            edtfName.requestFocus();
            return;
        }
        if(lName.isEmpty())
        {
            edtlName.setError("First Name is Required!");
            edtlName.requestFocus();
            return;
        }
        if(!lName.matches("^[A-Za-z ]+$"))
        {
            edtlName.setError("Name should contain only Alphabet");
            edtlName.requestFocus();
            return;
        }

        if(cnic.isEmpty())
        {
            edtcnic.setError("CNIC is required!");
            edtcnic.requestFocus();
            return;
        }

        if(cnic.length() != 13)
        {
            edtcnic.setError("Length should be 13 characters without dashes !");
            edtcnic.requestFocus();
            return;
        }

        Pattern pattern =
                Pattern. compile ( "[0123456789]*" ) ;
        Matcher matcher = pattern.matcher(cnic) ;
        boolean valid = matcher.matches() ;

        if (!valid) {
            edtcnic.setError("Enter valid CNIC Number without dashes!");
            edtcnic.requestFocus();
            return;
        }

        if(dob.isEmpty())
        {
            edtdob.setError("CNIC is required!");
            edtdob.requestFocus();
            return;
        }

        if(dob.length() != 6)
        {
            edtdob.setError("Length should be 6 integer values(e.g DDMMYY) !");
            edtdob.requestFocus();
            return;
        }

        Pattern patterndob =
                Pattern. compile ( "[0123456789]*" ) ;
        Matcher matcherdob = patterndob.matcher(cnic) ;
        boolean validdob = matcher.matches() ;

        if (!validdob) {
            edtdob.setError("Enter valid CNIC Number!");
            edtdob.requestFocus();
            return;
        }

        if(address.isEmpty())
        {
            edtaddress.setError("Address is Required!");
            edtaddress.requestFocus();
            return;
        }

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