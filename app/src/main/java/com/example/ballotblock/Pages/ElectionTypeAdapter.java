package com.example.ballotblock.Pages;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ballotblock.R;
import com.example.ballotblock.RestAPI.ElectionModel;
import com.example.ballotblock.RestAPI.MyRetrofit;
import com.example.ballotblock.RestAPI.MyRetrofitInterface;
import com.example.ballotblock.RestAPI.VoterVerifyModel;
import com.example.ballotblock.RestAPI.VoterVerifyRespModel;

import java.util.ArrayList;

import jnr.ffi.annotations.In;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElectionTypeAdapter extends RecyclerView.Adapter<ElectionTypeAdapter.MyViewHolder> {
    ArrayList<ElectionModel> myList;
    Context context;
    private String givenId;
    ElectionModel model;
    boolean a = true;


    public ElectionTypeAdapter(ArrayList<ElectionModel> myList, Context context) {
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
        model = myList.get(position);
        holder.electionType.setText(model.getName());
        holder.electionDate.setText(model.getStartDate());
        holder.electionStartTime.setText(model.getStartDate());
        holder.electionEndTime.setText(model.getEndDate());
        holder.electionDesignation.setText(model.getDesignation());
        holder.electionStatus.setText(model.getStatus());
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
        public EditText electionDesignation;
        public EditText electionStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            electionType = itemView.findViewById(R.id.electionHeader);
            electionDate = itemView.findViewById(R.id.electionDateInputTextView);
            electionStartTime = itemView.findViewById(R.id.votingStartTimeInputTextView);
            electionEndTime = itemView.findViewById(R.id.votingEndTimeInputTextView);
            electionDesignation = itemView.findViewById(R.id.designationsEditText);
            electionStatus = itemView.findViewById(R.id.statusEditText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context.getApplicationContext(), "Yayy u clicked on Card.", Toast.LENGTH_SHORT).show();

                    String electionUuid = model.getUuid();
                    String electionStatus = model.getStatus();
//                    voter is verified or not to participate in election
                    if(CheckVoterVerification(electionUuid)) {
//                        check if election has started or not
                        if(electionStatus.equals("VOTING")) {
                            Intent intent = new Intent(v.getContext(), Vote.class);
                            v.getContext().startActivity(intent);
                        }
                        else {
                            Toast.makeText(context.getApplicationContext(), "Voter verified, but Election not started yet.", Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            });

            itemView.findViewById(R.id.applyVoteBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder myDialog = new AlertDialog.Builder(v.getContext());
                    myDialog.setTitle("Enter ID: ");
                    final EditText idInput = new EditText(context.getApplicationContext());
                    idInput.setInputType(InputType.TYPE_CLASS_TEXT);
                    myDialog.setView(idInput);
                    myDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            givenId = idInput.getText().toString();
//                            Toast.makeText(context.getApplicationContext(), "Entered ID is " + givenId, Toast.LENGTH_SHORT).show();
                            String electionUuid = model.getUuid();
                            sendApplyRequest(electionUuid, givenId);

                        }
                    });
                    myDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    myDialog.show();

//                    Toast.makeText(context.getApplicationContext(), "Yayy u clicked on Button.", Toast.LENGTH_SHORT).show();
                }
            });

        }
        public void sendApplyRequest(String electionUuid, String givenId) {
            MyRetrofitInterface apiInterface;
            SharedPreferences sharedPreferences;

            apiInterface = MyRetrofit.getRetrofit().create(MyRetrofitInterface.class);
            sharedPreferences = context.getSharedPreferences("MyFile",0);

            String accessToken = sharedPreferences.getString("accessToken","");
            String voterUuid = sharedPreferences.getString("voterUuid","");

            VoterVerifyModel voterVerifyModel = new VoterVerifyModel(electionUuid, voterUuid, givenId);

            apiInterface.isVoterVerify(accessToken, voterVerifyModel).enqueue(new Callback<VoterVerifyRespModel>() {
                @Override
                public void onResponse(Call<VoterVerifyRespModel> call, Response<VoterVerifyRespModel> response) {
                    if (response.isSuccessful()) {
                        VoterVerifyRespModel voterVerifyRespModel = response.body();
                        String message = voterVerifyRespModel.getMessage();

                        if (!voterVerifyRespModel.isRegistered()) {
                            Toast.makeText(context.getApplicationContext(), "You have successfully applied for this election.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(context.getApplicationContext(), "Voter already registered before", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<VoterVerifyRespModel> call, Throwable t) {
                    Toast.makeText(context.getApplicationContext(), "API fetch failed.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public boolean CheckVoterVerification(String electionUuid) {
            MyRetrofitInterface apiInterface;
            SharedPreferences sharedPreferences;

            apiInterface = MyRetrofit.getRetrofit().create(MyRetrofitInterface.class);
            sharedPreferences = context.getSharedPreferences("MyFile",0);

            String accessToken = sharedPreferences.getString("accessToken","");
            String voterUuid = sharedPreferences.getString("voterUuid","");

            if(givenId == null) {
                Toast.makeText(context.getApplicationContext(), "Please enter ID below on clicking apply now, then enter election.", Toast.LENGTH_SHORT).show();
                return false;
            }

            VoterVerifyModel voterVerifyModel = new VoterVerifyModel(electionUuid, voterUuid, givenId);

            apiInterface.isVoterVerification(accessToken, voterVerifyModel).enqueue(new Callback<VoterVerifyRespModel>() {
                @Override
                public void onResponse(Call<VoterVerifyRespModel> call, Response<VoterVerifyRespModel> response) {

                    if (response.isSuccessful()) {
                        VoterVerifyRespModel voterVerifyRespModel = response.body();
                        String status = voterVerifyRespModel.getStatus();

                        if (status.equals("no voter found")) {
                            Toast.makeText(context.getApplicationContext(), "Please Apply for election First.", Toast.LENGTH_SHORT).show();
                            a = false;
                        } else if(status.equals("not verified")) {
                            Toast.makeText(context.getApplicationContext(), "Voter is not yet verified.", Toast.LENGTH_SHORT).show();
                            a = false;
                        }
                        else if(status.equals("verified")) {
                            Toast.makeText(context.getApplicationContext(), "Voter is verified.", Toast.LENGTH_SHORT).show();
                            a = true;
                        }
//                        Toast.makeText(context.getApplicationContext(), "Voter is verified.", Toast.LENGTH_SHORT).show();
                        Log.d("OnClickElection", "API response body: " + response.body());
                        Log.d("OnClickElection", "API response body: " + voterVerifyRespModel.getStatus());
                        Log.d("OnClickElection", "API response message: " + response.message());
                        Log.d("OnClickElection", "API response errorBody: " + response.errorBody());
                        Log.d("OnClickElection", "API response raw: " + response.raw());
                        Log.d("OnClickElection", "API response headers: " + response.headers());
                        Log.d("OnClickElection", "API response toString: " + response.toString());
                        Log.d("OnClickElection", "API response code: " + response.code());
                    }
                    else {
//                        Toast.makeText(context.getApplicationContext(), "API, response body Error you cannot participate in election.", Toast.LENGTH_SHORT).show();
                        Log.d("OnClickElection", "API response body: " + response.body());
                        Log.d("OnClickElection", "API response message: " + response.message());
                        Log.d("OnClickElection", "API response errorBody: " + response.errorBody());
                        Log.d("OnClickElection", "API response raw: " + response.raw());
                        Log.d("OnClickElection", "API response headers: " + response.headers());
                        Log.d("OnClickElection", "API response toString: " + response.toString());
                        Log.d("OnClickElection", "API response code: " + response.code());

                        Toast.makeText(context.getApplicationContext(), "Voter is not verified or election not started.", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<VoterVerifyRespModel> call, Throwable t) {
                    Toast.makeText(context.getApplicationContext(), "API fetch failed.", Toast.LENGTH_SHORT).show();
                }
            });
            return a;
        }

    }



}
