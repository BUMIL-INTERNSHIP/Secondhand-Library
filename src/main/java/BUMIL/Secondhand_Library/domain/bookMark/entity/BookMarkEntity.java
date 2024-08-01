package BUMIL.Secondhand_Library.domain.bookMark.entity;

import BUMIL.Secondhand_Library.domain.book.entity.BookEntity;
import BUMIL.Secondhand_Library.domain.member.entity.MemberEntity;
import BUMIL.Secondhand_Library.global.basic.BasicEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="bookmark")
public class BookMarkEntity extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookMarkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookEntity book;

    @OneToOne
    private MemberEntity member;

    private String author; //인물 혹 글쓴이

    private String quote; //인용구

    private LocalDate bookMarkDate;
}
