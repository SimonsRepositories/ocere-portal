package com.ocere.portal.service.Impl;

import com.ocere.portal.model.Role;
import com.ocere.portal.model.User;
import com.ocere.portal.repository.RoleRepository;
import com.ocere.portal.repository.UserRepository;
import com.ocere.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public void saveUser(User user, Set<Role> idRoles) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setMailpassword(encoder.encode(user.getMailpassword()));
        user.setStatus("VERIFIED");
        //Role userRole = roleRepository.findById(roleId);
        //user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        user.setRoles(new HashSet<Role>(idRoles));
        userRepository.save(user);
    }

    public void saveUserById(User user, int id, Set<Role> idRoles) {
        for (int i = 0; i < findAll().size(); i++) {
            User tmpValue = findAll().get(i);
            if (tmpValue.getId() == id) {
                saveUser(user, idRoles);
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

