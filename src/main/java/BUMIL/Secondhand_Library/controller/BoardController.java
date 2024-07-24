package BUMIL.Secondhand_Library.controller;


import BUMIL.Secondhand_Library.domain.board.dto.BoardCreateDto;
import BUMIL.Secondhand_Library.domain.board.dto.BoardResDto;
import BUMIL.Secondhand_Library.domain.board.dto.BoardShortDto;
import BUMIL.Secondhand_Library.domain.board.entity.BoardEntity;
import BUMIL.Secondhand_Library.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardEntity> createBoard(@RequestBody BoardCreateDto boardCreateDto, Authentication authentication) {
        BoardEntity createdBoard = boardService.createBoard(boardCreateDto, authentication);
        return ResponseEntity.ok(createdBoard);
    }

    @GetMapping
    public ResponseEntity<List<BoardShortDto>> getAllBoards() {
        List<BoardEntity> boards = boardService.getAllBoards();
        List<BoardShortDto> boardShortDtos = boards.stream().map(board -> {
            return new BoardShortDto(
                    board.getBook().getBookName(),
                    board.getBoardTitle(),
                    board.getBoardImg(),
                    board.getAddress(),
                    board.getPrice()
            );
        }).collect(Collectors.toList());

        return ResponseEntity.ok(boardShortDtos);
    }



    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResDto> getBoardById(@PathVariable Long boardId) {
        Optional<BoardEntity> board = boardService.getBoardById(boardId);

        return board.map(b -> ResponseEntity.ok(boardService.convertToDto(b)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<BoardEntity> updateBoard(@PathVariable Long boardId, @RequestBody BoardCreateDto boardCreateDto, Authentication authentication) {
        BoardEntity updatedBoard = boardService.updateBoard(boardId, boardCreateDto);
        return ResponseEntity.ok(updatedBoard);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId,Authentication authentication) {
        boardService.deleteBoard(boardId,authentication);
        return ResponseEntity.noContent().build();
    }
}
