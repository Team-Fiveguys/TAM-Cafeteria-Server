package fiveguys.Tom.Cafeteria.Server.domain.diet.service;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Meals;

import java.time.LocalDate;
import java.util.List;

public interface DietQueryService {

    public Diet getDiet(Long cafeteriaId, LocalDate localDate, Meals meals);

    public boolean existsDiet(Long cafeteriaId, LocalDate localDate, Meals meals);

    public List<Diet> getThreeWeeksDiet(Long cafeteriaId);

}
