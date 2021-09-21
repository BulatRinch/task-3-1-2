package com.example.demo;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.HashSet;

@SpringBootApplication
public class SpringBootSecurityUsersBootstrapApplication {

    private static Start start;

    public SpringBootSecurityUsersBootstrapApplication(Start start) {
        this.start = start;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityUsersBootstrapApplication.class, args);
        start.run();
    }
}
