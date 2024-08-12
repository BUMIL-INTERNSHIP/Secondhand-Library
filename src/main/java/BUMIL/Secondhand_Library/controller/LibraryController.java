package BUMIL.Secondhand_Library.controller;

import BUMIL.Secondhand_Library.domain.book.DTO.bookDto;
import BUMIL.Secondhand_Library.domain.book.entity.BookEntity;
import BUMIL.Secondhand_Library.domain.member.entity.MemberEntity;
import BUMIL.Secondhand_Library.domain.member.repository.MemberRepository;
import BUMIL.Secondhand_Library.domain.library.Service.LibraryService;
import BUMIL.Secondhand_Library.domain.library.DTO.ApiResponse;
import BUMIL.Secondhand_Library.domain.library.DTO.BookshelfResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mypage")
public class LibraryController {

    @Autowired
    LibraryService libraryService;

    @Autowired
    MemberRepository memberRepository;

    @GetMapping(value = "/bookshelf", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBookshelf(Authentication authentication,
                                          @RequestParam(defaultValue = "1") int page,
                                          @RequestParam(defaultValue = "6") int size) {
        Long outhId = null;
        try {
            outhId = (Long) authentication.getPrincipal(); // Principal을 Long으로 캐스팅
            MemberEntity member = memberRepository.findByOuthId(outhId); // MemberEntity를 가져옴

            if (member == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 사용자 정보를 찾을 수 없는 경우 처리
            }

            List<BookEntity> books = libraryService.getLibraryBooks(member);
            if (books == null) {
                return ResponseEntity.ok(new BookshelfResponse(0, List.of()));
            }

            int totalBooks = books.size();
            int totalPages = (int) Math.ceil((double) totalBooks / size);
            int start = (page - 1) * size;
            int end = Math.min(start + size, totalBooks);

            List<bookDto> bookDtoList = books.subList(start, end).stream().map(bookDto::new).collect(Collectors.toList());
            return ResponseEntity.ok(new BookshelfResponse(totalPages, bookDtoList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @DeleteMapping(value = "/bookshelf/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteBookFromLibrary(Authentication authentication, @PathVariable Long bookId) {
        Long outhId = null;
        try {
            outhId = (Long) authentication.getPrincipal(); // Principal을 Long으로 캐스팅
            MemberEntity member = memberRepository.findByOuthId(outhId); // MemberEntity를 가져옴

            if (member == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 사용자 정보를 찾을 수 없는 경우 처리
            }

            boolean removed = libraryService.removeBookFromLibrary(member, bookId);
            if (removed) {
                return ResponseEntity.ok(new ApiResponse(true, "Book removed from library"));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "Failed to remove book from library"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "Internal server error"));
        }
    }
}
