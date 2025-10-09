package com.unipi.smartalert.auth;

import com.unipi.smartalert.dtos.UserDTO;

public record AuthenticationResponse(String token, UserDTO customerDTO) {
}