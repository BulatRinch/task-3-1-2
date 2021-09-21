package com.example.demo;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class Start {

    private final RoleService roleService;
    private final UserService userService;

    public Start(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }


    public void run() {
        Role admin = new Role("ROLE_ADMIN");
        Role user = new Role("ROLE_USER");
        roleService.save(admin);
        roleService.save(user);

        userService.save(new User("Александр", "Иванов", 25, "admin@mail.ru",
                "admin",
                new HashSet<>() {{
                    add(admin);
                    add(user);
                }}));
        userService.save(new User("Максим", "Павлов", 31, "user@mail.ru",
                "user",
                new HashSet<>() {{
                    add(user);
                }}));
    }
}
