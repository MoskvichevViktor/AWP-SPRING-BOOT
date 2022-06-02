package application.services;

import application.constants.ContractStatus;
import application.models.Contract;
import application.repositories.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;

    public List<Contract> findAll() {
        return contractRepository.findAll();
    }

    public Optional<Contract> findById(Long id) {
        return contractRepository.findById(id);
    }

    public ResponseEntity<?> findContractsByStatus(ContractStatus status) {
        return new ResponseEntity<>(contractRepository.findContractsByStatus(status), HttpStatus.OK);
    }
}
