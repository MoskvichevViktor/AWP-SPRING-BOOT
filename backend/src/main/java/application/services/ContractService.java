package application.services;

import application.models.Contract;
import application.repositories.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;

    public List<Contract> findAll() {
        return contractRepository.findAll();
    }

    public List<Contract> findContractsByStatus(String signed) {
        return contractRepository.findContractsByStatus(signed);
    }

}
