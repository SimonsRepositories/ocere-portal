package com.ocere.portal.service.Impl;

import com.ocere.portal.model.Usergroup;
import com.ocere.portal.repository.UsergroupRepository;
import com.ocere.portal.service.UsergroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class UserGroupServiceImpl implements UsergroupService {

    private UsergroupRepository usergroupRepository;

    @Autowired
    public UserGroupServiceImpl(UsergroupRepository usergroupRepository) {
        this.usergroupRepository = usergroupRepository;
    }

    public List<Usergroup> findAll() {
        return usergroupRepository.findAll();
    }

    public Optional<Usergroup> findUsergroupById(int id) {
        return usergroupRepository.findById(id);
    }

    public Usergroup createUsergroup(Usergroup usergroup) {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        usergroup.setCreated_at(timestamp);
        return usergroupRepository.saveAndFlush(usergroup);
    }

    public void deleteUsergroupById(int id) throws Exception {
        if (usergroupRepository.existsById(id)) {
            usergroupRepository.deleteById(id);
        } else {
            throw new Exception("Couldn't delete usergroup, because it didn't exist!");
        }
    }

    public Usergroup updateUsergroup(Usergroup usergroup, int id) throws Exception {
        Usergroup updatedUsergroup;
        Optional<Usergroup> optionalUpdatedUsergroup = findUsergroupById(id);

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        if (optionalUpdatedUsergroup.isPresent()) {
            updatedUsergroup = optionalUpdatedUsergroup.get();
            updatedUsergroup.setActive(updatedUsergroup.isActive());
            updatedUsergroup.setCreated_at(updatedUsergroup.getCreated_at());
            updatedUsergroup.setEmpty(updatedUsergroup.isEmpty());
            updatedUsergroup.setName(updatedUsergroup.getName());
            updatedUsergroup.setTickets(updatedUsergroup.getTickets());
            updatedUsergroup.setUpdated_at(timestamp);
        } else {
            throw new Exception("Couldn't update usergroup, because it didn't exist!");
        }
        return usergroupRepository.saveAndFlush(updatedUsergroup);
    }
}
