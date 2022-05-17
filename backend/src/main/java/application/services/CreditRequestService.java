package application.services;

import application.repositories.CreditRequestRepositoriy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditRequestService {
    private final CreditRequestRepositoriy creditRequestRepositoriy;
}
