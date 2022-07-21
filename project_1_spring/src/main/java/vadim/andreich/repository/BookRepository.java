package vadim.andreich.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vadim.andreich.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
