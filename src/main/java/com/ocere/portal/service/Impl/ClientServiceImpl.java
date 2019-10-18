package com.ocere.portal.service.Impl;

import com.ocere.portal.model.Client;
import com.ocere.portal.model.User;
import com.ocere.portal.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Override
    public void saveClient(Client client) {

    }

    @Override
    public boolean isClientAlreadyPresent(Client client) {
        return false;
    }

    @Override
    public List<Client> findAll() {
        return null;
    }

    @Override
    public void removeClientById(int id) {

    }

    @Override
    public Client getClientById(int id) {
        return null;
    }

    @Override
    public Client saveClientById(Client client, int id) throws Exception {
        return null;
    }

    @Override
    public List<Client> findAllByTurnaround() {
        return null;
    }

    @Override
    public List<Client> findAllByAssignedUser(User user) {
        return null;
    }

    @Override
    public List<Client> findAllByAuthor(User user) {
        return null;
    }
}
