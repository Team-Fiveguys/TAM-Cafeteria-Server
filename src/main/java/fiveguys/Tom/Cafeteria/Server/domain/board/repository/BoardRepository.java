package fiveguys.Tom.Cafeteria.Server.domain.board.repository;

import fiveguys.Tom.Cafeteria.Server.domain.board.entity.Board;
import fiveguys.Tom.Cafeteria.Server.domain.board.entity.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByBoardType(BoardType boardType);
}
