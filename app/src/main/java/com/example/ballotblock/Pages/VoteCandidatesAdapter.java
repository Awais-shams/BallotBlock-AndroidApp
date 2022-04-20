package com.example.ballotblock.Pages;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ballotblock.Pages.com.example.ballotblock.Election;
import com.example.ballotblock.R;
import com.example.ballotblock.RestAPI.ElectionModel;
import com.example.ballotblock.RestAPI.VoteCandidatesModel;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.abi.EventValues;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;

public class VoteCandidatesAdapter extends RecyclerView.Adapter<VoteCandidatesAdapter.MyViewHolder> {
    ArrayList<VoteCandidatesModel> myList;
    Context context;
    public Web3j client;
    String URL, ethAddress, password, fileName, walletAddress, contractAddress;
    File walletDir;
    SharedPreferences sharedPreferences;

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
        holder.candidateNameV.setText(model.getFirstname() + " " + model.getLastname());
        holder.emailInputTextViewCDV.setText(model.getEmail());
        holder.cnicInputTextViewCDV.setText(model.getCnic());
        holder.permanentAddInputTextViewETV.setText(model.getPermanentAddress());
        holder.ethAddressInputTextViewETV.setText(model.getPublicAddress());
        holder.imageViewV.setImageResource(R.drawable.bb_logo);
        holder.model = model;
        holder.position = holder.getAdapterPosition();

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public EditText candidateNameV;
        public TextView emailInputTextViewCDV;
        public TextView cnicInputTextViewCDV;
        public TextView permanentAddInputTextViewETV;
        public TextView ethAddressInputTextViewETV;
        public ImageView imageViewV;
        int position;
        VoteCandidatesModel model;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            candidateNameV = itemView.findViewById(R.id.candidateNameVote);
            emailInputTextViewCDV = itemView.findViewById(R.id.emailInputTextViewCDVote);
            cnicInputTextViewCDV = itemView.findViewById(R.id.cnicInputTextViewCDVote);
            permanentAddInputTextViewETV = itemView.findViewById(R.id.permanentAddInputTextViewETVote);
            ethAddressInputTextViewETV = itemView.findViewById(R.id.ethAddressInputTextViewETVote);
            imageViewV = itemView.findViewById(R.id.imageViewVote);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context.getApplicationContext(), "Yayy u clicked on Card.", Toast.LENGTH_SHORT).show();
                }
            });

            itemView.findViewById(R.id.VoteBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context.getApplicationContext(), "Yayy u clicked on Vote Button.", Toast.LENGTH_SHORT).show();
