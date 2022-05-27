package application.services;

import application.models.Client;
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

//    public ResponseEntity<?> delete(Long id) {
//        Client client = clientRepository.findById(id).orElseThrow(() ->
//                new ResourceNotFoundException("Client with id:" + id + " not found.")
//        );
//        if (client == null) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        clientRepository.delete(client);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    public Optional<Client> getById(Long id) {
        return clientRepository.findById(id);
    }
}
