package com.ocere.portal.repository;

import com.ocere.portal.model.Client;
import com.ocere.portal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    List<Client> findAllByAssignedUser(User user);
    List<Client> findAllByAuthor(User user);
}