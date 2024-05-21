package fiveguys.Tom.Cafeteria.Server.domain.board.service;

import fiveguys.Tom.Cafeteria.Server.apiPayload.code.status.ErrorStatus;
import fiveguys.Tom.Cafeteria.Server.auth.UserContext;
import fiveguys.Tom.Cafeteria.Server.domain.board.dto.*;
import fiveguys.Tom.Cafeteria.Server.domain.board.entity.Post;
import fiveguys.Tom.Cafeteria.Server.domain.board.entity.PostLike;
import fiveguys.Tom.Cafeteria.Server.domain.board.entity.BoardType;
import fiveguys.Tom.Cafeteria.Server.domain.board.repository.PostLikeRepository;
import fiveguys.Tom.Cafeteria.Server.domain.board.repository.PostRepository;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.repository.CafeteriaRepository;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.service.CafeteriaQueryService;
import fiveguys.Tom.Cafeteria.Server.domain.user.converter.UserConverter;
import fiveguys.Tom.Cafeteria.Server.domain.user.dto.UserResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.User;
import fiveguys.Tom.Cafeteria.Server.domain.user.repository.UserRepository;
import fiveguys.Tom.Cafeteria.Server.domain.user.service.UserQueryService;
import fiveguys.Tom.Cafeteria.Server.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final UserQueryService userQueryService;
    private final CafeteriaQueryService cafeteriaQueryService;

    private static int postPageSize = 20;

    //게시물 생성
    public Post createPost(PostCreateDTO boardCreateDTO) {
        Long cafeteriaId = boardCreateDTO.getCafeteriaId();
        Long userId = UserContext.getUserId();
        User user = userQueryService.getUserById(userId);
        Cafeteria cafeteria = cafeteriaQueryService.findById(cafeteriaId);
        Post post = Post.builder()
                .user(user)
                .title(boardCreateDTO.getTitle())
                .content(boardCreateDTO.getContent())
                .boardType(boardCreateDTO.getBoardType())
                .cafeteria(cafeteria)
                .build();
        return postRepository.save(post);
    }

    //boardType에 따라 특정 게시판의 전체 게시물 조회
    public List<PostPreviewDTO> getPostPageOrderedByTime(BoardType boardType, Long cafeteriaId, int page) {
        Cafeteria cafeteria = cafeteriaQueryService.findById(cafeteriaId);
        Page<Post> userPage = postRepository.findAllByCafeteriaAndBoardType(
                PageRequest.of(page - 1, postPageSize, Sort.by(Sort.Order.desc("createdAt") ) ),
                cafeteria, boardType);
        List<PostPreviewDTO> postPreviewDTOList = userPage.stream()
                .map(post -> PostPreviewDTO.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .publisherName(post.getUser().getName())
                        .uploadTime(post.getCreatedAt())
                        .build()
                )
                .collect(Collectors.toList());

        return postPreviewDTOList;
    }

//    public List<PostPreviewDTO> getPostPageOrderedByLike(BoardType boardType, Long cafeteriaId, int page) {
//        Cafeteria cafeteria = cafeteriaQueryService.findById(cafeteriaId);
//        Page<Post> userPage = postRepository.findAllByCafeteriaAndBoardType(
//                PageRequest.of(page - 1, postPageSize, Sort.by(Sort.Order.desc("createdAt") ) ),
//                cafeteria, boardType);
//        List<PostPreviewDTO> postPreviewDTOList = userPage.stream()
//                .map(post -> PostPreviewDTO.builder()
//                        .id(post.getId())
//                        .title(post.getTitle())
//                        .publisherName(post.getUser().getName())
//                        .uploadTime(post.getCreatedAt())
//                        .build()
//                )
//                .collect(Collectors.toList());
//
//        return postPreviewDTOList;
//    }


    //특정 게시물 조회
    public PostResponseDTO getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));
        PostResponseDTO responseDTO = PostResponseDTO.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .userId(post.getUser().getId())
                .boardType(post.getBoardType())
                .build();
        // 기타 필요한 속성 설정
        return responseDTO;
    }

//    public BoardResponseDTO updateBoard(Long id, BoardUpdateDTO boardDetails) {
//
//        Post post = postRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("Invalid board Id:" + id));
//
//        post.setTitle(boardDetails.getTitle());
//        post.setContent(boardDetails.getContent());
//        post.setBoardType(boardDetails.getBoardType());
//        // board.setLikeCount(boardDetails.getLikeCount()); // 직접 수정 불가 필드 제외
//        post.setAdminPick(boardDetails.isAdminPick());
//
//        post = postRepository.save(post);
//
//        // Board 엔티티를 BoardResponseDTO로 변환
//        return convertToBoardResponseDTO(post);
//    }

//    public void deleteBoard(Long id) {
//        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid board Id:" + id));
//        postRepository.delete(post);
//    }

    // 게시글 좋아요 토글
//    public BoardResponseDTO toggleLike(Long boardId, Long userId) {
//        Post post = postRepository.findById(boardId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다. id=" + boardId));
//
//        // User 엔티티 찾기
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. id=" + userId));
//
//        if (postLikeRepository.existsByUserIdAndBoardId(userId, boardId)) {
//            // 이미 좋아요를 한 경우, 좋아요 취소 처리
//            postLikeRepository.deleteByUserIdAndBoardId(userId, boardId);
//            post.setLikeCount(post.getLikeCount() - 1);
//        } else {
//            // 좋아요를 하지 않은 경우, 좋아요 처리
//            PostLike postLike = new PostLike();
//            postLike.setUser(user); // User 엔티티를 설정
//            postLike.setPost(post);
//            postLikeRepository.save(postLike);
//            post.setLikeCount(post.getLikeCount() + 1);
//        }
//
//        post = postRepository.save(post);
//
//        // Board 엔티티를 BoardResponseDTO로 변환
//        return convertToBoardResponseDTO(post);
//    }

    //게시글 삭제
    public void deleteBoard(Long id, BoardDeleteDTO boardDeleteDTO) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

        postRepository.delete(post);
    }


//    // Board 엔티티를 BoardResponseDTO로 변환하는 메소드
//    private BoardResponseDTO convertToBoardResponseDTO(Post post) {
//        BoardResponseDTO dto = new BoardResponseDTO();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setContent(post.getContent());
//        dto.setBoardType(post.getBoardType());
//        dto.setLikeCount(post.getLikeCount());
//        dto.setAdminPick(post.isAdminPick());
//        // 필요한 다른 필드들도 여기에 추가
//        return dto;
//    }

}

