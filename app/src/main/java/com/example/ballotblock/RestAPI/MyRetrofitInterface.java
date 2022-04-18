package com.example.ballotblock.RestAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyRetrofitInterface {
//    for Logging in Voter, called in Authentication/LoginScreen
    @POST("auth/voter")
    Call<AccessToken> getCredentials(@Body MyRESTAPIModel cred);

//    for registering voters, called in Authentication/Register3
    @POST("voter/create")
    Call<RegisterVoterModel> registerVoter(@Body RegisterVoterModel cred);

//    for displaying election cards, called in Election
    @GET("election")
    Call<ArrayList<ElectionModel>> getElection(@Header("Cookie") String accessToken);

//    for displaying Vote Candidates cards, called in Pages/Vote
//    @GET("candidate")
//    Call<ArrayList<VoteCandidatesModel>> getCandidates(@Header("Cookie") String accessToken);

//    for displaying Vote filtered Candidates cards, called in Pages/Vote
    @GET("candidate/filtered/{uuid}")
    Call<ArrayList<VoteCandidatesModel>> getFilteredCandidates(@Header("Cookie") String accessToken, @Path("uuid") String uuid);

//    for applying for election, called in Pages/ElectionTypeAdapter
    @POST("voter/verify")
    Call<VoterVerifyRespModel> isVoterVerify(@Header("Cookie") String accessToken,
                               @Body VoterVerifyModel voterVerifyModel);

//    for checking if user is verified to vote in specific election, called in Pages/ElectionTypeAdapter
    @POST("voter/verificationStatus")
    Call<VoterVerifyRespModel> isVoterVerification(@Header("Cookie") String accessToken,
                                             @Body VoterVerifyModel voterVerifyModel);


}
