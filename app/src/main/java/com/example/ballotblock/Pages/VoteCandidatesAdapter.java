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
import com.example.ballotblock.RestAPI.VoteCandidatesModel;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.File;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class VoteCandidatesAdapter extends RecyclerView.Adapter<VoteCandidatesAdapter.MyViewHolder> {
    ArrayList<VoteCandidatesModel> myList;
    Context context;
    public Web3j client;
    String URL, ethAddress, password, fileName, walletAddress, contractAddress;
    File walletDir;
    SharedPreferences sharedPreferences;
//    electionContractAddress will remain same for all list of candidates, because candidates are filtered and shown for only that specific election.
    String electionContractAddress;

    public VoteCandidatesAdapter(ArrayList<VoteCandidatesModel> myList, Context context, String electionContractAddress) {
        this.myList = myList;
        this.context = context;
        this.electionContractAddress = electionContractAddress;
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
//           election contract address which is uploaded to rinkeby testnet
//            contractAddress = "0x080cEe9d8A8Bb57b894Dba17A1BF201bD377B347";

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
            ECKeyPair ecKeyPair = credentials.getEcKeyPair();
            String privateKey = ecKeyPair.getPrivateKey().toString(16);

//            we create credentials once again with private key of user, so user can send eth and make state change to BC i.e casting vote.
            credentials = Credentials.create(privateKey);

//            // gas limit
//            BigInteger gasLimit = BigInteger.valueOf(100000000);
////            4300000
//            // gas price
//            BigInteger gasPrice = BigInteger.valueOf(1000000);
////            22000000000

//            load the election
//            Gas price way
//            Election contract = Election.load(contractAddress, client, credentials,Contract.GAS_PRICE, Contract.GAS_LIMIT);
//            default gas price way, works fine as of now and
//            if we pass credentials here from load method it doesnt work for state change of BC, it works when we create credentials with help of private key,
//            as we did above.
            Election contract = Election.load(electionContractAddress, client, credentials, new DefaultGasProvider());

            try {
                electionName = contract.electionName().sendAsync().get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
//            medium article way, as api returns final to avoid blocking, read on web3j docs
//            Future<String> name = (Future<String>) contract.electionName().sendAsync();
//            electionName = name.get();
            Log.d("tagg", electionName);
            Log.d("tagg", "User's address: " +  ethAddress);

//            candidateAddress = "0xb18DCb383237b27fD770f7BE4DA8B1fCd9BBb1d3";


//                CompletableFuture<TransactionReceipt> vote = contract.vote(candidateAddress).sendAsync();

            String result = null;
            try {
                TransactionReceipt transactionReceipt = contract.vote(candidateAddress).sendAsync().get();
                result = " gasUsed: " + transactionReceipt.getGasUsed() + " tranasctionHash: " + transactionReceipt.getTransactionHash();

                Toast.makeText(context.getApplicationContext(), "Successfully Voted... ", Toast.LENGTH_LONG).show();
                Toast.makeText(context.getApplicationContext(), "gas used: " + transactionReceipt.getGasUsed(), Toast.LENGTH_LONG).show();
                Toast.makeText(context.getApplicationContext(), "transaction hash: " + transactionReceipt.getTransactionHash(), Toast.LENGTH_LONG).show();
                Log.d("tagg", "Successfully Voted... " +  result);
            } catch (Exception e) {
                e.printStackTrace();
            }


//            getting status of election Deployed, Voting or Ended.
//            try {
//                Log.d("tagg", "Status of Election: " + contract.getStatus().sendAsync().get());
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

//            getting address of candidate which user casted vote to.
//            try {
//                Log.d("tagg", "Candidate Address: " + contract.candidateAddresses(BigInteger.valueOf(0)).sendAsync().get());
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }


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
