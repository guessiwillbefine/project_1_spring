package vadim.andreich.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vadim.andreich.models.Book;
import vadim.andreich.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PeopleDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public PeopleDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select p from Person p", Person.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Person findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, id);
    }

    @Transactional
    public void addPerson(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(person);
    }

    public Optional<Person> findByName(String name) {
        List<Person> people = getAll();
        for (Person p : people) {
            if (p.getName().equals(name)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    @Transactional
    public void editPerson(int id, Person person) {
        Session session = sessionFactory.getCurrentSession();
        Person person1 = session.get(Person.class, id);
        person1.setName(person.getName());
        person1.setBirthday(person.getBirthday());
    }

    @Transactional
    public void deletePerson(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        if (person.getBooks() == null) {
            session.remove(person);
            return;
        }
        for (Book book : person.getBooks())
            book.setOwner(null);
        session.remove(person);
    }
}
