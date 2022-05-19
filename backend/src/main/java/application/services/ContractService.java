package application.services;

import application.models.Client;
import application.models.Contract;
import application.models.CreditResponse;
import application.repositories.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.CacheResponse;
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

//    public Client findClientByClientId(Long clientId) {
//        Client client = contractRepository.findClientByClientId(clientId);
//        System.out.format("ID = ?. Имя клиента:  ?",client.getName()  , clientId);
//        return contractRepository.findClientByClientId(clientId);
//    }

//    public CreditResponse findCreditResponseByCreditResponseId(Long creditResponseId) {
//        return contractRepository.finCreditResponseByResponseId(creditResponseId);
//    }
}
