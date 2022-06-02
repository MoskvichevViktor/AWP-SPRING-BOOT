package application.controllers.v1;

import application.exception.AwpException;
import application.models.Contract;
import application.services.ContractService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
