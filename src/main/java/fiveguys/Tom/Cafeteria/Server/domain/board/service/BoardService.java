package fiveguys.Tom.Cafeteria.Server.domain.board.service;

import fiveguys.Tom.Cafeteria.Server.domain.board.entity.Board;
import fiveguys.Tom.Cafeteria.Server.domain.board.entity.BoardType;
import fiveguys.Tom.Cafeteria.Server.domain.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }

    //boardType에 따라 특정 게시판의 전체 게시물 조회
    public List<Board> getAllBoardsByType(BoardType boardType) {
        return boardRepository.findAllByBoardType(boardType);
    }

    //전체 게시물 조회
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public Board getBoardById(Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid board Id:" + id));
    }

    public Board updateBoard(Long id, Board boardDetails) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid board Id:" + id));

        board.setTitle(boardDetails.getTitle());
        board.setContent(boardDetails.getContent());
        board.setBoardType(boardDetails.getBoardType());
        board.setLikeCount(boardDetails.getLikeCount());
        board.setAdminPick(boardDetails.isAdminPick());

        return boardRepository.save(board);
    }

    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid board Id:" + id));
        boardRepository.delete(board);
    }

    //게시글 좋아요
    public Board toggleLike(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다. id=" + boardId));
        board.setLikeCount(board.getLikeCount() + 1);
        return boardRepository.save(board);
    }

}

