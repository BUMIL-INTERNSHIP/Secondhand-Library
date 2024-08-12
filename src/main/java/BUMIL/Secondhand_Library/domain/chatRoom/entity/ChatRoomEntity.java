package BUMIL.Secondhand_Library.domain.chatRoom.entity;

import BUMIL.Secondhand_Library.domain.board.entity.BoardEntity;
import BUMIL.Secondhand_Library.domain.book.entity.BookEntity;
import BUMIL.Secondhand_Library.domain.member.entity.MemberEntity;
import BUMIL.Secondhand_Library.domain.message.entity.MessageEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="chatroom")
public class ChatRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @JsonBackReference
    private BookEntity book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Board_id")
    @JsonBackReference
    private BoardEntity board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    @JsonBackReference
    private MemberEntity receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    @JsonBackReference
    private MemberEntity sender;

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<MessageEntity> messages;

    public static ChatRoomEntity createChatRoom(BookEntity book, BoardEntity board, MemberEntity sender, MemberEntity receiver) {
        return ChatRoomEntity.builder()
                .book(book)
                .board(board)
                .sender(sender)
                .receiver(receiver)
                .build();
    }
}
