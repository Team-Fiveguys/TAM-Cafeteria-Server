package fiveguys.Tom.Cafeteria.Server.domain.board.service;

import fiveguys.Tom.Cafeteria.Server.apiPayload.code.status.ErrorStatus;
import fiveguys.Tom.Cafeteria.Server.auth.UserContext;
import fiveguys.Tom.Cafeteria.Server.domain.board.dto.*;
import fiveguys.Tom.Cafeteria.Server.domain.board.entity.Post;
import fiveguys.Tom.Cafeteria.Server.domain.board.entity.PostLike;
import fiveguys.Tom.Cafeteria.Server.domain.board.entity.BoardType;
import fiveguys.Tom.Cafeteria.Server.domain.board.entity.Report;
import fiveguys.Tom.Cafeteria.Server.domain.board.repository.PostLikeRepository;
import fiveguys.Tom.Cafeteria.Server.domain.board.repository.PostRepository;
import fiveguys.Tom.Cafeteria.Server.domain.board.repository.ReportRepository;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.service.CafeteriaQueryService;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.Role;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.User;
import fiveguys.Tom.Cafeteria.Server.domain.user.service.UserQueryService;
import fiveguys.Tom.Cafeteria.Server.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final ReportRepository reportRepository;
    private final UserQueryService userQueryService;
    private final CafeteriaQueryService cafeteriaQueryService;

    private static int postPageSize = 20;

    //게시물 생성
    @Transactional
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
        Long userId = UserContext.getUserId();
        User user = userQueryService.getUserById(userId);
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
                        .likeCount(post.getLikeCount())
                        .uploadTime(post.getCreatedAt())
                        .toggleLike(postLikeRepository.existsByUserAndPost(user, post))
                        .build()
                )
                .collect(Collectors.toList());

        return postPreviewDTOList;
    }

    public List<PostPreviewDTO> getPostPageOrderedByLike(BoardType boardType, Long cafeteriaId, int page) {
        Cafeteria cafeteria = cafeteriaQueryService.findById(cafeteriaId);
        Page<Post> userPage = postRepository.findAllByCafeteriaAndBoardType(
                PageRequest.of(page - 1, postPageSize, Sort.by(
                        Sort.Order.desc("likeCount"),
                        Sort.Order.desc("createdAt") ) ),
                cafeteria, boardType);
        List<PostPreviewDTO> postPreviewDTOList = userPage.stream()
                .map(post -> PostPreviewDTO.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .publisherName(post.getUser().getName())
                        .likeCount(post.getLikeCount())
                        .uploadTime(post.getCreatedAt())
                        .build()
                )
                .collect(Collectors.toList());

        return postPreviewDTOList;
    }


    //특정 게시물 조회
    public PostResponseDTO getPostById(Long id) {
        Long userId = UserContext.getUserId();
        User user = userQueryService.getUserById(userId);

        Post post = postRepository.findById(id).orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));
        PostResponseDTO responseDTO = PostResponseDTO.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .userId(post.getUser().getId())
                .boardType(post.getBoardType())
                .likeCount(post.getLikeCount())
                .toggleLike(postLikeRepository.existsByUserAndPost(user, post))
                .build();
        // 기타 필요한 속성 설정
        return responseDTO;
    }

    @Transactional
    public Post updatePost(Long postId, PostUpdateDTO postUpdateDTO) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new GeneralException(ErrorStatus.POST_NOT_FOUND)
        );
        Long userId = UserContext.getUserId();
        User user = userQueryService.getUserById(userId);
        if( !user.equals(post.getUser()) && user.getRole().equals(Role.MEMBER)){ // 본인이 아니거나 관리자가 아니면 예외
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }
        if(post.getBoardType().equals(BoardType.MENU_REQUEST)){ // 메뉴 건의는 수정 기능이 없으므로 예외 발생
            throw new GeneralException(ErrorStatus.INVALID_POST_TYPE);
        }
        post.updatePost(postUpdateDTO.getTitle(), postUpdateDTO.getContent());
        return post;
    }

    // 게시글 좋아요 토글
    @Transactional
    public boolean toggleLike(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));

        Long userId = UserContext.getUserId();
        User user = userQueryService.getUserById(userId);
        if (postLikeRepository.existsByUserAndPost(user, post)) {
            // 이미 좋아요를 한 경우, 좋아요 취소 처리
            postLikeRepository.deleteByUserAndPost(user,post);
            post.setLikeCount(post.getLikeCount() - 1);
            return false;
        } else {
            // 좋아요를 하지 않은 경우, 좋아요 처리
            PostLike postLike = new PostLike();
            postLike.setUser(user); // User 엔티티를 설정
            postLike.setPost(post);
            postLikeRepository.save(postLike);
            post.setLikeCount(post.getLikeCount() + 1);
            return true;
        }
    }

    @Transactional
    public boolean reportPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));

        Long userId = UserContext.getUserId();
        User user = userQueryService.getUserById(userId);
        if (reportRepository.existsByUserAndPost(user, post)) {
            throw new GeneralException(ErrorStatus.DUPLICATED_REPORT);
        } else {
            Report report = Report.createReport(user, post);
            post.setReportCount(post.getReportCount() + 1);
            reportRepository.save(report);
            return true;
        }
    }



    @Transactional
    //게시글 삭제
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
        Long userId = UserContext.getUserId();
        User user = userQueryService.getUserById(userId);
        if( !user.equals(post.getUser()) && user.getRole().equals(Role.MEMBER)){ // 본인이 아니거나 관리자가 아니면 예외
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }
        reportRepository.deleteAllByPost(post);
        postLikeRepository.deleteAllByPost(post);
        postRepository.delete(post);
    }
}

