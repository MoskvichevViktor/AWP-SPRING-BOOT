package application.services;

import application.constants.ContractStatus;
import application.constants.CreditResponseStatus;
import application.dto.ContractCreateDto;
import application.dto.ContractDto;
import application.dto.ContractStatusChangeDTO;
import application.exception.AwpException;
import application.models.Contract;
import application.models.CreditResponse;
import application.repositories.ClientRepository;
import application.repositories.ContractRepository;
import application.repositories.CreditResponseRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;
    private final CreditResponseRepository creditResponseRepository;
    private final ClientRepository clientRepository;

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
    @Transactional
    public void save(ContractCreateDto contractDto) {
        Long responseId = contractDto.getResponseId();
        CreditResponse creditResponse = creditResponseRepository.findById(responseId).
                orElseThrow(() -> new AwpException("CreditResponse Id:" + responseId + "not found"));
        if (creditResponse.getStatus() != CreditResponseStatus.CONFIRMED) {
            throw new AwpException("The response status must be " + CreditResponseStatus.CONFIRMED);
        }
        Contract contract = new Contract();
        contract.setClient(creditResponse.getClient());
        contract.setPeriod(creditResponse.getPeriod());
        contract.setSum(creditResponse.getSum());
        contract.setPercent(creditResponse.getPercent());
        contract.setStatus(ContractStatus.WAITING_SIGNING);
        contractRepository.save(contract);

        creditResponse.setStatus(CreditResponseStatus.PROCESSED);
        creditResponseRepository.save(creditResponse);
    }

    @SneakyThrows
    public void update(ContractStatusChangeDTO contractDto) {
        Long id = contractDto.getContractId();
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new AwpException("Договор с Id: " + id + " не существует."));

        ContractStatus contractStatus = contract.getStatus();
        ContractStatus contractStatusDto = contractDto.getStatus();

        if (!contractStatus.equals(ContractStatus.WAITING_SIGNING) && contractStatusDto.equals(ContractStatus.ACTIVE)) {
            throw new AwpException("Договор со статусом " + contractStatus + " не может быть подписан.");
        }

        if (!contractStatus.equals(ContractStatus.ACTIVE) && contractStatusDto.equals(ContractStatus.COMPLETED)) {
            throw new AwpException("Договор со статусом " + contractStatus + " не может быть завершен.");
        }

        contract.setStatus(contractDto.getStatus());
        contractRepository.save(contract);
    }
}
