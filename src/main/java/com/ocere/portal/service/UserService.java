package com.ocere.portal.service;

import com.ocere.portal.model.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);

    boolean isUserAlreadyPresent(User user);

    List<User> findAll();

    void removeUserById(int id);

    User getUserById(int id);

    void saveUserById(User user, int id);
}
