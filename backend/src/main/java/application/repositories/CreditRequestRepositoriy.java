package application.repositories;

import application.models.CreditRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRequestRepositoriy extends JpaRepository<CreditRequest, Long> {

}
