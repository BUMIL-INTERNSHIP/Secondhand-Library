package BUMIL.Secondhand_Library.domain.book.Service;

import BUMIL.Secondhand_Library.domain.book.APIClient.AladdinAPIClient;
import BUMIL.Secondhand_Library.domain.book.APIClient.LibraryAPIClient;
import BUMIL.Secondhand_Library.domain.book.entity.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class APIService {

    @Autowired
    LibraryAPIClient libraryAPIClient;

    @Autowired
    AladdinAPIClient aladdinAPIClient;

    public List<BookEntity> searchPopularBooks(String sex, String age, String location, String interest) {
     return   libraryAPIClient.searchPopularBooks(sex,age,location,interest);
    }

    public List<String> checkUsedStockInBranch(String isbn) {
        return aladdinAPIClient.findStore(isbn);
    }

    public String[] retrieveBookInfo(String bookName){
        return aladdinAPIClient.retrieveBookInfo(bookName);
    }



}
