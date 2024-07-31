package BUMIL.Secondhand_Library.domain.book.Service;


import BUMIL.Secondhand_Library.domain.book.Repository.BookRepository;
import BUMIL.Secondhand_Library.domain.book.entity.BookEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookRepositoryService {

    @Autowired
    APIService apiService;

    @Autowired
    BookRepository bookRepository;

    public List<BookEntity> searchPopularBooks(String sex, String age, String location, String interest) {
        int a = 1;
        //DB에 들어있는 객체와는 다른 객체
        List<BookEntity> bookEntities  = apiService.searchPopularBooks(sex,age,location,interest);

        Set<String> existingBookNames = new HashSet<>(bookRepository.findAllBookNames());

        //DB에 이미 존재하는 있는 도서는 제외
        List<BookEntity> newBooks = bookEntities.stream()
                        .filter(book -> !existingBookNames.contains(book.getBookName()))
                        .toList();

        for (BookEntity book : newBooks){
            String[] bookInfo = apiService.retrieveBookInfo(book.getBookName());

            if (bookInfo.length >= 4){
                book.setDescription(bookInfo[0]);
                book.setItem_ID(Integer.parseInt(bookInfo[1]));
                book.setPrice(Integer.parseInt(bookInfo[2]));
                book.setIsbn(bookInfo[3]);
            }else{
                log.info("추가 데이터가 존재하지않음 : {}",book.getBookName());
            }
        }

        System.out.println("호출되었다 : " + a++);
        for (BookEntity b : newBooks){
            System.out.println("db에 새로 추가되는 애들 : "+ b.getBookName());
        }


        //새롭게 추가되는 도서
        bookRepository.saveAll(newBooks);

        //DB에 존재하는 동일한 이름의 도서 객체를 반환
        //추천받은 전체 리스트 반환
        return bookEntities.stream()
                .filter(book -> existingBookNames.contains(book.getBookName()))
                .toList();
    }

    public boolean isBookNameExists(String bookName) {
        return bookRepository.existsByBookName(bookName);
    }

    public long getBookTableColumnCount(){
        return bookRepository.count();
    }

    public BookEntity getBook(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

}
