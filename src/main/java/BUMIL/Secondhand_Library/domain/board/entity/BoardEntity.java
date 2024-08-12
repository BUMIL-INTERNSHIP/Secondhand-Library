package BUMIL.Secondhand_Library.domain.board.entity;

import BUMIL.Secondhand_Library.domain.book.entity.BookEntity;
import BUMIL.Secondhand_Library.domain.member.entity.MemberEntity;
import BUMIL.Secondhand_Library.global.CategoryEnum;
import BUMIL.Secondhand_Library.global.basic.BasicEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id",nullable = false)
    private BookEntity book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",nullable = false)
    private MemberEntity member;

    @Column(name = "board_title",nullable = false)
    private String boardTitle;

    @Column(name = "board_content",nullable = false)
    private String boardContent;

    @Column(name = "board_img",nullable = false)
    private String boardImg;

    @Column
    private String address;

    @Column
    private Long price;

    @Enumerated(EnumType.STRING)
    private CategoryEnum category;


}
