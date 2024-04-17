package fiveguys.Tom.Cafeteria.Server.domain.board.controller;

import fiveguys.Tom.Cafeteria.Server.apiPayload.ApiResponse;
import fiveguys.Tom.Cafeteria.Server.domain.board.dto.BoardResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.board.entity.Board;
import fiveguys.Tom.Cafeteria.Server.domain.board.entity.BoardType;
import fiveguys.Tom.Cafeteria.Server.domain.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 게시글 생성
    @PostMapping
    public ApiResponse<BoardResponseDTO> createBoard(@RequestBody Board board) {
        return boardService.createBoard(ApiResponse.onSuccess());
    }

    // 전체 게시글 조회
    @GetMapping("/boards")
    public List<Board> getAllBoards() {
        return boardService.getAllBoards();
    }

    // 특정 게시판의 전체 게시글 조회
    @GetMapping("/{boardType}/boards")
    public List<Board> getAllBoardsByType(@PathVariable("boardType") BoardType boardType) {
        return boardService.getAllBoardsByType(boardType);
    }

    //특정 게시글 조회
    @GetMapping("/{id}")
    public Board getBoardById(@PathVariable Long id) {
        return boardService.getBoardById(id);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public Board updateBoard(@PathVariable Long id, @RequestBody Board board) {
        return boardService.updateBoard(id, board);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
    }

    //게시글 좋아요
    @PostMapping("/{id}/like")
    public Board toggleLike(@PathVariable Long boardId, @RequestParam Long userId) {
        return boardService.toggleLike(boardId, userId);
    }

}

