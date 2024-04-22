package fiveguys.Tom.Cafeteria.Server.domain.diet.repository;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Meals;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DietRepository extends JpaRepository<Diet, Long> {
    List<Diet> findByCafeteriaAndLocalDate(Cafeteria cafeteria, LocalDate localDate);
    Optional<Diet> findByCafeteriaAndLocalDateAndMeals(Cafeteria cafeteria, LocalDate date, Meals meals);
    List<Diet> findAllByCafeteriaAndYearAndMonthAndWeekAndMeals(Cafeteria cafeteria, int year, int month, int week, Meals meals);
}
