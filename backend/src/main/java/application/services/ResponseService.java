package application.services;

import application.models.CreditResponse;
import application.repositories.CreditResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResponseService {
    private final CreditResponseRepository responseRepository;

    public List<CreditResponse> findAll() {
        return responseRepository.findAll();
    }

    public Optional<CreditResponse> findById(Long id) {
        return responseRepository.findById(id);
    }
}
