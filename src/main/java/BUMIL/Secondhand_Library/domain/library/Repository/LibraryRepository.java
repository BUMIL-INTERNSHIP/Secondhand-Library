package BUMIL.Secondhand_Library.domain.library.repository;

import BUMIL.Secondhand_Library.domain.library.entity.LibraryEntity;
import BUMIL.Secondhand_Library.domain.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepository extends JpaRepository<LibraryEntity, Long> {
    LibraryEntity findByMember(MemberEntity member);
}

