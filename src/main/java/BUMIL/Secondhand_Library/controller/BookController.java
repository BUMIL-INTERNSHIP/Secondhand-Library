package BUMIL.Secondhand_Library.controller;

import BUMIL.Secondhand_Library.domain.book.DTO.MemberSelectionDto;

import BUMIL.Secondhand_Library.domain.book.Service.BookService;
import BUMIL.Secondhand_Library.domain.book.entity.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService recommendationService;

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
    public String recommendations(MemberSelectionDto memberSelectionDto , Model model){
        List<BookEntity> recommendedBooks = recommendationService.searchPopularBooks(
                memberSelectionDto.getSex(),
                memberSelectionDto.getAge(),
                memberSelectionDto.getLocation(),
                memberSelectionDto.getInterest()
        );
        model.addAttribute("recommendedBooks", recommendedBooks);
        return "Book/recommendationsList";
    }
}
