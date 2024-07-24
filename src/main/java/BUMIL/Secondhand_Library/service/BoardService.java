package BUMIL.Secondhand_Library.service;

import BUMIL.Secondhand_Library.domain.board.dto.BoardCreateDto;
import BUMIL.Secondhand_Library.domain.board.dto.BoardResDto;
import BUMIL.Secondhand_Library.domain.board.entity.BoardEntity;
import BUMIL.Secondhand_Library.domain.board.repository.BoardRepository;
import BUMIL.Secondhand_Library.domain.book.entity.BookEntity;
import BUMIL.Secondhand_Library.domain.book.entity.Repository.BookRepository;
import BUMIL.Secondhand_Library.domain.member.entity.MemberEntity;
import BUMIL.Secondhand_Library.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    @Transactional
    public BoardEntity createBoard(BoardCreateDto boardCreateDto, Authentication authentication) {
        Long outhId = Long.valueOf(authentication.getName());
        MemberEntity member = memberRepository.findByOuthId(outhId);
        BookEntity book = bookRepository.findByBookName(boardCreateDto.getBookName());

        BoardEntity board = BoardEntity.builder()
                .book(book)
                .member(member)
                .boardTitle(boardCreateDto.getBoardTitle())
                .boardContent(boardCreateDto.getBoardContent())
                .boardImg(book.getCoverImg())
                .address(boardCreateDto.getAddress())
                .price(boardCreateDto.getPrice())
                .build();

        BoardEntity savedBoard = boardRepository.save(board);

        member.getBoards().add(savedBoard);
        memberRepository.save(member);

        return savedBoard;
    }

    @Transactional
    public void deleteBoard(Long boardId,Authentication authentication) {
        Long outhId = Long.valueOf(authentication.getName());
        MemberEntity member = memberRepository.findByOuthId(outhId);

        BoardEntity board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다"));

        member.getBoards().remove(board);

        boardRepository.delete(board);
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public List<BoardEntity> getAllBoards() {
        return boardRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<BoardEntity> getBoardById(Long boardId) {
        return boardRepository.findById(boardId);
    }

    @Transactional
    public BoardEntity updateBoard(Long boardId, BoardCreateDto boardCreateDto) {
        BoardEntity board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다"));

        board.setBoardTitle(boardCreateDto.getBoardTitle());
        board.setBoardContent(boardCreateDto.getBoardContent());
        board.setAddress(boardCreateDto.getAddress());
        board.setPrice(boardCreateDto.getPrice());

        return boardRepository.save(board);
    }

    public BoardResDto convertToDto(BoardEntity board) {
        return BoardResDto.builder()
                .boardId(board.getBoardId())
                .memberName(board.getMember().getMemberName())
                .boardTitle(board.getBoardTitle())
                .boardContent(board.getBoardContent())
                .boardImg(board.getBoardImg())
                .address(board.getAddress())
                .price(board.getPrice())
                .build();
    }

}
