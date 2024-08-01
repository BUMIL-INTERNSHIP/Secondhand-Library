package BUMIL.Secondhand_Library.domain.library.entity;

import BUMIL.Secondhand_Library.domain.book.entity.BookEntity;
import BUMIL.Secondhand_Library.domain.library.Service.LibraryService;
import BUMIL.Secondhand_Library.domain.member.entity.MemberEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="library")
public class LibraryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long libraryId;

    @OneToOne
    private MemberEntity member;

    private static final Logger logger = LoggerFactory.getLogger(LibraryService.class);

    @OneToMany(mappedBy = "library", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<BookEntity> books = new ArrayList<>(); // books 리스트를 초기화합니다.

    public void addBook(BookEntity book) {
        if (!books.contains(book)) {
            books.add(book);
            book.setLibrary(this);
            logger.info("Book added: Book ID {}, Library ID {}", book.getBookId(), this.libraryId);
        }
    }
}
