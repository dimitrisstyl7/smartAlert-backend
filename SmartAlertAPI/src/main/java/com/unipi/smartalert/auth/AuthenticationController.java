package com.unipi.smartalert.auth;

import com.unipi.smartalert.jwt.JWTUtil;
import com.unipi.smartalert.mappers.UserMapper;
import com.unipi.smartalert.repositories.RoleRepository;
import com.unipi.smartalert.repositories.UserRepository;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public AuthenticationController(AuthenticationService authenticationService, JWTUtil jwtUtil, UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper) {
        this.authenticationService = authenticationService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request){
        AuthenticationResponse response = authenticationService.login(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, response.getToken())
                .body(response);
    }

    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestBody TokenValidationRequest request) {
        String token = request.getAccessToken();
        String username = request.getUsername();
        boolean isValid = jwtUtil.isTokenValid(token, username);

        if (isValid) {
            return ResponseEntity.ok("Token is valid");
        } else {
            return ResponseEntity.status(401).body("Token has expired");
        }
    }
}
