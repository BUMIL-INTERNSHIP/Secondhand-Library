package BUMIL.Secondhand_Library.domain.board.repository;

import BUMIL.Secondhand_Library.domain.board.entity.BoardEntity;
import BUMIL.Secondhand_Library.domain.book.entity.BookEntity;
import BUMIL.Secondhand_Library.global.CategoryEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    List<BoardEntity> findByCategory(CategoryEnum category);

    Page<BoardEntity> findAllByBookAndCategory(Pageable pageable, BookEntity book, CategoryEnum category);


}
