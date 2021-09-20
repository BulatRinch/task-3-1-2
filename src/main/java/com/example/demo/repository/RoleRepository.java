package ru.itsinfo.springbootsecurityusersbootstrap.repository;

import org.springframework.data.repository.CrudRepository;
import ru.itsinfo.springbootsecurityusersbootstrap.model.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
}
