package BUMIL.Secondhand_Library.service;

import BUMIL.Secondhand_Library.domain.board.dto.BoardCreateDto;
import BUMIL.Secondhand_Library.domain.board.dto.BoardResDto;
import BUMIL.Secondhand_Library.domain.board.dto.BoardShortDto;
import BUMIL.Secondhand_Library.domain.board.entity.BoardEntity;
import BUMIL.Secondhand_Library.domain.board.repository.BoardRepository;
import BUMIL.Secondhand_Library.domain.book.Repository.BookRepository;
import BUMIL.Secondhand_Library.domain.book.entity.BookEntity;


import BUMIL.Secondhand_Library.domain.member.entity.MemberEntity;
import BUMIL.Secondhand_Library.domain.member.repository.MemberRepository;
import BUMIL.Secondhand_Library.global.CategoryEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        BookEntity book = bookRepository.findSingleByBookName(boardCreateDto.getBookName());

        BoardEntity board = BoardEntity.builder()
                .book(book)
                .member(member)
                .boardTitle(boardCreateDto.getBoardTitle())
                .boardContent(boardCreateDto.getBoardContent())
                .boardImg(book.getCoverImg())
                .address(boardCreateDto.getAddress())
                .price(boardCreateDto.getPrice())
                .category(boardCreateDto.getCategory())
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
    public List<BoardShortDto> getAllBoards() {
        List<BoardEntity> boards = boardRepository.findAll();
        return boards.stream().map(board -> new BoardShortDto(
                board.getBoardId(),
                board.getMember().getMemberName(),
                board.getBook().getBookName(),
                board.getBoardTitle(),
                board.getBoardImg(),
                board.getAddress(),
                board.getPrice()
        )).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BoardShortDto> getAllSellBoards() {
        List<BoardEntity> sellBoards = boardRepository.findByCategory(CategoryEnum.SELL);
        return sellBoards.stream().map(board -> new BoardShortDto(
                board.getBoardId(),
                board.getMember().getMemberName(),
                board.getBook().getBookName(),
                board.getBoardTitle(),
                board.getBoardImg(),
                board.getAddress(),
                board.getPrice()
        )).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<BoardShortDto> getAllBuyBoards() {
        List<BoardEntity> buyBoards = boardRepository.findByCategory(CategoryEnum.BUY);
        return buyBoards.stream().map(board -> new BoardShortDto(
                board.getBoardId(),
                board.getMember().getMemberName(),
                board.getBook().getBookName(),
                board.getBoardTitle(),
                board.getBoardImg(),
                board.getAddress(),
                board.getPrice()
        )).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<BoardResDto> getBoardById(Long boardId, Authentication authentication) {
        Optional<BoardEntity> board = boardRepository.findById(boardId);
        return board.map(b -> convertToResDto(b, authentication));
    }

    private BoardResDto convertToResDto(BoardEntity board, Authentication authentication) {
        Long currentUserOuthId = null;

        if (authentication != null && authentication.getName() != null) {
            currentUserOuthId = Long.valueOf(authentication.getName());
        }

        return BoardResDto.builder()
                .boardId(board.getBoardId())
                .memberOuthId(board.getMember().getOuthId())
                .memberName(board.getMember().getMemberName())
                .bookName(board.getBook().getBookName())
                .boardTitle(board.getBoardTitle())
                .boardContent(board.getBoardContent())
                .boardImg(board.getBoardImg())
                .address(board.getAddress())
                .price(board.getPrice())
                .currentUserOuthId(currentUserOuthId) // 현재 로그인한 사용자의 outhId 또는 null 값
                .build();
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



}
