package vadim.andreich.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import vadim.andreich.models.Person;
import java.util.List;
import java.util.Optional;

@Component
public class PeopleDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PeopleDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> getAll(){
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person findById(int id){
        return jdbcTemplate.query("SELECT * FROM person WHERE id = ?",
                new BeanPropertyRowMapper<>(Person.class), id).
                stream().findAny().orElse(null);
    }


    public void addPerson(Person person) {
        jdbcTemplate.update("INSERT INTO person (name, birthday) VALUES (?,?)",
                person.getName(),person.getBirthday());
    }

    public Optional<Person> findByName(String name) {
        return jdbcTemplate.query("SELECT * FROM person WHERE name = ?",
                        new BeanPropertyRowMapper<>(Person.class), name).stream().findAny();
    }

    public void deleteBookById(int id) {
        jdbcTemplate.update("DELETE FROM person where id = ?", id);
    }




    public void editPerson(int id, Person person) {
        jdbcTemplate.update("UPDATE person SET name = ?, birthday = ? WHERE id = ?", person.getName(), person.getBirthday(), id);
    }
}
