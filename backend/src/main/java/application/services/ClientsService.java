package application.services;

import application.models.Client;
import application.models.security.User;
import application.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientsService {
    private final ClientRepository clientRepository;

    public ResponseEntity<?> save(Client client) {
        clientRepository.save(client);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public void delete(Client client) {
        clientRepository.delete(client);
    }

    public Optional<Client> getById(Long id) {
        return clientRepository.findById(id);
    }
}
