package fiveguys.Tom.Cafeteria.Server.domain.diet.repository;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Meals;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface DietRepository extends JpaRepository<Diet, Long> {
    public Optional<Diet> findByDayOfWeek(DayOfWeek dayOfWeek);
    Optional<Diet> findByDayOfWeekAndCafeteriaAndMeals(DayOfWeek dayOfWeek, Cafeteria cafeteria, Meals meals);
}
