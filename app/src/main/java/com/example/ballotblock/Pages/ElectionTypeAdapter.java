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
import com.example.ballotblock.RestAPI.VoterVerificationMode;
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
    private static String givenId;
    ElectionModel model;
    MyRetrofitInterface apiInterface;
    SharedPreferences sharedPreferences;
    String walletAddress = null;
    boolean applied = false;

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
        holder.model = model;
        holder.position = holder.getAdapterPosition();

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
        int position;
        ElectionModel model;

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
                    if(applied == false) {
//                        if user haven't clicked on Apply Now before
                        Toast.makeText(context.getApplicationContext(), "Please click on Apply Now below to register for elections.", Toast.LENGTH_SHORT).show();
                        return;
                    }


//    //                  Alert Dialog Box to take id from user
//                    AlertDialog.Builder myDialog = new AlertDialog.Builder(v.getContext());
//                    myDialog.setTitle("Enter same ID used for election registration: ");

//                    final EditText idInput = new EditText(context.getApplicationContext());
//                    idInput.setInputType(InputType.TYPE_CLASS_TEXT);
//                    myDialog.setView(idInput);
//                    myDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            givenId = idInput.getText().toString();
//        //                    String electionUuid = model.getUuid();
////                            get electionUuid to send in api for applying
//                            String electionUuid = myList.get(position).getUuid();
////                            get electionContractAddress to send in api for applying
//                            String electionContractAddress = myList.get(position).getContractAddress();
//        //                    voter is verified or not to participate in election
//                            CheckVoterVerification(electionUuid, v, electionContractAddress);
//                        }
//                    });
//                    myDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
//                    myDialog.show();
//                    Toast.makeText(context.getApplicationContext(), "Yayy u clicked on Card.", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(context.getApplicationContext(), "Yayy u clicked on Card." + position + " , elecName: " + model.getName(), Toast.LENGTH_SHORT).show();

//                            get electionUuid to send in api for applying
                    String electionUuid = myList.get(position).getUuid();
//                            get electionContractAddress to send in api for applying
                    String electionContractAddress = myList.get(position).getContractAddress();
                    //                    voter is verified or not to participate in election
                    CheckVoterVerification(electionUuid, v, electionContractAddress);
                    }
            });

            itemView.findViewById(R.id.applyVoteBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    AlertDialog.Builder myDialog = new AlertDialog.Builder(v.getContext());
//                    myDialog.setTitle("Enter ID: ");
//                    final EditText idInput = new EditText(context.getApplicationContext());
//                    idInput.setInputType(InputType.TYPE_CLASS_TEXT);
//                    myDialog.setView(idInput);
//                    myDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            givenId = idInput.getText().toString();
////                            Toast.makeText(context.getApplicationContext(), "Entered ID is " + givenId, Toast.LENGTH_SHORT).show();
////                            String electionUuid = model.getUuid();
//                            String electionUuid = myList.get(position).getUuid();
//
//                            sendApplyRequest(electionUuid, givenId);
//
//                        }
//                    });
//                    myDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
//                    myDialog.show();

                    String electionUuid = myList.get(position).getUuid();

                    sendApplyRequest(electionUuid);

//                    Toast.makeText(context.getApplicationContext(), "Yayy u clicked on Button.", Toast.LENGTH_SHORT).show();
                }
            });

        }
