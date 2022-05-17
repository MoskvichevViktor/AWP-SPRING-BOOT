package application.services;

import application.repositories.CreditResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditResponseService {
    private final CreditResponseRepository creditResponseRepository;
}
