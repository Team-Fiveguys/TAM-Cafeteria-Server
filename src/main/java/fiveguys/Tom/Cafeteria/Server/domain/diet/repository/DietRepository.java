package fiveguys.Tom.Cafeteria.Server.domain.diet.repository;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Meals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DietRepository extends JpaRepository<Diet, Long> {
    List<Diet> findByCafeteriaAndLocalDate(Cafeteria cafeteria, LocalDate localDate);

    @Query("SELECT d FROM Diet d " +
            "LEFT JOIN FETCH d.menuDietList md " +
            "LEFT JOIN FETCH md.menu m " +
            "WHERE d.cafeteria = :cafeteria " +
            "AND d.localDate = :localDate " +
            "AND d.meals = :meals")
    Optional<Diet> findByCafeteriaAndLocalDateAndMealsWithMenuDietAndMenu(
            @Param("cafeteria") Cafeteria cafeteria,
            @Param("localDate") LocalDate localDate,
            @Param("meals") Meals meals
    );
    @Query("SELECT d FROM Diet d " +
            "LEFT JOIN FETCH d.dietPhoto dp " +
            "LEFT JOIN FETCH d.menuDietList md " +
            "LEFT JOIN FETCH md.menu m " +
            "WHERE d.cafeteria = :cafeteria " +
            "AND d.year = :year " +
            "AND d.month = :month " +
            "AND d.week = :week " +
            "AND d.meals = :meals "
    )
    List<Diet> findAllByCafeteriaAndYearAndMonthAndWeekAndMeals(
            @Param("cafeteria") Cafeteria cafeteria,
            @Param("year") int year,
            @Param("month") int month,
            @Param("week") int week,
            @Param("meals") Meals meals
    );

    boolean existsByCafeteriaAndLocalDateAndMeals(Cafeteria cafeteria, LocalDate date, Meals meals);
}
