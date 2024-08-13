package BUMIL.Secondhand_Library.domain.member.entity;

import BUMIL.Secondhand_Library.domain.board.entity.BoardEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="member")
public class MemberEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String memberName;

    private String email;

    private String profileImage;

    private Long outhId;

    private String refreshToken;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BoardEntity> boards;

}