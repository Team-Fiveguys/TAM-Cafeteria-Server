package fiveguys.Tom.Cafeteria.Server.domain.diet.repository;

import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.Optional;

public interface DietRepository extends JpaRepository<Diet, Long> {
    public Optional<Diet> findByDayOfWeek(DayOfWeek dayOfWeek);
}
