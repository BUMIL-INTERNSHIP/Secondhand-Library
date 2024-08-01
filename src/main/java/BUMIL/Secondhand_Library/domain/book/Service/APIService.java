package BUMIL.Secondhand_Library.domain.book.Service;

import BUMIL.Secondhand_Library.domain.book.APIClient.AladdinAPIClient;
import BUMIL.Secondhand_Library.domain.book.APIClient.LibraryAPIClient;
import BUMIL.Secondhand_Library.domain.book.entity.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public String[] retrieveBookInfo(String bookName){
        return aladdinAPIClient.retrieveBookInfo(bookName);
    }



}
