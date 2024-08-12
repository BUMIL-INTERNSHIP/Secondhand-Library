package BUMIL.Secondhand_Library.controller;

import BUMIL.Secondhand_Library.domain.board.dto.BoardCreateDto;
import BUMIL.Secondhand_Library.domain.board.dto.BoardResDto;
import BUMIL.Secondhand_Library.domain.board.dto.BoardShortDto;
import BUMIL.Secondhand_Library.domain.board.entity.BoardEntity;
import BUMIL.Secondhand_Library.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/boards")
    public ResponseEntity<BoardEntity> createBoard(@RequestBody BoardCreateDto boardCreateDto, Authentication authentication) {
        BoardEntity createdBoard = boardService.createBoard(boardCreateDto, authentication);
        return ResponseEntity.ok(createdBoard);
    }

    @GetMapping("/boards")
    public ResponseEntity<List<BoardShortDto>> getAllBoards() {
        List<BoardShortDto> boardShortDtos = boardService.getAllBoards();
        return ResponseEntity.ok(boardShortDtos);
    }

    @GetMapping("/boards/sell")
    public ResponseEntity<List<BoardShortDto>> getAllSellBoards() {
        List<BoardShortDto> sellBoards = boardService.getAllSellBoards();
        return ResponseEntity.ok(sellBoards);
    }

    @GetMapping("/boards/buy")
    public ResponseEntity<List<BoardShortDto>> getAllBuyBoards() {
        List<BoardShortDto> buyBoards = boardService.getAllBuyBoards();
        return ResponseEntity.ok(buyBoards);
    }

    @GetMapping("/boards/{boardId}")
    public ResponseEntity<BoardResDto> getBoardById(@PathVariable Long boardId, Authentication authentication) {
        Optional<BoardResDto> boardResDto = boardService.getBoardById(boardId, authentication);
        return boardResDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/boards/{boardId}")
    public ResponseEntity<BoardEntity> updateBoard(@PathVariable Long boardId, @RequestBody BoardCreateDto boardCreateDto, Authentication authentication) {
        BoardEntity updatedBoard = boardService.updateBoard(boardId, boardCreateDto);
        return ResponseEntity.ok(updatedBoard);
    }

    @DeleteMapping("/boards/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId,Authentication authentication) {
        boardService.deleteBoard(boardId,authentication);
        return ResponseEntity.noContent().build();
    }
}
