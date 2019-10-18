package com.ocere.portal.service.Impl;

import com.ocere.portal.model.Client;
import com.ocere.portal.model.User;
import com.ocere.portal.repository.ClientRepository;
import com.ocere.portal.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public boolean isClientAlreadyPresent(Client client) {
        return clientRepository.findById(client.getId()).isPresent();
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public void removeClientById(int id) {
        clientRepository.deleteById(id);
    }

    @Override
    public Client getClientById(int id) {
        return clientRepository.findById(id).get();
    }

    @Override
    public Client saveClientById(Client client, int id) throws Exception {
        Client updatedClient;
        Optional<Client> optionalUpdatedClient = clientRepository.findById(id);
        if (optionalUpdatedClient.isPresent()) {
            updatedClient = optionalUpdatedClient.get();
            updatedClient.setId(client.getId());
            updatedClient.setJobs(client.getJobs());
            updatedClient.setNotes(client.getNotes());
            updatedClient.setStatus(client.getStatus());

        } else {
            throw new Exception("Couldn’t update client, because he didn’t exist !");
        }
        return clientRepository.saveAndFlush(updatedClient);
    }

    @Override
    public List<Client> findAllByAssignedUser(User user) {
        return clientRepository.findAllByAssignedUser(user);
    }

    @Override
    public List<Client> findAllByAuthor(User user) {
        return clientRepository.findAllByAuthor(user);
    }
}
