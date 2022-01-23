package com.example.ballotblock.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ballotblock.R;

import java.util.ArrayList;

public class ElectionTypeAdapter extends RecyclerView.Adapter<ElectionTypeAdapter.MyViewHolder> {
    ArrayList<ElectionTypesModelClass> myList;
    Context context;

    public ElectionTypeAdapter(ArrayList<ElectionTypesModelClass> myList, Context context) {
        this.myList = myList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.election_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ElectionTypesModelClass model = myList.get(position);
        holder.electionType.setText(model.getElectionType());
        holder.electionDate.setText(model.getElectionDate());
        holder.electionStartTime.setText(model.getElectionStartTime());
        holder.electionEndTime.setText(model.getElectionEndTime());

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public EditText electionType;
        public TextView electionDate;
        public TextView electionStartTime;
        public TextView electionEndTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            electionType = itemView.findViewById(R.id.electionHeader);
            electionDate = itemView.findViewById(R.id.electionDateInputTextView);
            electionStartTime = itemView.findViewById(R.id.votingStartTimeInputTextView);
            electionEndTime = itemView.findViewById(R.id.votingEndTimeInputTextView);

        }
    }
}
