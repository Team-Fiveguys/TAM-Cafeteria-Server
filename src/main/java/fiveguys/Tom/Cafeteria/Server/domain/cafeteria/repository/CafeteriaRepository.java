package fiveguys.Tom.Cafeteria.Server.domain.cafeteria.repository;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CafeteriaRepository extends JpaRepository<Cafeteria, Long> {

    public boolean existsByName(String name);
    public Optional<Cafeteria> findByName(String name);
}
