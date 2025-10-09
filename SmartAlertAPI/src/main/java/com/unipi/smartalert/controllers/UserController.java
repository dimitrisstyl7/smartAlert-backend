package com.unipi.smartalert.controllers;

import com.unipi.smartalert.dtos.UserDTO;
import com.unipi.smartalert.jwt.JWTUtil;
import com.unipi.smartalert.models.UserRegistrationRequest;
import com.unipi.smartalert.services.impl.UserServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserServiceImpl userService;
    private final JWTUtil jwtUtil;

    public UserController(UserServiceImpl userService, JWTUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<?> registerCustomer(@RequestBody UserRegistrationRequest request){
        userService.addCustomer(request);
        String jwtToken = jwtUtil.issueToken(request.getEmail(), "ROLE_CITIZEN");
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .build();
    }

}
