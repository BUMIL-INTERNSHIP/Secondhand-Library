package BUMIL.Secondhand_Library.domain.book.Service;


import BUMIL.Secondhand_Library.domain.book.APIClient.LibraryAPIClient;
import BUMIL.Secondhand_Library.domain.book.Repository.BookRepository;
import BUMIL.Secondhand_Library.domain.book.entity.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    LibraryAPIClient libraryAPIClient;

    @Autowired
    BookRepository bookRepository;

    public List<BookEntity> searchPopularBooks(String sex, String age, String location, String interest) {
       return libraryAPIClient.searchPopularBooks(sex,age,location,interest);
    }

    public boolean isBookNameExists(String bookName) {
        return bookRepository.existsByBookName(bookName);
    }


    public BookEntity getBook(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
}
