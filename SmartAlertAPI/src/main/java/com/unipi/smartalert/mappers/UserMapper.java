package com.unipi.smartalert.mappers;

import com.unipi.smartalert.dtos.UserDTO;
import com.unipi.smartalert.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserMapper implements Function<User, UserDTO> {

    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()),
                user.getUsername());
    }

}
