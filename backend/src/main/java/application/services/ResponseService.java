package application.services;

import application.models.CreditResponse;
import application.repositories.CreditResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponseService {
    private final CreditResponseRepository creditResponseRepository;

    List<CreditResponse> findCreditResponsesByStatus(String status) {
        return creditResponseRepository.findCreditResponsesByStatus(status);
    }

    public List<CreditResponse> findAll() {
        return creditResponseRepository.findAll();
    }
}
