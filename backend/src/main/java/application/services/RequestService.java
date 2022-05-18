package application.services;

import application.repositories.CreditRequestRepositoriy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final CreditRequestRepositoriy creditRequestRepositoriy;
}
