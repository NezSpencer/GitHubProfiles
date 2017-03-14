package com.nezspencer.githubprofiles.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by nezspencer on 3/6/17.
 */

public interface GithubApi {

    @GET("/search/users?q=lagos%20in:location+location:lagos&type:user&language" +
            ":java&access_token:19a66c9e6b3c846fce3184ad8f1b168ae5f64f9c")
    rx.Observable<UserResponse> getUsers();

    @Headers("Accept: application/json")
    @POST("/login/oauth/access_token")
    rx.Observable<Token> requestAcessToken(@Body TokenRequestBody body);


    @GET("/search/users?q=lagos%20in:location+location:lagos&type:user&language" +
            ":java&access_token:19a66c9e6b3c846fce3184ad8f1b168ae5f64f9c")
    Call<UserResponse> getGitHubUsers(@Query("page") String pageToLoad);



}