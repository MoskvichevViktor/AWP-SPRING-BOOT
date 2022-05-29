package application.repositories;

import application.models.CreditResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditResponseRepository extends JpaRepository<CreditResponse, Long> {
    List<CreditResponse> findCreditResponsesByStatus(String status);
}
