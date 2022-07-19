package vadim.andreich.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import vadim.andreich.models.Book;
import vadim.andreich.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BooksDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BooksDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAll() {
        //return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    return null;
    }
    public List<Book> findByPerson(int id){
        return jdbcTemplate.query("SELECT * FROM book where Person_idPerson = ?", new BeanPropertyRowMapper<>(Book.class), id);
    //return null;
    }

    public Book findbyid(int id) {
        //return jdbcTemplate.query("SELECT * FROM book where idBook = ?", new BeanPropertyRowMapper<>(Book.class) ,id).stream().findAny().orElse(null);
    return null;
    }

    public Optional<Person> findOwner(int id) {
        //return jdbcTemplate.query(" select name, BookName,author from book inner join person on id = Person_idPerson and idBook = ?;",
          //      new BeanPropertyRowMapper<>(Person.class), id).stream().findAny();
   return null;
    }

    public void deleteBookById(int id) {
        //jdbcTemplate.update("DELETE FROM Book WHERE idBook = ?", id);
    }

    public void editBook(int id,Book book) {
        //jdbcTemplate.update("UPDATE BOOK set BookName = ?, author = ?, year = ? WHERE idBook = ?",
               // book.getBookName(), book.getAuthor(), book.getYear(), id);
    }

    public void addBook(Book book) {
        //jdbcTemplate.update("INSERT INTO book (BookName,author,year,Person_idPerson) VALUES  (?, ?, ?, ?)", book.getBookName(),book.getAuthor(),book.getYear(),0);
    }

    public Optional<Object> findByName(String bookName) {
        //return Optional.ofNullable(jdbcTemplate.query("SELECT * FROM book WHERE bookName = ?", new BeanPropertyRowMapper<>(Book.class), bookName).stream().findAny().orElse(null));
    return null;
    }
    public void releaseBook(int id){
        //jdbcTemplate.update("UPDATE book SET Person_idPerson = NULL WHERE idBook = ?", id);
    }
    public void giveBook(int idBook, int idPerson){
        //jdbcTemplate.update("UPDATE book SET Person_idPerson = ? WHERE idBook = ?", idPerson, idBook);
    }
}
