package fiveguys.Tom.Cafeteria.Server.domain.board.repository;

import fiveguys.Tom.Cafeteria.Server.domain.board.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
//    void deleteByUserIdAndBoardId(Long userId, Long boardId);
//    boolean existsByUserIdAndBoardId(Long userId, Long boardId);
}