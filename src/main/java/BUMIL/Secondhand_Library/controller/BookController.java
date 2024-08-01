package BUMIL.Secondhand_Library.controller;

import BUMIL.Secondhand_Library.domain.book.DTO.BookFinderDTO;
import BUMIL.Secondhand_Library.domain.book.DTO.MemberSelectionDTO;

import BUMIL.Secondhand_Library.domain.book.Service.APIService;
import BUMIL.Secondhand_Library.domain.book.Service.BookRepositoryService;
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
    BookRepositoryService bookRepositoryService;

    @Autowired
    APIService apiService;

    @GetMapping("/{id}")
    public String bookInfo(@PathVariable("id") Long id, Model model){
        BookEntity bookEntity = bookRepositoryService.getBook(id);

        model.addAttribute(bookEntity);
        return "Book/bookInfo";
    }

    //사용자 맞춤 도서 추천 서비스 이동
    @GetMapping("/recommendations")
    public String recommendations(){
        return "Book/recommendations";
    }

    //사용자 정보 제출 및 추천 도서 리스트 반환
    @PostMapping("/recommendations")
    public String recommendations(MemberSelectionDTO memberSelectionDto , Model model){

        List<BookEntity> recommendedBooks = bookRepositoryService.searchPopularBooks(
                memberSelectionDto.getSex(),
                memberSelectionDto.getAge(),
                memberSelectionDto.getLocation(),
                memberSelectionDto.getInterest()
        );

        model.addAttribute("recommendedBooks", recommendedBooks);
        return "Book/recommendationsList";
    }

    //알라딘 중고 재고 위치 확인
    @GetMapping("/used-inventory-check/{isbn}")
    public String usedInventoryCheck(@PathVariable("isbn") String isbn){
        //재고가있는 지점 반환 재고가 없으면 빈 배열
        //유효성 검사 필수

        apiService.checkUsedStockInBranch(isbn);

        return "Book/recommendationsList";
    }


    //인용구작성할 도서 찾기 폼으로 이동
    @GetMapping("/BookFinder")
    public String quoteBookFinder(Model model){
        List<BookEntity> bookEntities = bookRepositoryService.getAllBooks();
        model.addAttribute("bookEntities", bookEntities);
        return  "BookMark/BookFinder";
    }

    @PostMapping("/BookFinder")
    public String quoteBookFinder(BookFinderDTO bookFinderDTO, Model model){
        List<BookEntity> bookEntities =  bookRepositoryService.bookFinder(bookFinderDTO.getBookName());
        if (bookEntities != null){
            model.addAttribute("bookEntities", bookEntities);
            return  "BookMark/BookFinder";
        }
        return null;
    }

    @GetMapping("/{bookId}/quoteForm")
    public String quoteForm(@PathVariable("bookId") Long id , Model model){
        model.addAttribute("bookId",id);
        return "";
    }

}
