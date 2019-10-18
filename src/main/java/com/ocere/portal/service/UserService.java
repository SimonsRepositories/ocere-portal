package com.ocere.portal.service;

import com.ocere.portal.model.Role;
import com.ocere.portal.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(User user, Role role);

    boolean isUserAlreadyPresent(User user);

    List<User> findAll();

    void removeUserById(int id);

    Optional<User> getUserById(int id);

    void saveUserById(User user, int id, Role role);

    User findByEmail(String email);
}
