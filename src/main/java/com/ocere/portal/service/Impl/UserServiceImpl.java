package com.ocere.portal.service.Impl;

import com.ocere.portal.model.Role;
import com.ocere.portal.model.User;
import com.ocere.portal.repository.RoleRepository;
import com.ocere.portal.repository.UserRepository;
import com.ocere.portal.service.UserService;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    public BCryptPasswordEncoder encoder;
    public RoleRepository roleRepository;
    public UserRepository userRepository;

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder encoder, RoleRepository roleRepository, UserRepository userRepository) {
        this.encoder = encoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user, Role role) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setStatus("VERIFIED");
        Role userRole = roleRepository.findByRole(role.getDesc());
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    public void saveUserById(User user, int id, Role role) {
        for (int i = 0; i < findAll().size(); i++) {
            User tmpValue = findAll().get(i);
            if (tmpValue.getId() == id) {
                saveUser(user, role);
                return;
            }
        }
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean isUserAlreadyPresent(User user) {
        return userRepository.findById(user.getId()).isPresent();
    }

    public void removeUserById(int id) {
        for (int i = 0; i < findAll().size(); i++) {
            User tmpValue = findAll().get(i);
            if(tmpValue.getId() == id) {
                userRepository.deleteById(id);
                return;
            }
        }
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
