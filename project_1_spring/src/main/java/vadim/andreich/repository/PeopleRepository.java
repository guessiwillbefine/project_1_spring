package vadim.andreich.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vadim.andreich.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
   Person findPersonByName(String name);
}
