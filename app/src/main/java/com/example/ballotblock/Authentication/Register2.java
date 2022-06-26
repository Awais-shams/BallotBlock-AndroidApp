package com.example.ballotblock.Authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ballotblock.R;
import com.example.ballotblock.RestAPI.MyRetrofit;
import com.example.ballotblock.RestAPI.MyRetrofitInterface;
import com.example.ballotblock.RestAPI.RegisterVoterModel;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register2 extends AppCompatActivity {
    Toolbar toolbar;
    EditText edtfName, edtlName, edtcnic, edtdob, edtaddress;
    String newEmail, newPass, fName, lName, cnic, dob, address;
    SharedPreferences sharedPreferences;
    MyRetrofitInterface apiInterface;

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

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        edtdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Register2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = day+"-"+month+"-"+year;
                        edtdob.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        apiInterface = MyRetrofit.getRetrofit().create(MyRetrofitInterface.class);

        sharedPreferences = getSharedPreferences("MyFile",0);
    }

    public void createAccount(View view) {
//        Getting Data via Intent from First Activity
        Intent intent = getIntent();
        newEmail = intent.getStringExtra("newEmail");
        newPass = intent.getStringExtra("newPass");

//        Parsing Data from Edit Text Fields
        fName = Register2.this.edtfName.getText().toString().trim();
        lName = Register2.this.edtlName.getText().toString().trim();
        cnic = Register2.this.edtcnic.getText().toString().trim();
        dob = Register2.this.edtdob.getText().toString().trim();
        address = Register2.this.edtaddress.getText().toString().trim();

        if(fName.isEmpty()) {
            edtfName.setError("First Name is Required!");
            edtfName.requestFocus();
            return;
        }
        if(!fName.matches("^[A-Za-z ]+$")) {
            edtfName.setError("Name should contain only Alphabet");
            edtfName.requestFocus();
            return;
        }
        if(lName.isEmpty()) {
            edtlName.setError("Last Name is Required!");
            edtlName.requestFocus();
            return;
        }
        if(!lName.matches("^[A-Za-z ]+$")) {
            edtlName.setError("Name should contain only Alphabet");
            edtlName.requestFocus();
            return;
        }

        if(cnic.isEmpty()) {
            edtcnic.setError("CNIC is required!");
            edtcnic.requestFocus();
            return;
        }
        if(cnic.length() != 13) {
            edtcnic.setError("Length should be 13 characters without dashes !");
            edtcnic.requestFocus();
            return;
        }

        Pattern pattern =
                Pattern. compile ( "[0123456789]*" ) ;
        Matcher matcher = pattern.matcher(cnic) ;
        boolean valid = matcher.matches();

        if (!valid) {
            edtcnic.setError("Enter valid CNIC Number without dashes!");
            edtcnic.requestFocus();
            return;
        }
        if(dob.isEmpty()) {
            edtdob.setError("Date of Birth is required!");
            edtdob.requestFocus();
            return;
        }

//        if(dob.length() != 6)
//        {
//            edtdob.setError("Length should be 6 integer values(e.g DDMMYY) !");
//            edtdob.requestFocus();
//            return;
//        }
//
//        Pattern patterndob =
//                Pattern. compile ( "[0123456789]*" ) ;
//        Matcher matcherdob = patterndob.matcher(cnic) ;
//        boolean validdob = matcher.matches() ;
//
//        if (!validdob) {
//            edtdob.setError("Enter valid CNIC Number!");
//            edtdob.requestFocus();
//            return;
//        }

        if(address.isEmpty()) {
            edtaddress.setError("Address is Required!");
            edtaddress.requestFocus();
            return;
        }

////      Saving First Activity "Intent" Data to sharedPreferences
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("K1", newEmail);
//        editor.putString("K2", newPass);
////      Saving Second Activity "Current" Data to sharedPreferences
//        editor.putString("K3", fName);
//        editor.putString("K4", lName);
//        editor.putString("K5", cnic);
//        editor.putString("K6", dob);
//        editor.putString("K7", address);
//
//        editor.apply();
////        Snackbar.make(view, "Saved",Snackbar.LENGTH_LONG).show();
//
//        Intent intent1 = new Intent(this, Register3.class);
//        startActivity(intent1);
        createUser();
    }

    public void createUser() {
        RegisterVoterModel cred = new RegisterVoterModel(fName, lName, newEmail, newPass, dob, cnic, address);
        apiInterface.registerVoter(cred);
        Call<RegisterVoterModel> myPost = apiInterface.registerVoter(cred);
        myPost.enqueue(new Callback<RegisterVoterModel>() {
            @Override
            public void onResponse(Call<RegisterVoterModel> call, Response<RegisterVoterModel> response) {
                if (response.isSuccessful()) {
                    if(response.body() != null) {
                        Toast.makeText(Register2.this, "Voter Registered...", Toast.LENGTH_SHORT).show();
                        Intent intent3 = new Intent(Register2.this, LoginScreen.class);
                        startActivity(intent3);
                    }
                    else {
                        Toast.makeText(Register2.this, "Cannot Register Voter. API Response body is null", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(Register2.this, "Problem in Voter Credentials format. Error in API response, response not successful.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RegisterVoterModel> call, Throwable t) {
                Toast.makeText(Register2.this, "Error in fetching API!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}