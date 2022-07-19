package vadim.andreich.DAO;

import org.hibernate.Hibernate;
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
public class BooksDAO {
    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;

    @Autowired
    public BooksDAO(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
    }
    @Transactional(readOnly = true)
    public List<Book> getAll() {
        Session session = sessionFactory.getCurrentSession();
        //return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
        System.out.println(session.createQuery("SELECT b from Book b", Book.class).getResultList());
        return session.createQuery("SELECT b from Book b", Book.class).getResultList();
    }
    @Transactional(readOnly = true)
    public Optional<List<Book>> findByPerson(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        Hibernate.initialize(person);
        System.out.println(person.getBooks());
        return Optional.ofNullable(session.get(Person.class, id).getBooks());
        //return jdbcTemplate.query("SELECT * FROM book where Person_idPerson = ?", new BeanPropertyRowMapper<>(Book.class), id);
        //return null;
    }
    @Transactional(readOnly = true)
    public Optional<Book> findbyid(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        Hibernate.initialize(book);
        return Optional.ofNullable(book);
        //return jdbcTemplate.query("SELECT * FROM book where idBook = ?", new BeanPropertyRowMapper<>(Book.class) ,id).stream().findAny().orElse(null);
        //return null;
    }
    @Transactional(readOnly = true)
    public Optional<Person> findOwner(int id) {
        Session session = sessionFactory.getCurrentSession();
        System.out.println(session.get(Book.class, id).getOwner());
        //return jdbcTemplate.query(" select name, BookName,author from book inner join person on id = Person_idPerson and idBook = ?;",
        //      new BeanPropertyRowMapper<>(Person.class), id).stream().findAny();
        return Optional.ofNullable(session.get(Book.class, id).getOwner());
    }
    @Transactional
    public void deleteBookById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        session.remove(book);
        Person person = session.get(Book.class, id).getOwner();
        person.getBooks().remove(book);

        //jdbcTemplate.update("DELETE FROM Book WHERE idBook = ?", id);
    }
    @Transactional
    public void editBook(int id, Book book) {
        Session session = sessionFactory.getCurrentSession();
        Book book1 = session.get(Book.class, id);
        book1.setBookName(book.getBookName());
        book1.setAuthor(book.getAuthor());
        book1.setYear(book.getYear());
        //jdbcTemplate.update("UPDATE BOOK set BookName = ?, author = ?, year = ? WHERE idBook = ?",
        // book.getBookName(), book.getAuthor(), book.getYear(), id);
    }
    @Transactional
    public void addBook(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(book);
        //jdbcTemplate.update("INSERT INTO book (BookName,author,year,Person_idPerson) VALUES  (?, ?, ?, ?)", book.getBookName(),book.getAuthor(),book.getYear(),0);
    }

    public Optional<Object> findByName(String bookName) {
        return Optional.ofNullable(jdbcTemplate.query("SELECT * FROM book WHERE bookName = ?", new BeanPropertyRowMapper<>(Book.class), bookName).stream().findAny().orElse(null));
    }
    @Transactional
    public void releaseBook(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        book.setOwner(null);
        //jdbcTemplate.update("UPDATE book SET Person_idPerson = NULL WHERE idBook = ?", id);
    }
    @Transactional
    public void giveBook(int idBook, int idPerson) {
        Session session = sessionFactory.getCurrentSession();
        session.get(Book.class, idBook).setOwner(session.get(Person.class, idPerson));
        //jdbcTemplate.update("UPDATE book SET Person_idPerson = ? WHERE idBook = ?", idPerson, idBook);
    }
}
