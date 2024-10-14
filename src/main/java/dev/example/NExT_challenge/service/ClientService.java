package dev.example.NExT_challenge.service;

import dev.example.NExT_challenge.domain.client.Client;
import dev.example.NExT_challenge.domain.client.ClientRequestDTO;
import dev.example.NExT_challenge.domain.client.UpdateClientRequestDTO;
import dev.example.NExT_challenge.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client createClient(ClientRequestDTO requestDTO) {
        Client newClient = new Client();

        newClient.setAge(requestDTO.age());
        newClient.setName(requestDTO.name());
        newClient.setDependents(requestDTO.dependents());
        newClient.setIncome(requestDTO.income());
        newClient.setMaritalStatus(requestDTO.maritalStatus());

        clientRepository.save(newClient);

        return newClient;
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public Client findById(UUID id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + id));
    }

    public Client deleteUser(UUID id) {
       Client client =  clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + id));

       clientRepository.delete(client);

       return client;
    }

    public Client updateUser(UUID id, UpdateClientRequestDTO requestDTO) {
        Client updateClient = this.clientRepository.findById(id).orElseThrow();

        updateClient.setName(requestDTO.name());
        updateClient.setAge(requestDTO.age());
        updateClient.setDependents(requestDTO.dependents());
        updateClient.setIncome(requestDTO.income());
        updateClient.setMaritalStatus(requestDTO.maritalStatus());

        clientRepository.save(updateClient);

        return updateClient;
    }
}

