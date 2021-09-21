package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping({"", "list"})
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("allRoles", roleService.findAll());

        model.addAttribute("showUserProfile",
                model.containsAttribute("user") && !((User) model.getAttribute("user")).isNew());
        model.addAttribute("showNewUserForm",
                model.containsAttribute("user") && ((User) model.getAttribute("user")).isNew());
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }

        return "admin-page";
    }

    @GetMapping("/{id}/profile")
    public String showUserProfileModal(@PathVariable("id") Long userId, Model model, RedirectAttributes attributes) {
        try {
            model.addAttribute("allRoles", roleService.findAll());
            model.addAttribute("user", userService.find(userId));
            return "fragments/user-form";
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PatchMapping()
    public String updateUser(@Valid @ModelAttribute("user") User user,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        bindingResult = checkBinding(bindingResult);
        if (!bindingResult.hasErrors()) {
            String oldPassword = user.getPassword();
            try {

                if (user.getPassword().isEmpty()) {
                    user.setPassword(userService.find(user.getId()).getPassword());
                    userService.update(user); // UPDATE
                } else {
                    userService.save(user);
                }
            } catch (DataIntegrityViolationException e) {
                user.setPassword(oldPassword);
                addError(bindingResult);
                addRedirect(user, bindingResult, redirectAttributes);
            }
        } else {
            addRedirect(user, bindingResult, redirectAttributes);
        }
        return "redirect:/admin";
    }

    @DeleteMapping("")
    public String deleteUser(@ModelAttribute("user") User user) {
        userService.delete(user.getId());
        return "redirect:/admin";
    }

    @PostMapping()
    public String saveUser(@Valid @ModelAttribute("user") User user,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasErrors()) {
            String oldPassword = user.getPassword();
            try {
                userService.save(user);
            } catch (DataIntegrityViolationException e) {
                user.setPassword(oldPassword);
                addError(bindingResult);
                addRedirect(user, bindingResult, redirectAttributes);
            }
        } else {
            addRedirect(user, bindingResult, redirectAttributes);
        }

        return "redirect:/admin";
    }

    private void addError(BindingResult bindingResult) {
        bindingResult.addError(new FieldError(bindingResult.getObjectName(),
                "email", "E-mail must be unique"));
    }

    private void addRedirect(User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("user", user);
        redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
    }


    private BindingResult checkBinding(BindingResult bindingResult) {
        if (!bindingResult.hasFieldErrors()) {
            return bindingResult;
        }

        User user = (User) bindingResult.getTarget();
        BindingResult newBindingResult = new BeanPropertyBindingResult(user, bindingResult.getObjectName());
        for (FieldError error : bindingResult.getFieldErrors()) {
            if (!user.isNew() && !error.getField().equals("password")) {
                newBindingResult.addError(error);
            }
        }

        return newBindingResult;
    }
}
