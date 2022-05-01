package application.repositories;

import application.models.CreditRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<CreditRequest, Integer> {
}
