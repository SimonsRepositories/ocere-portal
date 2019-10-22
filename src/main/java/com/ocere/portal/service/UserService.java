package com.ocere.portal.service;

import com.ocere.portal.model.Role;
import com.ocere.portal.model.User;

import java.util.*;

public interface UserService {
    void saveUser(User user, Set<Role> idRoles);

    boolean isUserAlreadyPresent(User user);

    List<User> findAll();

    void removeUserById(int id);

    Optional<User> getUserById(int id);

    void saveUserById(User user, int id, Set<Role> idRoles);

    User findByEmail(String email);
}
