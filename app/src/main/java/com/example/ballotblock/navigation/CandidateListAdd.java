package com.example.ballotblock.navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ballotblock.R;

import java.util.HashMap;

    public class CandidateListAdd extends AppCompatActivity {
    DBTools dbTools;
    View view;
    EditText name, status;
    TextView electionType, designation, votingEndTime;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_list_add);
//        Our Candidates Db will be created after creating this dbTools object
        dbTools = new DBTools(getApplicationContext());
//        Snackbar.make(view, "Database OK", Snackbar.LENGTH_LONG).show();
        Toast.makeText(this, "DB Ok...", Toast.LENGTH_LONG).show();

//        Bind textViews
        name = findViewById(R.id.candidateNameET);
        electionType = findViewById(R.id.candidateElectionTypeET);
        designation = findViewById(R.id.candidateDesignationET);
        votingEndTime = findViewById(R.id.candidateVotingEndTimeET);
        status = findViewById(R.id.candidateStatusET);
//        bind button
        button = findViewById(R.id.btnSave);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Create Hashmap to store fields data
                HashMap<String, String> candidate = new HashMap<String, String>();
                candidate.put("name", name.getText().toString());
                candidate.put("electionType", electionType.getText().toString());
                candidate.put("designation", designation.getText().toString());
                candidate.put("votingEndTime", votingEndTime.getText().toString());
                candidate.put("status", status.getText().toString());
//                add our hashmap to dbTools
                dbTools.addNewCandidate(candidate);

                Intent intent =  new Intent(getApplicationContext(),CandidatesList.class);
                startActivity(intent);
            }
        });
    }
}