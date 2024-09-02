package fiveguys.Tom.Cafeteria.Server.domain.diet.service;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietRequestDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Meals;
import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;

import java.time.LocalDate;
import java.util.List;

public interface DietCommandService {
    public Diet createDiet(Long cafeteriaId, DietRequestDTO.DietCreateDTO dietCreateDTO, List<Menu> menuList);
    public void removeDiet(Long cafeteriaId, LocalDate date, Meals meals);

    public Diet removeMenu(Diet diet, Menu menu);

    public Diet switchSoldOut(Diet diet);

    public Diet switchDayOff(DietRequestDTO.CheckDayOffDTO checkDayOffDTO);

}
