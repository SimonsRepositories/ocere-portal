package com.ocere.portal.service.Impl;

import com.ocere.portal.enums.Tier;
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
            updatedClient.setTargetMonthlyValue(client.getTargetMonthlyValue());

            updatedClient.getContact().setFirst_name(client.getContact().getFirst_name());
            updatedClient.getContact().setLast_name(client.getContact().getLast_name());
            updatedClient.getContact().setPhone(client.getContact().getPhone());
            updatedClient.getContact().setEmail(client.getContact().getEmail());

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
    public Client findClientByContact(User user) {
        return user.getContact().getClient();
    }

    @Override
    public void updateTier(int id) {
        Client client = findClientById(id);
        if (client.getMonthlySpending() < 1000) {
            client.setTier(Tier.C);
        } else if (client.getMonthlySpending() < 5000) {
            client.setTier(Tier.B);
        } else {
            client.setTier(Tier.A);
        }
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
