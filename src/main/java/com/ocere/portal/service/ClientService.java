package com.ocere.portal.service;

import com.ocere.portal.model.Client;
import com.ocere.portal.model.User;

import java.util.List;

public interface ClientService {
    void saveClient(Client client);

    boolean isClientAlreadyPresent(Client client);

    List<Client> findAll();

    Client createClient(Client client);

    void deleteClientById(int id) throws Exception;

    Client findClientById(int id);

    List<Client> findAllByAuthor(User user);

    Client updateClient(Client client, int id) throws Exception;
}
