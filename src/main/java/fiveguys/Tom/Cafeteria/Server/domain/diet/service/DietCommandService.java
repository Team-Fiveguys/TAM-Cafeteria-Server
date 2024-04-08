package fiveguys.Tom.Cafeteria.Server.domain.diet.service;

import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;

import java.util.List;

public interface DietCommandService {
    public Diet createDiet(Diet diet, List<Long> menuIdList);
}
