package application.repositories;

import application.models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository <Contract, Integer> {

    List<Contract> findContractsByStatus(String status);

}
