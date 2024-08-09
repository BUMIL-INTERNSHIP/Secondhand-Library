package BUMIL.Secondhand_Library.domain.chatRoom.repository;

import BUMIL.Secondhand_Library.domain.chatRoom.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity,Long> {
}
