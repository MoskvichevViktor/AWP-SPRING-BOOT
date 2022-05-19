package application.controllers.v1;

import application.models.Client;
import application.models.Contract;
import application.models.CreditResponse;
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

//    @GetMapping("/client/{id}")
//    public Client findByClientId(@PathVariable Long id) {
//        return contractService.findClientByClientId(id);
//    }

//    @GetMapping("/request")
//    public CreditResponse findByCreditResponseId(Long creditResponseId) {
//        return contractService.findCreditResponseByCreditResponseId(creditResponseId);
//    }
}
