package com.pl.Ecommerce.service;

import com.pl.Ecommerce.domain.Client;
import com.pl.Ecommerce.repository.ClientRepository;
import com.pl.Ecommerce.requests.CreateClientRequest;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client createClient(CreateClientRequest request) {
        Client client = new Client();
        client.setName(request.getName());
        client.setSurname(request.getSurname());
        client.setEmail(request.getEmail());
        client.setPhoneNumber(request.getPhoneNumber());
        client.setStreet(request.getStreet());
        client.setHouseNumber(request.getHouseNumber());
        client.setApartmentNumber(request.getApartmentNumber());
        client.setCity(request.getCity());
        client.setCountry(request.getCountry());
        client.setPostalCode(request.getPostalCode());
        return clientRepository.save(client);
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
    }

}
