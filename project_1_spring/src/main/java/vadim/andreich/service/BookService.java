package vadim.andreich.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Literal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vadim.andreich.models.Book;
import vadim.andreich.models.Person;
import vadim.andreich.repository.BookRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final PeopleService peopleService;
    private static final int OVERDUE_TIME = 432000;

    @Autowired
    public BookService(BookRepository bookRepository, PeopleService peopleService) {
        this.bookRepository = bookRepository;
        this.peopleService = peopleService;
    }

    public List<Book> getBooksByLetter(String request) {
        System.out.println(bookRepository.findByBookNameStartingWith(request));
        List<Book> books = bookRepository.findByBookNameStartingWith(request);
        for (Book book : books)
            System.out.println(book.getOwner());
        return books;
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(int id) {
        return bookRepository.findById(id);
    }

    public List<Book> findByPerson(int id) {
        Person person = peopleService.findById(id).get();
        Hibernate.initialize(person.getBooks());
        person.getBooks().forEach(a -> {
            System.out.println(a.getGivingTime());
            if (System.currentTimeMillis() - a.getGivingTime().getTime() > OVERDUE_TIME){
                a.setOverdue(true);
            }
        });
        return person.getBooks();
    }

    public Person findOwner(int id) {
        System.out.println(bookRepository.findOwnerById(id).getOwner() + " <--------");
        return bookRepository.findOwnerById(id).getOwner();
    }

    @Transactional
    public void deleteById(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void editBook(int id, Book updBook) {
        updBook.setIdBook(id);
        bookRepository.save(updBook);
    }

    @Transactional
    public void releaseBook(int id) {
        findById(id).get().setOwner(null);
    }

    @Transactional
    public void giveBook(int bookId, int idPerson) {
        Person person = peopleService.findById(idPerson).get();
        findById(bookId).get().setOwner(person);
        findById(bookId).get().setGivingTime(new Date());
    }

    @Transactional
    public Book findByName(String bookName) {
        return bookRepository.findDistinctByBookName(bookName);
    }
}