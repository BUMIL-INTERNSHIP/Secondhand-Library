package BUMIL.Secondhand_Library.domain.library.Service;

import BUMIL.Secondhand_Library.domain.book.entity.BookEntity;
import BUMIL.Secondhand_Library.domain.book.Repository.BookRepository;
import BUMIL.Secondhand_Library.domain.library.entity.LibraryEntity;
import BUMIL.Secondhand_Library.domain.library.repository.LibraryRepository;
import BUMIL.Secondhand_Library.domain.member.entity.MemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<BookEntity> getLibraryBooks(MemberEntity member) {
        LibraryEntity library = libraryRepository.findByMember(member);
        if (library != null) {
            return library.getBooks();
        } else {
            return null;
        }
    }

    public void addBookToLibrary(MemberEntity member, Long bookId) {
        LibraryEntity library = libraryRepository.findByMember(member);
        if (library == null) {
            library = new LibraryEntity();
            library.setMember(member);
        }

        BookEntity book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        library.addBook(book); // addBook 메소드 사용

        libraryRepository.save(library); // 변경된 library를 저장합니다.

    }

    public boolean removeBookFromLibrary(MemberEntity member, Long bookId) {
        LibraryEntity library = libraryRepository.findByMember(member);
        if (library == null) {
            return false;
        }

        BookEntity book = bookRepository.findById(bookId).orElse(null);
        if (book == null || !library.getBooks().remove(book)) {
            return false;
        }

        book.setLibrary(null);
        bookRepository.save(book);
        libraryRepository.save(library);
        return true;
    }
}
