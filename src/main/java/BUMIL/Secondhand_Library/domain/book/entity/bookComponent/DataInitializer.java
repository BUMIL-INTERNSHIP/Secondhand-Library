package BUMIL.Secondhand_Library.domain.book.entity.bookComponent;

import BUMIL.Secondhand_Library.domain.book.entity.APIClient.LibraryAPIClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private LibraryAPIClient libraryAPIClient;

    @Override
    public void run(String... args) throws Exception {
        libraryAPIClient.searchPopularBooks();
    }
}
