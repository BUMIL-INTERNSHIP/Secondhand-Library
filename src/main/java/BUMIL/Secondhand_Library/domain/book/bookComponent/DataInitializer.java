package BUMIL.Secondhand_Library.domain.book.bookComponent;

import BUMIL.Secondhand_Library.domain.book.APIClient.LibraryAPIClient;
import BUMIL.Secondhand_Library.domain.book.Repository.BookRepository;
import BUMIL.Secondhand_Library.domain.book.Service.BookRepositoryService;
import BUMIL.Secondhand_Library.domain.book.entity.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private LibraryAPIClient libraryAPIClient;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookRepositoryService bookRepositoryService;

    @Override
    public void run(String... args) throws Exception {

        if (bookRepositoryService.getBookTableColumnCount() == 0) {
            CompletableFuture<List<BookEntity>> futureBookList = libraryAPIClient.initializePopularBooks();
            List<BookEntity> bookEntityList = futureBookList.get(); // 비동기 작업 완료를 기다립니다.
            if (bookEntityList != null) {
                bookRepository.saveAll(bookEntityList);
            }
        }
    }
}