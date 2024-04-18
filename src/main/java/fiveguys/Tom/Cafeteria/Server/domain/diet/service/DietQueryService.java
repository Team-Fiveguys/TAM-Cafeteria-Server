package fiveguys.Tom.Cafeteria.Server.domain.diet.service;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Meals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public interface DietQueryService {

    public Diet getDiet(Long dietId);
    public Diet getDiet(Cafeteria cafeteria, LocalDate localDate, Meals meals);
    public List<Diet> getDietListOfWeek(Cafeteria cafeteria, int year, int month, int weekNum, Meals meals);

}
