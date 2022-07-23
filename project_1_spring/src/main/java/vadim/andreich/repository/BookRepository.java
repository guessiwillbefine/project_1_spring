package vadim.andreich.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vadim.andreich.models.Book;
import vadim.andreich.models.Person;

import javax.validation.constraints.Pattern;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findOwnerById(int i);
    Book findDistinctByBookName(String name);
    List<Book> findByBookNameStartingWith(String request);
}
