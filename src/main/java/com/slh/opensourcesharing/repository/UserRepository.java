package com.slh.opensourcesharing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.slh.opensourcesharing.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{

}
