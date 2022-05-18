package application.controllers.v1;

import application.services.ContractService;
import application.services.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/allContract")
@RequiredArgsConstructor
public class AllContractController {

    private final ContractService contractService;
    private final ResponseService creditResponseService;

}
