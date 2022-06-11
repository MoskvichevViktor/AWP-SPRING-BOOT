package application.controllers.v1;

import application.constants.ContractStatus;
import application.dto.ContractCreateDto;
import application.dto.ContractDto;
import application.dto.CreditResponseDto;
import application.exception.AwpException;
import application.services.ContractService;
import application.services.generateContract.GenerateContractService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/contracts")
@AllArgsConstructor
public class ContractController {
    private final ContractService contractService;
    private final GenerateContractService generateContractService;

    @GetMapping("")
    public List<ContractDto> getAll() {
        return contractService.findAll();
    }

    @SneakyThrows
    @GetMapping("/{id}")
    public ContractDto getById(@PathVariable Long id) {
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
    public List<ContractDto> findByClientId(@PathVariable Long clientid) {
        return contractService.findByClientId(clientid);
    }

    @SneakyThrows
    @PutMapping("/status/{id}/{status}")
    public ResponseEntity<?> setStatus(@PathVariable Long id, @PathVariable ContractStatus status) {
        return contractService.setStatus(id, status);
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public void create(@RequestBody ContractCreateDto contract) {
        contractService.save(contract);
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generate(@RequestBody CreditResponseDto creditResponseDto) throws IOException, InvalidFormatException {
        generateContractService.generate(creditResponseDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping()
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void update(@RequestBody ContractDto contractDto) {
        contractService.update(contractDto);
    }


}
