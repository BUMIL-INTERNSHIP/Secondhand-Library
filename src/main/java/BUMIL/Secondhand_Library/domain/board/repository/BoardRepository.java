package BUMIL.Secondhand_Library.domain.board.repository;

import BUMIL.Secondhand_Library.domain.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}
