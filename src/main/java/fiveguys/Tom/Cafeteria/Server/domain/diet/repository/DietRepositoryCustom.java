package fiveguys.Tom.Cafeteria.Server.domain.diet.repository;

import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import java.util.List;

public interface DietRepositoryCustom {
    List<Diet> findDietsByThreeWeeks();
}
