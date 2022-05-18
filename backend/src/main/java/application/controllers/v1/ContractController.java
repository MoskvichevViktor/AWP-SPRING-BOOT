package application.controllers.v1;

import application.models.Client;
import application.models.Contract;
import application.services.ContractService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
