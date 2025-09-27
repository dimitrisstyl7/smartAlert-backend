package com.unipi.smartalert.auth;

import com.unipi.smartalert.dtos.UserDTO;

public class AuthenticationResponse {
    private String token;
    private UserDTO customerDTO;

    public AuthenticationResponse(String token, UserDTO customerDTO) {
        this.token = token;
        this.customerDTO = customerDTO;
    }

    public String getToken() {
        return token;
    }

    public UserDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setCustomerDTO(UserDTO customerDTO) {
        this.customerDTO = customerDTO;
    }
}
