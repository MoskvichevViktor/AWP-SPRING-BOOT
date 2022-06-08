package application.services;

import application.constants.ContractStatus;
import application.constants.RequestStatus;
import application.dto.ContractDto;
import application.dto.ContractInputDto;
import application.exception.AwpException;
import application.models.Contract;
import application.models.CreditResponse;
import application.repositories.ContractRepository;
import application.repositories.CreditResponseRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;
    private final CreditResponseRepository creditResponseRepository;

    public List<ContractDto> findAll() {
        return contractRepository.findAll().stream()
                .map(contract -> ContractDto.valueOf(contract))
                .collect(Collectors.toList());
    }

    public Optional<ContractDto> findById(Long id) {
        return contractRepository.findById(id)
                .map(contract -> ContractDto.valueOf(contract));
    }

    public ResponseEntity<?> findByStatus(ContractStatus status) {
        return new ResponseEntity<>(contractRepository.findContractsByStatus(status)
                .stream()
                .map(contract -> ContractDto.valueOf(contract))
                , HttpStatus.OK);
    }

    public List<ContractDto> findByClientId(Long clientId) {
        return contractRepository.findByClientId(clientId)
                .stream()
                .map(contract -> ContractDto.valueOf(contract))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public ResponseEntity<?> setStatus(Long id, ContractStatus status) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new AwpException("Договора с id:" + id + " не найдено."));
        contract.setStatus(status);
        contractRepository.save(contract);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @SneakyThrows
    public void save(ContractInputDto contractDto) {
        Long contractId = contractDto.getContractId();
        CreditResponse creditResponse = creditResponseRepository.findById(contractId).
                orElseThrow(() -> new AwpException("CreditResponse Id:" + contractId + "not found"));
        if (creditResponse.getStatus() != RequestStatus.CONFIRMED) {
            throw new AwpException("The response status must be " + RequestStatus.CONFIRMED);
        }
        Contract contract = new Contract();
        contract.setClient(creditResponse.getClient());
        contract.setPeriod(contractDto.getPeriod());
        contract.setSum(contractDto.getSum());
        contract.setPercent(contractDto.getPercent());
        contract.setStatus(ContractStatus.WAITING_SIGNING);
        contractRepository.save(contract);
    }
}
