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
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findClientById(int id) {
        return clientRepository.findById(id).get();
    }

    @Override
    public Client createClient(Client client) {
        return clientRepository.saveAndFlush(client);
    }

    @Override
    public void deleteClientById(int id) throws Exception {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
        } else {
            throw new Exception("Couldn't delete client because it didn't exist");
        }
    }

    @Override
    public Client updateClient(Client client, int id) throws Exception {
        Client updatedClient;
        Optional<Client> optionalUpdatedClient = clientRepository.findById(id);

        if (optionalUpdatedClient.isPresent()) {
            updatedClient = optionalUpdatedClient.get();
            updatedClient.setNotes(client.getNotes());
            updatedClient.setJobs(client.getJobs());
            updatedClient.setId(client.getId());
            updatedClient.setAuthor(client.getAuthor());
            updatedClient.setCity(client.getCity());
            updatedClient.setCompanyName(client.getCompanyName());

            updatedClient.getFirstContact().setFirst_name(client.getFirstContact().getFirst_name());
            updatedClient.getFirstContact().setLast_name(client.getFirstContact().getLast_name());
            updatedClient.getFirstContact().setPhone(client.getFirstContact().getPhone());
            updatedClient.getFirstContact().setEmail(client.getFirstContact().getEmail());

            updatedClient.getSecondContact().setFirst_name(client.getSecondContact().getFirst_name());
            updatedClient.getSecondContact().setLast_name(client.getSecondContact().getLast_name());
            updatedClient.getSecondContact().setPhone(client.getSecondContact().getPhone());
            updatedClient.getSecondContact().setEmail(client.getSecondContact().getEmail());

            updatedClient.getThirdContact().setFirst_name(client.getThirdContact().getFirst_name());
            updatedClient.getThirdContact().setLast_name(client.getThirdContact().getLast_name());
            updatedClient.getThirdContact().setPhone(client.getThirdContact().getPhone());
            updatedClient.getThirdContact().setEmail(client.getThirdContact().getEmail());

            updatedClient.setContactUsPage(client.getContactUsPage());
            updatedClient.setAddressLine2(client.getAddressLine2());
            updatedClient.setStreet(client.getStreet());
            updatedClient.setWebsite(client.getWebsite());
        } else {
            throw new Exception("Couldn’t update client because he didn’t exist");
        }
        return clientRepository.saveAndFlush(updatedClient);
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.saveAndFlush(client);
    }

    @Override
    public boolean isClientAlreadyPresent(Client client) {
        return clientRepository.findById(client.getId()).isPresent();
    }


    @Override
    public List<Client> findAllByAuthor(User user) {
        return clientRepository.findAllByAuthor(user);
    }
}
