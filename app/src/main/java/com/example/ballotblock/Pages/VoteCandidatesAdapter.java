package com.example.ballotblock.Pages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ballotblock.R;
import com.example.ballotblock.RestAPI.ElectionModel;
import com.example.ballotblock.RestAPI.VoteCandidatesModel;

import java.util.ArrayList;

public class VoteCandidatesAdapter extends RecyclerView.Adapter<VoteCandidatesAdapter.MyViewHolder> {
    ArrayList<VoteCandidatesModel> myList;
    Context context;

    public VoteCandidatesAdapter(ArrayList<VoteCandidatesModel> myList, Context context) {
        this.myList = myList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_and_list_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VoteCandidatesModel model = myList.get(position);
        holder.candidateNameVote.setText(model.getFirstname() + " " + model.getLastname());
        holder.emailInputTextViewCDVote.setText(model.getEmail());
        holder.cnicInputTextViewCDVote.setText(model.getCnic());
        holder.permanentAddInputTextViewETVote.setText(model.getPermanentAddress());
        holder.ethAddressInputTextViewETVote.setText(model.getPublicAddress());
        holder.imageViewVote.setImageResource(R.drawable.bb_logo);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public EditText candidateNameVote;
        public TextView emailInputTextViewCDVote;
        public TextView cnicInputTextViewCDVote;
        public TextView permanentAddInputTextViewETVote;
        public EditText ethAddressInputTextViewETVote;
        public ImageView imageViewVote;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            candidateNameVote = itemView.findViewById(R.id.candidateNameVote);
            emailInputTextViewCDVote = itemView.findViewById(R.id.emailInputTextViewCDVote);
            cnicInputTextViewCDVote = itemView.findViewById(R.id.cnicInputTextViewCDVote);
            permanentAddInputTextViewETVote = itemView.findViewById(R.id.permanentAddInputTextViewETVote);
            ethAddressInputTextViewETVote = itemView.findViewById(R.id.ethAddressInputTextViewETVote);
            imageViewVote = itemView.findViewById(R.id.imageViewVote);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context.getApplicationContext(), "Yayy u clicked on Card.", Toast.LENGTH_SHORT).show();
                }
            });

            itemView.findViewById(R.id.VoteBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context.getApplicationContext(), "Yayy u clicked on Vote Button.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
