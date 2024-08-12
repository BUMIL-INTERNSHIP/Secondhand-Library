package BUMIL.Secondhand_Library.domain.member.entity;

import BUMIL.Secondhand_Library.domain.board.entity.BoardEntity;
import BUMIL.Secondhand_Library.domain.chatRoom.entity.ChatRoomEntity;
import BUMIL.Secondhand_Library.domain.message.entity.MessageEntity;
import BUMIL.Secondhand_Library.global.basic.BasicEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ChatRoomEntity> receivedChatRooms;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ChatRoomEntity> sentChatRooms;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MessageEntity> sentMessages;

    //해당 회원이 참여하고 있는 모든 채팅방 리스트
    public List<ChatRoomEntity> getChatRooms() {
        return Stream.concat(receivedChatRooms.stream(), sentChatRooms.stream())
                .collect(Collectors.toList());
    }

}