package com.ocere.portal.service;

import com.ocere.portal.model.Role;
import com.ocere.portal.repository.RoleRepository;
import com.ocere.portal.repository.UserRepository;

import java.util.List;

public interface RoleService
{
    Role findByRole(String role);
    Role findById(int roleId);
    List<Role> findAll();
}
