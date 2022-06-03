package application.controllers.v1;

import application.constants.ContractStatus;
import application.exception.AwpException;
import application.models.Contract;
import application.services.ContractService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contracts")
@AllArgsConstructor
public class ContractController {
    private final ContractService contractService;

    @GetMapping("")
    public List<Contract> getAllClients() {
        return contractService.findAll();
    }

    @SneakyThrows
    @GetMapping("/{id}")
    public Contract getById(@PathVariable Long id) {
        return contractService.
                findById(id).
                orElseThrow(() -> new AwpException("No contract with Id: " + id));
    }

    @SneakyThrows
    @GetMapping("/status/{status}")
    ResponseEntity<?> findContractsByStatus(@PathVariable ContractStatus status) {
        return contractService.findByStatus(status);
    }

    @GetMapping("/client_id/{clientid}")
    public List<Contract> findByClientId(@PathVariable Long clientid) {
        return contractService.findByClientId(clientid);
    }

    @SneakyThrows
    @PutMapping("/status/{id}/{status}")
    public ResponseEntity<?> setStatus(@PathVariable Long id, @PathVariable ContractStatus status) {
        return contractService.setStatus(id, status);
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Contract contract) {
        return contractService.save(contract);
    }
}
