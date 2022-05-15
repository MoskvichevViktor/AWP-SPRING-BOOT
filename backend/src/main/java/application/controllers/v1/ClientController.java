package application.controllers.v1;


import application.exception.ResourceNotFoundException;
import application.models.Client;
import application.services.ClientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientsService clientsService;

    @GetMapping("")
    public List<Client> getAllClients() {
        return clientsService.findAll();
    }

    @PostMapping("/update")
    public ResponseEntity<?> saveOrUpdate(@RequestBody Client client) {
        return clientsService.save(client);
    }

    @PostMapping("/del")
    public ResponseEntity<?> delete(@RequestBody Client client) {
        clientsService.delete(client);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/id")
    public Client getById(@RequestBody Long id) {
        return clientsService.getById(id).orElseThrow(() -> new ResourceNotFoundException("No client with Id: " + id));
    }

}