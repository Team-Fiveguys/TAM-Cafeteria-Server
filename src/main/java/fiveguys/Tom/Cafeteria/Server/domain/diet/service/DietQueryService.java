package fiveguys.Tom.Cafeteria.Server.domain.diet.service;

import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public interface DietQueryService {
    public Diet getDiet(DayOfWeek dayOfWeek);
    public List<Diet> getDietListOfWeek();
}
