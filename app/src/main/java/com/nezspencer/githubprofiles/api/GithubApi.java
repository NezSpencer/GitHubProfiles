package com.nezspencer.githubprofiles.api;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by nezspencer on 3/6/17.
 */

public interface GithubApi {

    @GET("/users/user")
    rx.Observable<UserResponse> getUsers(@Query("type") String accountType, @Query("location") String
            location, @Query("language") String language, @Query("access_token") String OAuTH);

    @Headers("Accept: application/json")
    @POST("/login/oauth/access_token")
    rx.Observable<Token> requestAcessToken(@Body TokenRequestBody body);



}