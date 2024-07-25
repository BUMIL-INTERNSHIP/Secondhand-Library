package BUMIL.Secondhand_Library.domain.book.bookComponent;

import BUMIL.Secondhand_Library.domain.book.APIClient.LibraryAPIClient;
import BUMIL.Secondhand_Library.domain.book.Repository.BookRepository;
import BUMIL.Secondhand_Library.domain.book.entity.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private LibraryAPIClient libraryAPIClient;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
//        List<BookEntity> bookEntityList = libraryAPIClient.initializePopularBooks();
//        if (bookEntityList != null){
//            bookRepository.saveAll(bookEntityList);
//        }

    }
}
