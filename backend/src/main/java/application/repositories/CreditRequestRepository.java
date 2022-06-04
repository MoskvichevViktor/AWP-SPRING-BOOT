package application.repositories;

import application.models.CreditRequest;
import application.models.CreditResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditRequestRepository extends JpaRepository<CreditRequest, Long> {

    Optional<CreditRequest> findByCreditResponse(CreditResponse response);
}
