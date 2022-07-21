package vadim.andreich.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vadim.andreich.models.Person;
import vadim.andreich.repository.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> getAll(){
        return peopleRepository.findAll();
    }

    public Optional<Person> findById(int id){
        return peopleRepository.findById(id);
    }
    @Transactional
    public void addPerson(Person person){
        peopleRepository.save(person);
    }

    @Transactional
    public void deletePerson(int id){
        peopleRepository.deleteById(id);
    }
    @Transactional
    public void updatePerson(int id, Person updPerson){
        updPerson.setId(id);
        peopleRepository.save(updPerson);
    }
    public Person findByName(String name){
        return peopleRepository.findPersonByName(name);
    }

}
