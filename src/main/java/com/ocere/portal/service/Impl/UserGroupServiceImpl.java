package com.ocere.portal.service.Impl;

import com.ocere.portal.model.Usergroup;
import com.ocere.portal.repository.UsergroupRepository;
import com.ocere.portal.service.UsergroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
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
        //Date date //= new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        //System.out.println(formatter.format(date));
        //usergroup.setCreated_at();
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

        if (optionalUpdatedUsergroup.isPresent()) {
            updatedUsergroup = optionalUpdatedUsergroup.get();
            updatedUsergroup.setActive(usergroup.isActive());
            updatedUsergroup.setCreated_at(usergroup.getCreated_at());
            updatedUsergroup.setEmpty(usergroup.isEmpty());
            updatedUsergroup.setName(usergroup.getName());
            updatedUsergroup.setTickets(usergroup.getTickets());
            updatedUsergroup.setUpdated_at(usergroup.getUpdated_at());
        } else {
            throw new Exception("Couldn't update usergroup, because it didn't exist!");
        }

        return usergroupRepository.saveAndFlush(updatedUsergroup);
    }
}
