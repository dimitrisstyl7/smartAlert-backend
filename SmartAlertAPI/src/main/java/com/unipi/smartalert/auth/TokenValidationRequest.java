package com.unipi.smartalert.auth;

public class TokenValidationRequest {
    private String accessToken;
    private String username;

    public TokenValidationRequest(String accessToken, String username) {
        this.accessToken = accessToken;
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getUsername() {
        return username;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
