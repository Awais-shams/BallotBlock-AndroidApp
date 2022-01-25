package com.example.ballotblock.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.example.ballotblock.R;

public class ForgotPassword extends AppCompatActivity {
    EditText edtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        edtEmail = findViewById(R.id.forgotPassEdtTxt);
    }

    public void forgotPasswordSend(View view) {
        String Email = edtEmail.getText().toString().trim();

        if (Email.isEmpty())
        {
            edtEmail.setError("Email is Required!");
            edtEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
        {
            edtEmail.setError("Please enter valid email!");
            edtEmail.requestFocus();
            return;
        }
    }
}