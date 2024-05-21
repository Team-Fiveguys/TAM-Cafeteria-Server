package fiveguys.Tom.Cafeteria.Server.domain.board.controller;

import fiveguys.Tom.Cafeteria.Server.apiPayload.ApiResponse;
import fiveguys.Tom.Cafeteria.Server.domain.board.dto.*;
import fiveguys.Tom.Cafeteria.Server.domain.board.entity.BoardType;
import fiveguys.Tom.Cafeteria.Server.domain.board.entity.Post;
import fiveguys.Tom.Cafeteria.Server.domain.board.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    @Operation(summary = "게시글 리스트 최신순 조회 API", description = "최신 순으로 정렬하여 받을 페이지를 인자로 받아 응답한다")
    @GetMapping("/{boardType}/boards")
    public ApiResponse<List<PostPreviewDTO>> getAllBoardsByType(@PathVariable("boardType") BoardType boardType,
                                                                @RequestParam(name = "cafeteriaId") Long cafeteriaId,
                                                                @RequestParam(name = "page") int page ) {
        List<PostPreviewDTO> postList = postService.getPostPageOrderedByTime(boardType, cafeteriaId, page);
        return ApiResponse.onSuccess(postList);
    }


    //특정 게시글 조회
    @Operation(summary = "특정 게시글 조회 API")
    @GetMapping("/{id}")
    public ApiResponse<PostResponseDTO> getBoardById(@PathVariable(name = "id") Long id) {
        PostResponseDTO postResponseDTO = postService.getPostById(id);
        return ApiResponse.onSuccess(postResponseDTO);
    }

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
    //게시글 좋아요
//    @Operation(summary = "게시글에 좋아요")
//    @PostMapping("/{id}/like")
//    public ApiResponse<PostResponseDTO> toggleLike(@PathVariable Long id, @RequestParam Long userId) {
//        PostResponseDTO boardResponseDTO = postService.toggleLike(id, userId);
//        return ApiResponse.onSuccess(boardResponseDTO);
//    }

}

