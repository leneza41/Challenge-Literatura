package lenez.liter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import lenez.liter.models.Book;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContaining(String title);
    List<Book> findByLanguagesContaining(String language);
}
