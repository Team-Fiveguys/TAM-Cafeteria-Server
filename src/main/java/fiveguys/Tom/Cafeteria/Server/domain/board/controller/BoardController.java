package fiveguys.Tom.Cafeteria.Server.domain.board.controller;

import fiveguys.Tom.Cafeteria.Server.apiPayload.ApiResponse;
import fiveguys.Tom.Cafeteria.Server.domain.board.dto.*;
import fiveguys.Tom.Cafeteria.Server.domain.board.entity.Board;
import fiveguys.Tom.Cafeteria.Server.domain.board.entity.BoardType;
import fiveguys.Tom.Cafeteria.Server.domain.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 게시글 생성
    @Operation(summary = "게시글 생성 API")
    @PostMapping
    public ApiResponse<Board> createBoard(@RequestBody BoardCreateDTO boardCreateDTO) {
        Board board = boardService.createBoard(boardCreateDTO);
        return ApiResponse.onSuccess(board);
    }

    // 특정 게시판의 전체 게시글 조회
    @Operation(summary = "게시글 리스트 조회 API", description = "수정 필요")
    @GetMapping("/{boardType}/boards")
    public List<Board> getAllBoardsByType(@PathVariable("boardType") BoardType boardType) {
        return boardService.getAllBoardsByType(boardType);
    }

    //특정 게시글 조회
    @Operation(summary = "특정 게시글 조회 API")
    @GetMapping("/{id}")
    public ApiResponse<BoardResponseDTO> getBoardById(@PathVariable Long id) {
        BoardResponseDTO boardResponseDTO = boardService.getBoardById(id);
        return ApiResponse.onSuccess(boardResponseDTO);
    }

    // 게시글 수정
    @Operation(summary = "특정 게시글 조회 API")
    @PutMapping("/{id}")
    public ApiResponse<BoardResponseDTO> updateBoard(@PathVariable Long id, @RequestBody BoardUpdateDTO boardUpdateDTO) {
        BoardResponseDTO updatedBoard = boardService.updateBoard(id, boardUpdateDTO);
        return ApiResponse.onSuccess(updatedBoard);
    }

    // 게시글 삭제
    @Operation(summary = "특정 게시글 삭제 API")
    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
    }

    //게시글 좋아요
    @Operation(summary = "게시글에 좋아요")
    @PostMapping("/{id}/like")
    public ApiResponse<BoardResponseDTO> toggleLike(@PathVariable Long id, @RequestParam Long userId) {
        BoardResponseDTO boardResponseDTO = boardService.toggleLike(id, userId);
        return ApiResponse.onSuccess(boardResponseDTO);
    }

}
