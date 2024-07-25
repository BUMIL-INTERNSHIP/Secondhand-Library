package BUMIL.Secondhand_Library.controller;

import BUMIL.Secondhand_Library.domain.book.APIClient.AladdinAPIClient;
import BUMIL.Secondhand_Library.domain.book.APIClient.LibraryAPIClient;
import BUMIL.Secondhand_Library.domain.book.DTO.MemberSelectionDto;

import BUMIL.Secondhand_Library.domain.book.Service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    RecommendationService recommendationService;

//    @GetMapping("/test/{bookName}")
//    public String test(@PathVariable String bookName) throws IOException {
//        aladdinAPIClient.searchBooks(bookName);
//        return "검색 완료";
//    }
//
//    @GetMapping("/text/add")
//    public String tt() throws IOException {
//        libraryAPIClient.initializePopularBooks();
//        return "검색 완료";
//    }


    @GetMapping("/recommendations")
    public String recommendations(){
        return "Book/recommendations";
    }

    @PostMapping("/recommendations")
    public String recommendations(MemberSelectionDto memberSelectionDto){
        recommendationService.searchPopularBooks(memberSelectionDto.getSex(),memberSelectionDto.getAge(),
                memberSelectionDto.getLocation(),memberSelectionDto.getInterest());
        return "Book/recommendations";
    }
}
