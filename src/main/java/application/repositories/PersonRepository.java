package application.repositories;

import application.models.Contract;
import application.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    List<Person> findPeopleByName(String name);
    List<Person> findPeopleByPhone(String phone);
    List<Person> findPeopleByPasport(String pasport);

}