//                    Candidate's public ethAddress
                    String candidateAddress = myList.get(position).getPublicAddress();
                    CastVote(candidateAddress);
                }
            });
        }
        public void CastVote(String candidateAddress) {
//            for creating wallet error - saw from github
            setupBouncyCastle();

            sharedPreferences = context.getSharedPreferences("MyFile",0);
            walletAddress = sharedPreferences.getString("ethAddress",null);

            //        Infura Rinkeby Network url
            URL = "https://rinkeby.infura.io/v3/6c60a808c9c54857a88cc2a8f969d5a8";
//            contractAddress = "0x1D3d3e74dE0Aac8cB475d54c5D34de2beb35bc99";
            contractAddress = "0x1Bc61cA6537491B593cADd374Ee151787Ec6EC39";
            Credentials credentials = null;
            String electionName = null;

            client = Web3j.build(new HttpService(URL));
            password = "Pass123";
            String walletDirSP = sharedPreferences.getString("walletDir","");
            walletDir = new File(walletDirSP);
//            walletDirSP = "/data/data/com.example.ballotblock/files/UTC--2022-04-19T00-53-29.658000000Z--525b3a8ec5c44c63ce3badbb7d03430e7a6bf26c.json"

            try {
                credentials = WalletUtils.loadCredentials(password, walletDir);
//                Toast.makeText(context.getApplicationContext(), "Your address is " + credentials.getAddress(), Toast.LENGTH_LONG).show();
                Log.d("tagg", "Wallet Address: " + credentials.getAddress());
            }
            catch (Exception e){
                Toast.makeText(context.getApplicationContext(), "Could not Fetch Address... ", Toast.LENGTH_LONG).show();
            }

            // gas limit
            BigInteger gasLimit = BigInteger.valueOf(100000000);
//            4300000
            // gas price
            BigInteger gasPrice = BigInteger.valueOf(1000000);
//            22000000000


//            load the election
//            Election contract = Election.load(contractAddress, client, credentials,Contract.GAS_PRICE, Contract.GAS_LIMIT);
            Election contract = Election.load(contractAddress, client, credentials, new DefaultGasProvider());


            try {
                electionName = contract.electionName().sendAsync().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            medium article way, as api returns final to avoid blocking, read on web3j docs
//            Future<String> name = (Future<String>) contract.electionName().sendAsync();
//            electionName = name.get();
            Log.d("tagg", electionName);


            candidateAddress = "0xb18DCb383237b27fD770f7BE4DA8B1fCd9BBb1d3";

//            Function function = new Function<>(
//                    "vote",  // function we're calling
//                    Arrays.asList(new Type(value), ...),  // Parameters to pass as Solidity Types
//            Arrays.asList(new TypeReference<Type>() {}, ...));
//
//            String encodedFunction = FunctionEncoder.encode(function)
//            Transaction transaction = Transaction.createFunctionCallTransaction(
//                    <from>, <gasPrice>, <gasLimit>, contractAddress, <funds>, encodedFunction);
//
//            org.web3j.protocol.core.methods.response.EthSendTransaction transactionResponse =
//                    web3j.ethSendTransaction(transaction).sendAsync().get();
//
//            String transactionHash = transactionResponse.getTransactionHash();

            //                CompletableFuture<TransactionReceipt> transactionReceiptCompletableFuture = contract.vote(candidateAddress).sendAsync();
//
//                transactionReceiptCompletableFuture.thenAccept(transactionReceipt -> {
//
//                    // then accept gets transaction receipt only if the transaction is successful
//
//                }).exceptionally(transactionReceipt  -> {
//                    return null;
//                });

//                Future<TransactionReceipt> transactionReceiptFuture = contract.vote(candidateAddress).sendAsync();
//                String result = " gas: " +
//                        transactionReceiptFuture.get().getGasUsed();
//                Log.d("tagg", result);

//                CompletableFuture<TransactionReceipt> vote = contract.vote(candidateAddress).sendAsync();
//                electionName = String.valueOf(vote.get());

//                TransactionReceipt transactionReceipt = contract.vote(candidateAddress).sendAsync().get();
//            contract.vote(candidateAddress).sendAsync();

            try {
                Log.d("tagg", "Voted: " + contract.vote("0xb18DCb383237b27fD770f7BE4DA8B1fCd9BBb1d3").sendAsync());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Log.d("tagg", "Voted: " + contract.getStatus().sendAsync().get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Log.d("tagg", "Voted: " + contract.candidateAddresses(BigInteger.valueOf(0)).sendAsync().get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
        private void setupBouncyCastle() {
            final Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
            if (provider == null) {
                // Web3j will set up the provider lazily when it's first used.
                return;
            }
            if (provider.getClass().equals(BouncyCastleProvider.class)) {
                // BC with same package name, shouldn't happen in real life.
                return;
            }
            // Android registers its own BC provider. As it might be outdated and might not include
            // all needed ciphers, we substitute it with a known BC bundled in the app.
            // Android's BC has its package rewritten to "com.android.org.bouncycastle" and because
            // of that it's possible to have another BC implementation loaded in VM.
            Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
            Security.insertProviderAt(new BouncyCastleProvider(), 1);
        }
    }

}
