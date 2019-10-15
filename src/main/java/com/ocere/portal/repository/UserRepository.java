package com.ocere.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ocere.portal.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{

}
