package com.pl.Ecommerce.unit;

import com.pl.Ecommerce.domain.Client;
import com.pl.Ecommerce.repository.ClientRepository;
import com.pl.Ecommerce.requests.CreateClientRequest;
import com.pl.Ecommerce.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    private ClientRepository clientRepository;
    private ClientService clientService;

    @BeforeEach
    void setup() {
        clientRepository = mock(ClientRepository.class);
        clientService = new ClientService(clientRepository);
    }

    @Test
    void createClient_shouldSaveClient() {
        CreateClientRequest req = new CreateClientRequest();
        req.setName("Pawel");
        req.setSurname("Test");
        req.setEmail("pawel@example.com");
        req.setPhoneNumber("123456789");
        req.setStreet("Testowa");
        req.setHouseNumber("1");
        req.setCity("Bydgoszcz");
        req.setCountry("PL");
        req.setPostalCode("85-567");

        Client saved = new Client();
        saved.setClientId(1L);
        saved.setEmail("pawel@example.com");

        when(clientRepository.save(any(Client.class))).thenReturn(saved);

        Client client = clientService.createClient(req);

        assertThat(client.getClientId()).isEqualTo(1L);
        assertThat(client.getEmail()).isEqualTo("pawel@example.com");

        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void getClientById_shouldReturnClient() {
        Client client = new Client();
        client.setClientId(2L);

        when(clientRepository.findById(2L)).thenReturn(Optional.of(client));

        Client found = clientService.getClientById(2L);

        assertThat(found).isNotNull();
        assertThat(found.getClientId()).isEqualTo(2L);
    }

    @Test
    void getClient_shouldThrowWhenNotFound() {
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> clientService.getClientById(99L));
    }
}
