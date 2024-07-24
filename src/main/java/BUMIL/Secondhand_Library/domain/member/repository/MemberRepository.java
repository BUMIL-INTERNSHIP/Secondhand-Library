package BUMIL.Secondhand_Library.domain.member.repository;

import BUMIL.Secondhand_Library.domain.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
    MemberEntity findByOuthId(Long outhId);
}
