package com.unipi.smartalert.services.impl;

import com.unipi.smartalert.dtos.UserDTO;
import com.unipi.smartalert.exceptions.DuplicateResourceException;
import com.unipi.smartalert.exceptions.ResourceNotFoundException;
import com.unipi.smartalert.mappers.UserMapper;
import com.unipi.smartalert.models.Role;
import com.unipi.smartalert.models.User;
import com.unipi.smartalert.models.UserRegistrationRequest;
import com.unipi.smartalert.repositories.RoleRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService {

    private final UserDataAccessService userDataAccessService;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDataAccessService userDataAccessService, UserMapper userMapper,
                           RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userDataAccessService = userDataAccessService;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDataAccessService.selectUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException(
                "Username: " + username + " not found!"));
    }

    public List<UserDTO> getAllCustomers() {
        return userDataAccessService.selectAllUsers().stream()
                .map(userMapper)
                .collect(Collectors.toList());
    }

    public UserDTO getCustomer(Long id) {
        return userDataAccessService.selectUserByID(id)
                .map(userMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(id)
                ));
    }

    public void addCustomer(UserRegistrationRequest customerRegistrationRequest) {
        String email = customerRegistrationRequest.getEmail();

        if (userDataAccessService.existsPersonWithEmail(email)) {
            throw new DuplicateResourceException("email already taken");
        }

        Role userRole = roleRepository.findByAuthority("ROLE_CITIZEN").get();
        List<Role> authorities = new ArrayList<>();
        authorities.add(userRole);


        User customer = new User(
                customerRegistrationRequest.getEmail(),
                passwordEncoder.encode(customerRegistrationRequest.getPassword()),
                customerRegistrationRequest.getFirstName(),
                customerRegistrationRequest.getLastName(),
                new Timestamp(System.currentTimeMillis()),
                authorities);
        userDataAccessService.insertUser(customer);
    }

    public void deleteCustomerById(Long customerId) {

        if (!userDataAccessService.existsPersonWithId(customerId)) {
            throw new ResourceNotFoundException("Customer with id [%s] not found".formatted(customerId));
        }


        userDataAccessService.deleteUserById(customerId);
    }

}