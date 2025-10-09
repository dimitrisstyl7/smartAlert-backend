package com.unipi.smartalert.auth;

import com.unipi.smartalert.dtos.UserDTO;
import com.unipi.smartalert.jwt.JWTUtil;
import com.unipi.smartalert.mappers.UserMapper;
import com.unipi.smartalert.models.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserMapper userDTOMapper;
    private final JWTUtil jwtUtil;

    public AuthenticationService(AuthenticationManager authenticationManager, UserMapper userDTOMapper, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDTOMapper = userDTOMapper;
        this.jwtUtil = jwtUtil;
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        User principal = (User) authentication.getPrincipal();
        UserDTO customerDTO = userDTOMapper.apply(principal);
        String token = jwtUtil.issueToken(customerDTO.getUsername(), customerDTO.getRoles().get(0));
        return new AuthenticationResponse(token, customerDTO);
    }
}
