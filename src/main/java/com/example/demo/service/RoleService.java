package com.example.demo.service;

import com.example.demo.model.Role;

public interface RoleService {
    Iterable<Role> findAll();
    void save(Role role);
}
