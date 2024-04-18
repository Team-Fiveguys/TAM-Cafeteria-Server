package fiveguys.Tom.Cafeteria.Server.domain.board.service;

import fiveguys.Tom.Cafeteria.Server.domain.board.dto.*;
import fiveguys.Tom.Cafeteria.Server.domain.board.entity.Board;
import fiveguys.Tom.Cafeteria.Server.domain.board.entity.BoardLike;
import fiveguys.Tom.Cafeteria.Server.domain.board.entity.BoardType;
import fiveguys.Tom.Cafeteria.Server.domain.board.repository.BoardRepository;
import fiveguys.Tom.Cafeteria.Server.domain.board.repository.BoardLikeRepository;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.User;
import fiveguys.Tom.Cafeteria.Server.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fiveguys.Tom.Cafeteria.Server.exception.ResourceNotFoundException;


import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardLikeRepository boardLikeRepository;

    private final UserRepository userRepository;

    @Autowired
    public BoardService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //게시물 생성
    public Board createBoard(BoardCreateDTO boardCreateDTO) {
        Board board = new Board();
        board.setTitle(boardCreateDTO.getTitle());
        board.setContent(boardCreateDTO.getContent());
        board.setBoardType(boardCreateDTO.getBoardType());
        return boardRepository.save(board);
    }

    //boardType에 따라 특정 게시판의 전체 게시물 조회
    public List<Board> getAllBoardsByType(BoardType boardType) {
        return boardRepository.findAllByBoardType(boardType);
    }

    //특정 게시물 조회
    public BoardResponseDTO getBoardById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Board", "id", id));
        BoardResponseDTO boardResponseDTO = new BoardResponseDTO();
        boardResponseDTO.setId(board.getId());
        boardResponseDTO.setTitle(board.getTitle());
        boardResponseDTO.setContent(board.getContent());
        boardResponseDTO.setBoardType(board.getBoardType());
        // 기타 필요한 속성 설정
        return boardResponseDTO;
    }

    public BoardResponseDTO updateBoard(Long id, BoardUpdateDTO boardDetails) {

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid board Id:" + id));

        board.setTitle(boardDetails.getTitle());
        board.setContent(boardDetails.getContent());
        board.setBoardType(boardDetails.getBoardType());
        // board.setLikeCount(boardDetails.getLikeCount()); // 직접 수정 불가 필드 제외
        board.setAdminPick(boardDetails.isAdminPick());

        board = boardRepository.save(board);

        // Board 엔티티를 BoardResponseDTO로 변환
        return convertToBoardResponseDTO(board);
    }

    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid board Id:" + id));
        boardRepository.delete(board);
    }

    // 게시글 좋아요 토글
    // 게시글 좋아요 토글
    public BoardResponseDTO toggleLike(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다. id=" + boardId));

        // User 엔티티 찾기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. id=" + userId));

        if (boardLikeRepository.existsByUserIdAndBoardId(userId, boardId)) {
            // 이미 좋아요를 한 경우, 좋아요 취소 처리
            boardLikeRepository.deleteByUserIdAndBoardId(userId, boardId);
            board.setLikeCount(board.getLikeCount() - 1);
        } else {
            // 좋아요를 하지 않은 경우, 좋아요 처리
            BoardLike boardLike = new BoardLike();
            boardLike.setUser(user); // User 엔티티를 설정
            boardLike.setBoard(board);
            boardLikeRepository.save(boardLike);
            board.setLikeCount(board.getLikeCount() + 1);
        }

        board = boardRepository.save(board);

        // Board 엔티티를 BoardResponseDTO로 변환
        return convertToBoardResponseDTO(board);
    }

    // Board 엔티티를 BoardResponseDTO로 변환하는 메소드
    private BoardResponseDTO convertToBoardResponseDTO(Board board) {
        BoardResponseDTO dto = new BoardResponseDTO();
        dto.setId(board.getId());
        dto.setTitle(board.getTitle());
        dto.setContent(board.getContent());
        dto.setBoardType(board.getBoardType());
        dto.setLikeCount(board.getLikeCount());
        dto.setAdminPick(board.isAdminPick());
        // 필요한 다른 필드들도 여기에 추가
        return dto;
    }

}

