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
import java.util.Objects;
import java.util.Optional;

@Component
public class BooksDAO {
//    private final SessionFactory sessionFactory;
//
//    @Autowired
//    public BooksDAO(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
//    @Transactional(readOnly = true)
//    public List<Book> getAll() {
//        Session session = sessionFactory.getCurrentSession();
//        System.out.println(session.createQuery("SELECT b from Book b", Book.class).getResultList());
//        return session.createQuery("SELECT b from Book b", Book.class).getResultList();
//    }
//    @Transactional(readOnly = true)
//    public List<Book> findByPerson(int id) {
//        Session session = sessionFactory.getCurrentSession();
//        Person person = session.get(Person.class, id);
//        Hibernate.initialize(person.getBooks());
//        return person.getBooks();
//    }
//    @Transactional(readOnly = true)
//    public Book findbyid(int id) {
//        Session session = sessionFactory.getCurrentSession();
//        Book book = session.get(Book.class, id);
//        Hibernate.initialize(book);
//        return book;
//    }
//    @Transactional(readOnly = true)
//    public Person findOwner(int id) {
//        Session session = sessionFactory.getCurrentSession();
//        Person person = session.get(Book.class, id).getOwner();
//        Hibernate.initialize(person);
//        return person;
//    }
//    @Transactional
//    public void deleteBookById(int id) {
//        Session session = sessionFactory.getCurrentSession();
//        Book book = session.get(Book.class, id);
//        session.remove(book);
//    }
//    @Transactional
//    public void editBook(int id, Book book) {
//        Session session = sessionFactory.getCurrentSession();
//        Book book1 = session.get(Book.class, id);
//        book1.setBookName(book.getBookName());
//        book1.setAuthor(book.getAuthor());
//        book1.setYear(book.getYear());
//    }
//    @Transactional
//    public void addBook(Book book) {
//        Session session = sessionFactory.getCurrentSession();
//        session.persist(book);
//        //jdbcTemplate.update("INSERT INTO book (BookName,author,year,Person_idPerson) VALUES  (?, ?, ?, ?)", book.getBookName(),book.getAuthor(),book.getYear(),0);
//    }
//
//    public Optional<Object> findByName(String bookName) {
//        List<Book> books = getAll();
//        for (Book b : books)
//            if (Objects.equals(b.getBookName(), bookName))
//                return Optional.of(b);
//        return Optional.empty();
//    }
//    @Transactional
//    public void releaseBook(int id) {
//        Session session = sessionFactory.getCurrentSession();
//        Book book = session.get(Book.class, id);
//        Person person = book.getOwner();
//        person.getBooks().remove(book);
//        book.setOwner(null);
//    }
//    @Transactional
//    public void giveBook(int idBook, int idPerson) {
//        Session session = sessionFactory.getCurrentSession();
//        session.get(Book.class, idBook).setOwner(session.get(Person.class, idPerson));
//    }
}
