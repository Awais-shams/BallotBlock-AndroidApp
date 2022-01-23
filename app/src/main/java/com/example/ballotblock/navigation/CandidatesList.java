package com.example.ballotblock.navigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.ballotblock.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CandidatesList extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    DBTools dbTools;
    TextView candidateID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidates_list);
//        toolbar
        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("BallotBlock");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        bind listView
        listView = findViewById(R.id.candidatesListView);

        dbTools = new DBTools(getApplicationContext());

        //        Called function to get all candidate
        ArrayList<HashMap<String, String>> AllCandidates = dbTools.getAllCandidates();
//        to see if db is not empty
        if (AllCandidates.size() != 0) {
            SimpleAdapter simpleAdapter = new SimpleAdapter(this, AllCandidates,
                    R.layout.candidate_view,
                    new String[] {"_id", "name", "electionType", "designation", "votingEndTime", "status"},
                    new int[]{R.id.idTextViewCD, R.id.candidateName, R.id.electionTypeInputTextViewCD, R.id.designationInputTextViewCD, R.id.votingEndTimeInputTextViewET, R.id.statusEditText});
            listView.setAdapter(simpleAdapter);
        }



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getApplicationContext(), CandidatesListEdit.class);
                //        binding TextViews
                candidateID = view.findViewById(R.id.idTextViewCD);
                intent.putExtra("_id", candidateID.getText().toString());
                startActivity(intent);
            }
        });

    }

    public void AddCandidate(View view) {
        Intent intent = new Intent(this, CandidateListAdd.class);
        startActivity(intent);
    }
}