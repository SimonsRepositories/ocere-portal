package com.ocere.portal.service;

import com.ocere.portal.model.Client;
import com.ocere.portal.model.User;

import java.util.List;

public interface ClientService {
    void saveClient(Client client);

    boolean isClientAlreadyPresent(Client client);

    List<Client> findAll();

    void deleteClientById(int id) throws Exception;

    Client getClientById(int id);

    Client saveClientById(Client client, int id) throws Exception;

    List<Client> findAllByAssignedUser(User user);

    List<Client> findAllByAuthor(User user);

    Client updateClient(Client client, int id) throws Exception;
}
