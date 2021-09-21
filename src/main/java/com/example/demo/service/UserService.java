package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> findAll();

    User find(Long userId) throws IllegalArgumentException;

    void delete(Long userId);

    void save(User user);

    void update(User user);
}
