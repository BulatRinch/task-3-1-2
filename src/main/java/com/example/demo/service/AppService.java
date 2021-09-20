package ru.itsinfo.springbootsecurityusersbootstrap.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itsinfo.springbootsecurityusersbootstrap.config.exception.LoginException;
import ru.itsinfo.springbootsecurityusersbootstrap.model.Role;
import ru.itsinfo.springbootsecurityusersbootstrap.model.User;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface AppService extends UserDetailsService {
    List<User> findAllUsers();

    User findUser(Long userId) throws IllegalArgumentException;

    void deleteUser(Long userId);

    Iterable<Role> findAllRoles();

    void authenticateOrLogout(Model model, HttpSession session, LoginException authenticationException, String authenticationName);

    //boolean saveUser(User user, BindingResult bindingResult, Model model);

    void insertUser(User user, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    void updateUser(User user, BindingResult bindingResult, RedirectAttributes redirectAttributes);
}
