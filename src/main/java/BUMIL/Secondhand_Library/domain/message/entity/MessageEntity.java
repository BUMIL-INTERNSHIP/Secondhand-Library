package BUMIL.Secondhand_Library.domain.message.entity;

import BUMIL.Secondhand_Library.domain.chatRoom.entity.ChatRoomEntity;
import BUMIL.Secondhand_Library.domain.member.entity.MemberEntity;
import BUMIL.Secondhand_Library.global.basic.BasicEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="message")
public class MessageEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatRoom_id")
    private ChatRoomEntity chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private MemberEntity sender;

    private String content;

    @Builder
    public MessageEntity(LocalDateTime createdAt, LocalDateTime updatedAt, ChatRoomEntity chatRoom, MemberEntity sender, String content) {
        super(createdAt, updatedAt);
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.content = content;
    }

    public static MessageEntity createMessage(ChatRoomEntity chatRoom,MemberEntity sender,String content){
        return MessageEntity.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .content(content)
                .build();
    }

}
