package com.example.ballotblock.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

        Button votenow = (Button) view.findViewById(R.id.Vote_Now_btn);
        votenow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Vote.class);
                startActivity(intent);
            }
        });

        Button candidate = (Button) view.findViewById(R.id.appVisitCountBtn);
        candidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.frameLayout, cad).commit();
                Intent intent = new Intent(getActivity(), AppVisitCount.class);
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


        Button election = (Button) view.findViewById(R.id.Election_type_btn);
        election.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ElectionType.class);
                startActivity(intent);
            }
        });

        Button list = (Button) view.findViewById(R.id.Candidate_list_btn);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CandidatesList.class);
                startActivity(intent);
            }
        });

        Button relax = (Button) view.findViewById(R.id.relaxBtn);
        relax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Relax.class);
                startActivity(intent);
            }
        });

        Button broadcast = (Button) view.findViewById(R.id.broadcastBtn);
        broadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AirplaneModeMainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}