package com.slh.opensourcesharing.service;

import com.slh.opensourcesharing.model.Post;
import com.slh.opensourcesharing.model.Role;
import com.slh.opensourcesharing.model.User;
import com.slh.opensourcesharing.repository.RoleRepository;
import com.slh.opensourcesharing.repository.UserRepository;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService
{
    @Autowired
    public BCryptPasswordEncoder encoder;
    @Autowired
    public RoleRepository roleRepository;
    @Autowired
    public UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setStatus("VERIFIED");
        Role userRole = roleRepository.findByRole("SITE_USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    public void saveUserById(User user, int id)
    {
        for(int i = 0; i < findAll().size(); i++) {
            User tmpValue = findAll().get(i);
            if (tmpValue.getId() == id) {
                saveUser(user);
                return;
            }
        }
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public boolean isUserAlreadyPresent(User user)
    {
        return false;
    }

    @Cascade(CascadeType.DELETE)
    public void removeUserById(int id) {
        userRepository.deleteById(id);
    }
}
