package application.services;

import application.models.CreditRequest;
import application.repositories.CreditRequestRepositoriy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final CreditRequestRepositoriy requestRepositoriy;

    public List<CreditRequest> findAll() {
        return requestRepositoriy.findAll();
    }

    public Optional<CreditRequest> findById(Long id) {
        return requestRepositoriy.findById(id);
    }
}
