package fiveguys.Tom.Cafeteria.Server.domain.board.controller;

import fiveguys.Tom.Cafeteria.Server.apiPayload.ApiResponse;
import fiveguys.Tom.Cafeteria.Server.domain.board.dto.*;
import fiveguys.Tom.Cafeteria.Server.domain.board.entity.Post;
import fiveguys.Tom.Cafeteria.Server.domain.board.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final PostService postService;

    // 게시글 생성
    @Operation(summary = "게시글 생성 API")
    @PostMapping
    public ApiResponse<String> createPost(@RequestBody PostCreateDTO postCreateDTO) {
        Post post = postService.createPost(postCreateDTO);
        return ApiResponse.onSuccess(post.getId() + "번 게시물이 생성되었습니다.");
    }

    // 특정 게시판의 전체 게시글 조회
//    @Operation(summary = "게시글 리스트 조회 API", description = "수정 필요")
//    @GetMapping("/{boardType}/boards")
//    public ApiResponse<List<PostListDTO>> getAllBoardsByType(@PathVariable("boardType") BoardType boardType) {
//        List<PostListDTO> postListDTOList = boardService.getAllBoardsByType(boardType);
//        return ApiResponse.onSuccess(postListDTOList);
//    }


//    //특정 게시글 조회
//    @Operation(summary = "특정 게시글 조회 API")
//    @GetMapping("/{id}")
//    public ApiResponse<BoardResponseDTO> getBoardById(@PathVariable Long id) {
//        BoardResponseDTO boardResponseDTO = postService.getBoardById(id);
//        return ApiResponse.onSuccess(boardResponseDTO);
//    }

    // 게시글 수정
//    @Operation(summary = "특정 게시글 수정 API")
//    @PutMapping("/{id}")
//    public ApiResponse<BoardResponseDTO> updateBoard(@PathVariable Long id, @RequestBody BoardUpdateDTO boardUpdateDTO) {
//        BoardResponseDTO updatedBoard = boardService.updateBoard(id, boardUpdateDTO);
//        return ApiResponse.onSuccess(updatedBoard);
//    }

    // 게시글 삭제
//    @Operation(summary = "특정 게시글 삭제 API")
//    @DeleteMapping("/{id}")
//    public ApiResponse<Void> deleteBoard(@PathVariable Long id, @RequestBody BoardDeleteDTO boardDeleteDTO) {
//        boardService.deleteBoard(id, boardDeleteDTO);
//        return ApiResponse.onSuccess(null); // 성공적으로 삭제 처리됐음을 응답. 별도의 데이터 반환 필요 없음
//    }
//
//    //게시글 좋아요
//    @Operation(summary = "게시글에 좋아요")
//    @PostMapping("/{id}/like")
//    public ApiResponse<BoardResponseDTO> toggleLike(@PathVariable Long id, @RequestParam Long userId) {
//        BoardResponseDTO boardResponseDTO = boardService.toggleLike(id, userId);
//        return ApiResponse.onSuccess(boardResponseDTO);
//    }

}

