package com.ocere.portal.repository;

import com.ocere.portal.model.Usergroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsergroupRepository extends JpaRepository<Usergroup, Integer> {
}
