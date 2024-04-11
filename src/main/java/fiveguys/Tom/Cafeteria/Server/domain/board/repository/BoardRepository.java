package fiveguys.Tom.Cafeteria.Server.domain.board.repository;

import fiveguys.Tom.Cafeteria.Server.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

}
