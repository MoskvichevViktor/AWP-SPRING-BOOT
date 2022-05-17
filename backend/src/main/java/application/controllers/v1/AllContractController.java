package application.controllers.v1;

import application.models.Contract;
import application.services.ContractService;
import application.services.CreditResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/allContract")
@RequiredArgsConstructor
public class AllContractController {

    private final ContractService contractService;
    private final CreditResponseService creditResponseService;

}
