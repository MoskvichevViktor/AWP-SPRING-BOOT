package application.controllers.v1;


import application.exception.ResourceNotFoundException;
import application.models.Client;
import application.services.ClientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
//@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER','ROLE_MAIN_MANAGER')")
public class ClientController {
    private final ClientsService clientsService;

    @GetMapping("")
    public List<Client> getAllClients() {
        return clientsService.findAll();
    }

    @PostMapping("")
    public ResponseEntity<?> saveOrUpdate(@RequestBody Client client) {
        return clientsService.save(client);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> delete(@PathVariable Long id) {
//        return clientsService.delete(id);
//    }

    @GetMapping("/{id}")
    public Client getById(@PathVariable Long id) {
        return clientsService.
                getById(id).
                orElseThrow(() -> new ResourceNotFoundException("No client with Id: " + id));
    }


}