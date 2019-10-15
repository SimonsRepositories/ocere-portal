package com.slh.opensourcesharing.service;
import com.slh.opensourcesharing.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService
{
    public void saveUser(User user);
    public boolean isUserAlreadyPresent(User user);
    public List<User> findAll();
    public void removeUserById(int id);
    public User getUserById(int id);
    public void saveUserById(User user, int id);
}
