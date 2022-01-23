package com.example.ballotblock.navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ballotblock.R;

import java.util.HashMap;

public class CandidatesListEdit extends AppCompatActivity {
    DBTools dbTools;
    EditText name, status;
    TextView electionType, designation, votingEndTime;
    String id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidates_list_edit);

        name = findViewById(R.id.candidateNameETEdit);
        electionType = findViewById(R.id.candidateElectionTypeETEdit);
        designation = findViewById(R.id.candidateDesignationETEdit);
        votingEndTime = findViewById(R.id.candidateVotingEndTimeETEdit);
        status = findViewById(R.id.candidateStatusETEdit);

        dbTools = new DBTools(getApplicationContext());

        Intent intent = getIntent();
//        another method of extracting data from intent
        id = intent.getExtras().getString("_id");
//        calling function in dbTools and passing id as argument
        HashMap<String, String> singleRecord = dbTools.getSingleRecord(id);
        if (singleRecord.size() != 0) {
            name.setText(singleRecord.get("name"));
            electionType.setText(singleRecord.get("electionType"));
            designation.setText(singleRecord.get("designation"));
            votingEndTime.setText(singleRecord.get("votingEndTime"));
            status.setText(singleRecord.get("status"));
        }
    }

    public void UpdateRecord(View view) {
        name = findViewById(R.id.candidateNameETEdit);
        electionType = findViewById(R.id.candidateElectionTypeETEdit);
        designation = findViewById(R.id.candidateDesignationETEdit);
        votingEndTime = findViewById(R.id.candidateVotingEndTimeETEdit);
        status = findViewById(R.id.candidateStatusETEdit);

        String fname, felectionType, fdesignation, fvotingEndTime, fstatus;

        fname = name.getText().toString();
        felectionType = electionType.getText().toString();
        fdesignation = designation.getText().toString();
        fvotingEndTime = votingEndTime.getText().toString();
        fstatus = status.getText().toString();

        ContentValues contentValues = new ContentValues();
        if (!fname.matches("")){
            contentValues.put("name", fname);
        }
        if (!felectionType.matches("")) {
            contentValues.put("electionType", felectionType);
        }
        if (!fdesignation.matches("")) {
            contentValues.put("designation", fdesignation);
        }
        if (!fvotingEndTime.matches("")) {
            contentValues.put("votingEndTime", fvotingEndTime);
        }
        if (!fstatus.matches("")) {
            contentValues.put("status", fstatus);
        }

        dbTools.UpdateRecord(id, contentValues);

        Intent intent =  new Intent(getApplicationContext(), CandidatesList.class);
        startActivity(intent);
    }

    public void DeleteRecord(View view) {
        dbTools.DeleteRecord(id);

        Intent intent =  new Intent(getApplicationContext(), CandidatesList.class);
        startActivity(intent);
    }
}