//        when clicked on "Apply to Vote" button -- checks if user has applied to participate in election or not and providing givenId
        public void sendApplyRequest(String electionUuid) {

            apiInterface = MyRetrofit.getRetrofit().create(MyRetrofitInterface.class);
            sharedPreferences = context.getSharedPreferences("MyFile",0);

//            check if wallet address is created and attached or not
            walletAddress = sharedPreferences.getString("ethAddress",null);
            if(walletAddress == null) {
                Toast.makeText(context.getApplicationContext(), "Wallet not created, please create Wallet to continue", Toast.LENGTH_SHORT).show();
                return;
            }

            String accessToken = sharedPreferences.getString("accessToken","");
            String voterUuid = sharedPreferences.getString("voterUuid","");
            String email = sharedPreferences.getString("voterEmail","");

            VoterVerifyModel voterVerifyModel = new VoterVerifyModel(electionUuid, voterUuid, walletAddress, email);

            apiInterface.isVoterVerify(accessToken, voterVerifyModel).enqueue(new Callback<VoterVerifyRespModel>() {
                @Override
                public void onResponse(Call<VoterVerifyRespModel> call, Response<VoterVerifyRespModel> response) {
                    if (response.isSuccessful()) {
                        VoterVerifyRespModel voterVerifyRespModel = response.body();
                        String message = voterVerifyRespModel.getMessage();
                        applied = true;

                        if (!voterVerifyRespModel.isRegistered()) {
                            Toast.makeText(context.getApplicationContext(), "You have successfully applied for this election.", Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                        applied = true;
                        Toast.makeText(context.getApplicationContext(), "Voter already applied for registeration of election", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<VoterVerifyRespModel> call, Throwable t) {
                    Toast.makeText(context.getApplicationContext(), "API fetch failed.", Toast.LENGTH_SHORT).show();
                }
            });
            givenId = null;
        }

//        when clicked on Election Card -- checks if user is permitted to vote in election or not.
        public void CheckVoterVerification(String electionUuid, View v, String electionContractAddress) {

            apiInterface = MyRetrofit.getRetrofit().create(MyRetrofitInterface.class);
            sharedPreferences = context.getSharedPreferences("MyFile",0);

            String accessToken = sharedPreferences.getString("accessToken","");
            String voterUuid = sharedPreferences.getString("voterUuid","");
            String email = sharedPreferences.getString("voterEmail","");

            VoterVerificationMode voterVerificationMode = new VoterVerificationMode(electionUuid, voterUuid, email);
            apiInterface.isVoterVerification(accessToken, voterVerificationMode).enqueue(new Callback<VoterVerifyRespModel>() {
                @Override
                public void onResponse(Call<VoterVerifyRespModel> call, Response<VoterVerifyRespModel> response) {

                    if (response.isSuccessful()) {
                        VoterVerifyRespModel voterVerifyRespModel = response.body();
                        String status = voterVerifyRespModel.getStatus();

                        if (status.equals("no voter found")) {
                            Toast.makeText(context.getApplicationContext(), "Please Apply for election First.", Toast.LENGTH_SHORT).show();
//                            givenId = null;
                        } else if(status.equals("unverified")) {
                            Toast.makeText(context.getApplicationContext(), "Voter is not yet verified.", Toast.LENGTH_SHORT).show();
//                            givenId = null;
                        }
                        else if(status.equals("verified")) {
                            Toast.makeText(context.getApplicationContext(), "Voter is verified.", Toast.LENGTH_SHORT).show();
                            String electionStatus = myList.get(position).getStatus();
                            if(electionStatus.equals("VOTING")) {
//                                givenId = null;
                                Intent intent = new Intent(v.getContext(), Vote.class);
                                intent.putExtra("electionUuid", electionUuid);
                                intent.putExtra("contractAddress", electionContractAddress);
                                v.getContext().startActivity(intent);
                            }
                            else {
                                Toast.makeText(context.getApplicationContext(), "Voter verified, but Election Not Started yet.", Toast.LENGTH_SHORT).show();
                            }
                        }
//                        givenId = null;
//                        Toast.makeText(context.getApplicationContext(), "Voter is verified.", Toast.LENGTH_SHORT).show();
                        Log.d("tagg", "API response body: " + response.body());
                    }
                    else {
//                        Toast.makeText(context.getApplicationContext(), "API, response body Error you cannot participate in election.", Toast.LENGTH_SHORT).show();
                        Log.d("tagg", "API response body: " + response.body());
                        Log.d("tagg", "API response message: " + response.message());
                        Log.d("tagg", "API response errorBody: " + response.errorBody());

                        Toast.makeText(context.getApplicationContext(), "Voter is not verified or election not started.", Toast.LENGTH_SHORT).show();
//                        givenId = null;
                    }
                }
                @Override
                public void onFailure(Call<VoterVerifyRespModel> call, Throwable t) {
                    Toast.makeText(context.getApplicationContext(), "API fetch failed.", Toast.LENGTH_SHORT).show();
                }
            });
//            givenId = null;
        }

    }



}
