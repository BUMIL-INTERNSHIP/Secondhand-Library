package BUMIL.Secondhand_Library.domain.library.DTO;

import BUMIL.Secondhand_Library.domain.book.DTO.bookDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BookshelfResponse {
    private int totalPages;
    private List<bookDto> bookDtoList;

    @JsonCreator
    public BookshelfResponse(@JsonProperty("totalPages") int totalPages, @JsonProperty("bookDtoList") List<bookDto> bookDtoList) {
        this.totalPages = totalPages;
        this.bookDtoList = bookDtoList;
    }

    // Getters and setters
    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<bookDto> getBookDtoList() {
        return bookDtoList;
    }

    public void setBookDtoList(List<bookDto> bookDtoList) {
        this.bookDtoList = bookDtoList;
    }
}

