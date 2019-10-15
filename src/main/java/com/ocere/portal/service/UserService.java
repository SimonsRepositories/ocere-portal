package com.ocere.portal.service;
import com.ocere.portal.model.User;

import java.util.List;

public interface UserService
{
    public void saveUser(User user);
    public boolean isUserAlreadyPresent(User user);
    public List<User> findAll();
    public void removeUserById(int id);
    public User getUserById(int id);
    public void saveUserById(User user, int id);
}
