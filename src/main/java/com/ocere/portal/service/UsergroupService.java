package com.ocere.portal.service;

import com.ocere.portal.model.Usergroup;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UsergroupService {

    List<Usergroup> findAll();

    Optional<Usergroup> findUsergroupById(int id);

    Usergroup createUsergroup(Usergroup usergroup);

    Usergroup updateUsergroup(Usergroup usergroup, int id) throws Exception;

    void deleteUsergroupById(int id) throws Exception;
}
