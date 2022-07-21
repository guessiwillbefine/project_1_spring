package vadim.andreich.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vadim.andreich.models.Book;
import vadim.andreich.models.Person;
import vadim.andreich.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final PeopleService peopleService;

    @Autowired
    public BookService(BookRepository bookRepository, PeopleService peopleService) {
        this.bookRepository = bookRepository;
        this.peopleService = peopleService;
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(int id) {
        return bookRepository.findById(id);
    }
    public List<Book> findByPerson(int id) {
        Person person = peopleService.findById(id).get();
        return person.getBooks();
    }
    public Person findOwner(int id){
        System.out.println(bookRepository.findOwnerById(id).getOwner() + " <--------");
       return bookRepository.findOwnerById(id).getOwner();
    }
    @Transactional
    public void deleteById(int id){
        bookRepository.deleteById(id);
    }
    @Transactional
    public void addBook(Book book){
        bookRepository.save(book);
    }
    @Transactional
    public void editBook(int id,Book updBook){
        updBook.setIdBook(id);
        bookRepository.save(updBook);
    }
}