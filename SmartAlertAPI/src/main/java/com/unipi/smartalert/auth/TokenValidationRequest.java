package com.unipi.smartalert.auth;

public record TokenValidationRequest(String accessToken, String username) {
}