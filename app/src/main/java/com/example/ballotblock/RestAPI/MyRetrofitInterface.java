package com.example.ballotblock.RestAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MyRetrofitInterface {
    @POST("auth/voter")
    Call<MyRESTAPIModel> getCredentials(@Body MyRESTAPIModel cred);

    @POST("voter/create")
    Call<RegisterVoterModel> getCredentials(@Body RegisterVoterModel cred);

}
