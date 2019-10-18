package com.ocere.portal.repository;

import com.ocere.portal.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>
{
    public Role findByRole(String role);

    public Role findById(int id);

    public List<Role> findAll();
}
