package BUMIL.Secondhand_Library.domain.message.repository;

import BUMIL.Secondhand_Library.domain.member.entity.MemberEntity;
import BUMIL.Secondhand_Library.domain.message.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity,Long> {
    List<MessageEntity> findAllByReceiver(MemberEntity member);
    List<MessageEntity> findAllBySender(MemberEntity member);
}
