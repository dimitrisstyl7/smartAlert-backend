package com.unipi.smartalert;

import com.unipi.smartalert.dtos.LocationDTO;
import com.unipi.smartalert.dtos.ReportDTO;
import com.unipi.smartalert.dtos.UserDTO;
import com.unipi.smartalert.models.Role;
import com.unipi.smartalert.models.User;
import com.unipi.smartalert.repositories.RoleRepository;
import com.unipi.smartalert.repositories.UserRepository;
import com.unipi.smartalert.services.impl.IncidentReportServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SmartAlertApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartAlertApiApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository,
                          PasswordEncoder passwordEncode, IncidentReportServiceImpl incidentReportServiceImpl){
        return args ->{

            /*Role citizenRole = roleRepository.findByAuthority("ROLE_CITIZEN").get();
            List<Role> authorities = new ArrayList<>();
            authorities.add(citizenRole);

            Role employeeRole = roleRepository.findByAuthority("ROLE_EMPLOYEE").get();
            List<Role> authorities2 = new ArrayList<>();
            authorities.add(employeeRole);

            User citizen = new User(
                    "citizen@gmail.com",
                    passwordEncode.encode("123456789"),
                    "citizen",
                    "citizen",
                    new Timestamp(System.currentTimeMillis()),
                    authorities);

            userRepository.save(citizen);

            User employee = new User(
                    "employee@gmail.com",
                    passwordEncode.encode("password"),
                    "employee",
                    "employee",
                    new Timestamp(System.currentTimeMillis()),
                    authorities2);

            userRepository.save(employee);*/

        };
    }


}
