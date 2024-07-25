package BUMIL.Secondhand_Library.domain.book.Repository;

import BUMIL.Secondhand_Library.domain.book.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity,Long> {

    // bookName이 존재하는지 여부를 반환하는 메서드
    boolean existsByBookName(String bookName);
}
