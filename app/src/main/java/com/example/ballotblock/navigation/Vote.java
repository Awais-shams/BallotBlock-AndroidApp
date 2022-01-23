package com.example.ballotblock.navigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import com.example.ballotblock.R;

public class Vote extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    String name[] = {"John", "Charlie", "Mehmood", "Khawaja", "Rajput"};
    String ElectionType[] = {"CEO Elections", "CEO Elections", "CEO Elections", "CEO Elections", "CEO Elections"};
    String Designation[] = {"CEO", "CEO", "CEO", "CEO", "CEO"};
    String VotingEndTime[] = {"11:59am", "11:59am", "11:59am", "11:59am", "11:59am"};
    int Image[] = {R.drawable.brooklyn, R.drawable.canyon, R.drawable.london, R.drawable.miro, R.drawable.city};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
//        for back button, we can also customize back btn - to customize see link bookmarked
        getSupportActionBar().setTitle("BallotBlock");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.voteListView);

        MyAdapterList obj = new MyAdapterList(this, name, ElectionType, Designation, VotingEndTime, Image);
        listView.setAdapter(obj);

    }
}