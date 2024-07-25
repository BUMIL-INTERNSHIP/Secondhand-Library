package BUMIL.Secondhand_Library.domain.book.Service;


import BUMIL.Secondhand_Library.domain.book.APIClient.LibraryAPIClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendationService {

    @Autowired
    LibraryAPIClient libraryAPIClient;

    public void searchPopularBooks(String sex, String age, String location, String interest) {
        libraryAPIClient.searchPopularBooks(sex,age,location,interest);
    }
}
