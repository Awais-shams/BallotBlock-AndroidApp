package com.example.ballotblock.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ballotblock.R;
import com.example.ballotblock.navigation.Home;
import com.example.ballotblock.navigation.HomeScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {
    TextView signUpLink;
    EditText email, pass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        signUpLink = findViewById(R.id.signupLinkTxtView);
        signUpLink.setOnClickListener(this);

        email = findViewById(R.id.emailTxt);
        pass = findViewById(R.id.passTxt);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, Register1.class);
        startActivity(intent);
    }

    public void LogIn(View view) {
        String emailS = email.getText().toString().trim();
        String passwordS = pass.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(emailS, passwordS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("TAG", "signInWithCustomToken:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);

                }
                else {
                    Log.d("TAG", "signInWithCustomToken:failure", task.getException());
                    Toast.makeText(LoginScreen.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    //Change UI according to user data.
    public void updateUI(FirebaseUser account){
        if(account != null) {
            Toast.makeText(this,"You Signed In successfully",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
            startActivity(intent);
        }
        else {
//            Toast.makeText(this,"Authentication Failed.",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        FirebaseAuth.getInstance().signOut();
    }
}