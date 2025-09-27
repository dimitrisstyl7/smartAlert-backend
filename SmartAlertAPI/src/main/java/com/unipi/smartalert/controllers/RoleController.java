package com.unipi.smartalert.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/roles")
public class RoleController {

    @GetMapping
    public String getRoleOfAuthenticatedUser() {
        // Get the authentication object from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());

        // Return the role as a response
        return authorities.isEmpty() ? "ROLE_USER" : authorities.getFirst().getAuthority();
    }

}
