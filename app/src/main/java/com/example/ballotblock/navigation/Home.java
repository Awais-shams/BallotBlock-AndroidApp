package com.example.ballotblock.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ballotblock.R;

public class Home extends Fragment {
    public Home(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Main_Profile profile = new Main_Profile();
        VoteNow vn = new VoteNow();
        Candidate cad = new Candidate();

        View view = inflater.inflate(R.layout.homeview, container, false);

        Button election = (Button) view.findViewById(R.id.Election_type_btn);
        election.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ElectionType.class);
                startActivity(intent);
            }
        });

        Button votenow = (Button) view.findViewById(R.id.Vote_Now_btn);
        votenow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Vote.class);
                startActivity(intent);
            }
        });

        Button userprofile = (Button) view.findViewById(R.id.faqBtn);
        userprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FAQ.class);
                startActivity(intent);
            }
        });


        return view;
    }
}