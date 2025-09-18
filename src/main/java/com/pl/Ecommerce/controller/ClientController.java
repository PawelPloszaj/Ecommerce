package com.pl.Ecommerce.controller;

import com.pl.Ecommerce.service.ClientService;
import com.pl.Ecommerce.domain.Client;
import com.pl.Ecommerce.dto.ClientDto;
import com.pl.Ecommerce.requests.CreateClientRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientDto> createClient(@Valid @RequestBody CreateClientRequest request) {
        Client client = clientService.createClient(request);
        ClientDto clientDto = new ClientDto(
                client.getClientId(),
                client.getName(),
                client.getSurname(),
                client.getEmail(),
                client.getPhoneNumber(),
                client.getStreet(),
                client.getHouseNumber(),
                client.getApartmentNumber(),
                client.getCity(),
                client.getCountry(),
                client.getPostalCode()
        );
        return ResponseEntity.status(201).body(clientDto);
    }
}
