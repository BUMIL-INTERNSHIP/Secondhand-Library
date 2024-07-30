package BUMIL.Secondhand_Library.domain.book.Service;


import BUMIL.Secondhand_Library.domain.book.APIClient.AladdinAPIClient;
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

    @Autowired
    AladdinAPIClient aladdinAPIClient;

    public List<BookEntity> searchPopularBooks(String sex, String age, String location, String interest) {
       return libraryAPIClient.searchPopularBooks(sex,age,location,interest);
    }

    public boolean isBookNameExists(String bookName) {
        return bookRepository.existsByBookName(bookName);
    }

    public BookEntity getBook(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void checkUsedStockInBranch(String isbn) {
        List<String> store =  aladdinAPIClient.findStore(isbn);;
        if (!store.isEmpty()){
            for (String s : store){
                System.out.println(s);
            }
        }else{
            System.out.println("재고가 없습니다.");
        }


    }
}
