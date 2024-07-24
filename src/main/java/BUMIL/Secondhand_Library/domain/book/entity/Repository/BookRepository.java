package BUMIL.Secondhand_Library.domain.book.entity.Repository;

import BUMIL.Secondhand_Library.domain.book.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface

BookRepository extends JpaRepository<BookEntity,Long> {
    BookEntity findByBookName(String bookName);
}
