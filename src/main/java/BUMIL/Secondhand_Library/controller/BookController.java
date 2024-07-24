package BUMIL.Secondhand_Library.controller;

import BUMIL.Secondhand_Library.domain.book.entity.APIClient.AladdinAPIClient;
import BUMIL.Secondhand_Library.domain.book.entity.APIClient.LibraryAPIClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private AladdinAPIClient aladdinAPIClient;

    @Autowired
    private LibraryAPIClient libraryAPIClient;

    @GetMapping("/test/{bookName}")
    public String test(@PathVariable String bookName) throws IOException {
        aladdinAPIClient.searchBooks(bookName);
        return "검색 완료";
    }

    @GetMapping("/text/add")
    public String tt() throws IOException {
        libraryAPIClient.searchPopularBooks();
        return "검색 완료";
    }
}
