package application.controllers.v1;

import application.exception.ResourceNotFoundException;
import application.models.Contract;
import application.services.ContractService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contract")
@AllArgsConstructor

public class ContractController {
    private final ContractService contractService;


    @GetMapping("/all")
    public List<Contract> getAllClients() {
        return contractService.findAll();
    }

    @GetMapping("/{id}")
    public Contract getById(@PathVariable Long id) {
        return contractService.
                findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No contract with Id: " + id));
    }
}
