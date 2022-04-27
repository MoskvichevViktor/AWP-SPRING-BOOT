package application.repositories;

import application.models.Contract;
import application.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    List<Person> findPeopleByName(String name);
    List<Person> findPeopleByPhone(String phone);
    List<Person> findPeopleByPasport(String pasport);

    List<Person> findAll();

}
