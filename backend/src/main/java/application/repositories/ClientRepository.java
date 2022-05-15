package application.repositories;

import application.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByName(String name);

    List<Client> findByPhone(String phone);
    List<Client> findByPassport(String passport);

    List<Client> findAll();


}
