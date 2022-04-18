package com.example.ballotblock.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.ballotblock.R;

public class MyAdapterList extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] Name;
    private final String[] ElectionType;
    private final String[] Designation;
    private final String[] VotingEndTime;
    private final int[] Image;

    //    this constructor is made using alt+enter
    public MyAdapterList(@NonNull Context context, String[] name, String[] electionType, String[] designation, String[] votingEndTime,  int[] image) {
        super(context, R.layout.image_and_list_view, electionType);
        this.context = (Activity) context;
        Name = name;
        ElectionType = electionType;
        Designation = designation;
        VotingEndTime = votingEndTime;
        Image = image;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View viewHolder = inflater.inflate(R.layout.image_and_list_view, null);
        TextView name = viewHolder.findViewById(R.id.candidateNameVote);
//        TextView electionType = viewHolder.findViewById(R.id.electionTypeInputTextViewCDVote);
//        TextView designation = viewHolder.findViewById(R.id.designationInputTextViewCDVote);
//        TextView votingEndTime = viewHolder.findViewById(R.id.votingEndTimeInputTextViewETVote);
        ImageView imageView = viewHolder.findViewById(R.id.imageViewVote);
        Button button = viewHolder.findViewById(R.id.VoteBtn);

        name.setText(Name[position]);
//        electionType.setText(ElectionType[position]);
//        designation.setText(Designation[position]);
//        votingEndTime.setText(VotingEndTime[position]);
        imageView.setImageResource(Image[position]);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to VOTE ?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(context, "Yay you Voted.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        return viewHolder;

    }
}
