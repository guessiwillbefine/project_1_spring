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
        //check custom jpa requests
        return null;
    }
    public Optional<Person> findOwner(int id){
        Optional<Book> book = bookRepository.findById(id);
        return null;
    }
}