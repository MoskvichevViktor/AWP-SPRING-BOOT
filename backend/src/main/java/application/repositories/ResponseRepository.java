package application.repositories;

import application.models.CreditResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseRepository extends JpaRepository<CreditResponse, Integer> {
    List<CreditResponse> findCreditResponsesByStatus(String status);
}
