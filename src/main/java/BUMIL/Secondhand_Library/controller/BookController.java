package BUMIL.Secondhand_Library.controller;

import BUMIL.Secondhand_Library.domain.book.DTO.MemberSelectionDto;

import BUMIL.Secondhand_Library.domain.book.Service.APIService;
import BUMIL.Secondhand_Library.domain.book.Service.BookRepositoryService;
import BUMIL.Secondhand_Library.domain.book.entity.BookEntity;
import BUMIL.Secondhand_Library.domain.library.Service.LibraryService;
import BUMIL.Secondhand_Library.domain.member.entity.CustomUserDetails;
import BUMIL.Secondhand_Library.domain.member.entity.MemberEntity;
import BUMIL.Secondhand_Library.domain.member.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookRepositoryService recommendationService;

    @Autowired
    APIService apiService;

    @Autowired
    BookRepositoryService bookService;

    @Autowired
    LibraryService libraryService;

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/{id}")
    public String bookInfo(@PathVariable("id") Long id, Model model){
        BookEntity bookEntity = recommendationService.getBook(id);

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
    public String recommendations(MemberSelectionDto memberSelectionDto , Model model){
        System.out.println("test 한번만 호출");
        List<BookEntity> recommendedBooks = recommendationService.searchPopularBooks(
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

    @PostMapping("/add-to-wishlist/{id}")
    public ResponseEntity<String> addToWishlist(@PathVariable("id") Long bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long outhId = (Long) authentication.getPrincipal();
        MemberEntity member = memberRepository.findByOuthId(outhId);

        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }

        try {
            libraryService.addBookToLibrary(member, bookId);
            return ResponseEntity.ok("Book added to wishlist");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add book to wishlist");
        }
    }
}
