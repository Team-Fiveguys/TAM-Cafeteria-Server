package fiveguys.Tom.Cafeteria.Server.domain.cafeteria.repository;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeteriaRepository extends JpaRepository<Cafeteria, Long> {
}
