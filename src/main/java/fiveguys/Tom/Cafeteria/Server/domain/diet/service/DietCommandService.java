package fiveguys.Tom.Cafeteria.Server.domain.diet.service;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietRequestDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;

import java.util.List;

public interface DietCommandService {
    public Diet createDiet(Cafeteria cafeteria, DietRequestDTO.DietCreateDTO dietCreateDTO, List<Menu> menuList);

    public Diet addMenu(Diet diet, Menu menu);

    public Diet removeMenu(Diet diet, Menu menu);

    public Diet switchSoldOut(Diet diet);

    public Diet switchDayOff(DietRequestDTO.CheckDayOffDTO checkDayOffDTO);

}
