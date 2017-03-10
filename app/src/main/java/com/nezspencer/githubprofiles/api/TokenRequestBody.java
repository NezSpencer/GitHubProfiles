package com.nezspencer.githubprofiles.api;

/**
 * Created by nezspencer on 3/8/17.
 */

public class TokenRequestBody {

    private String clientID;
    private String clientSecret;
    private String accessCode;

    public TokenRequestBody(String clientID, String clientSecret, String accessCode) {
        this.clientID = clientID;
        this.clientSecret = clientSecret;
        this.accessCode = accessCode;
    }

    public String getClientID() {
        return clientID;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getAccessCode() {
        return accessCode;
    }
}
