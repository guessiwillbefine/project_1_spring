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
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PeopleDAO(SessionFactory sessionFactory, JdbcTemplate jdbcTemplate) {
        this.sessionFactory = sessionFactory;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(readOnly = true)
    public List<Person> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("Select p from Person p", Person.class).getResultList();
    }
    @Transactional(readOnly = true)
    public Person findById(int id) {
//        return jdbcTemplate.query("SELECT * FROM person WHERE id = ?",
//                new BeanPropertyRowMapper<>(Person.class), id).
//                stream().findAny().orElse(null);
//    }
        Session session = sessionFactory.getCurrentSession();
        System.out.println(session.get(Person.class, id));
        return session.get(Person.class, id);
    }
    @Transactional
    public void addPerson(Person person) {

        Session session = sessionFactory.getCurrentSession();
        session.persist(person);
    }

    public Optional<Person> findByName(String name) {
        return jdbcTemplate.query("SELECT * FROM person WHERE name = ?",
                new BeanPropertyRowMapper<>(Person.class), name).stream().findAny();
    }
    @Transactional
    public void deleteBookById(int id) {
            Session session = sessionFactory.getCurrentSession();
            List<Book> books = session.get(Person.class, id).getBooks();
    }
    @Transactional
    public void editPerson(int id, Person person) {
        Session session = sessionFactory.getCurrentSession();
        Person person1 = session.get(Person.class, id);
        person1.setName(person.getName());
        person1.setBirthday(person.getBirthday());
    }
}
