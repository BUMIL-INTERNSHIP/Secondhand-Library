package BUMIL.Secondhand_Library.domain.book.DTO;

import BUMIL.Secondhand_Library.domain.book.entity.BookEntity;
import lombok.Data;

@Data
public class bookDto {
    private Long bookId;
    private String bookName;
    private String author;
    private String pubDate;
    private String description;
    private String coverImg;
    private String kdc;
    private int item_ID;
    private int price;
    private String isbn;

    public bookDto(BookEntity bookEntity) {
        this.bookId = bookEntity.getBookId();
        this.bookName = bookEntity.getBookName();
        this.author = bookEntity.getAuthor();
        this.pubDate = bookEntity.getPubDate();
        this.description = bookEntity.getDescription();
        this.coverImg = bookEntity.getCoverImg();
        this.kdc = bookEntity.getKdc();
        this.item_ID = bookEntity.getItem_ID();
        this.price = bookEntity.getPrice();
        this.isbn = bookEntity.getIsbn();
    }
}
