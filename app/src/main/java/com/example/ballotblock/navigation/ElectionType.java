package com.example.ballotblock.navigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ballotblock.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ElectionType extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference myReference;
    ArrayList<ElectionTypesModelClass> myList;
    ElectionTypeAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_election_type);

        recyclerView = findViewById(R.id.electionTypeRecyclerView);

        database = FirebaseDatabase.getInstance("https://ballotblock-c7af9-default-rtdb.firebaseio.com/");
        myReference = database.getReference("ElectionTypes");

        myList = new ArrayList<>();

        DisplayData();
    }

    public void DisplayData() {
        myReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String elecType;
                Clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ElectionTypesModelClass student_record = new ElectionTypesModelClass();
//                    we can get parent node value by calling getKey();
                    elecType = snapshot.getValue().toString();
                    student_record.setElectionType(snapshot.getKey().toString());
                    student_record.setElectionDate(snapshot.child("Date").getValue().toString());
                    student_record.setElectionStartTime(snapshot.child("StartTime").getValue().toString());
                    student_record.setElectionEndTime(snapshot.child("EndTime").getValue().toString());

                    myList.add(student_record);
                }

                recyclerView.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ElectionType.this);
                recyclerView.setLayoutManager(linearLayoutManager);

                myAdapter = new ElectionTypeAdapter(myList, getApplicationContext());
                recyclerView.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void Clear() {
        if (myList != null) {
            myList.clear();
            if (myAdapter != null) {
                myAdapter.notifyDataSetChanged();
            }
            else
                myList = new ArrayList<>();
        }
    }
}