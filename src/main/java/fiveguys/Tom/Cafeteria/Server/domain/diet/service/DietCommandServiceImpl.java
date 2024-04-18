package fiveguys.Tom.Cafeteria.Server.domain.diet.service;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.MenuDiet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.repository.MenuDietRepository;
import fiveguys.Tom.Cafeteria.Server.domain.diet.repository.DietRepository;
import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;
import fiveguys.Tom.Cafeteria.Server.domain.menu.service.MenuQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class DietCommandServiceImpl implements DietCommandService{
    private final DietRepository dietRepository;
    private final MenuDietRepository menuDietRepository;

    @Override
    public Diet createDiet(Cafeteria cafeteria, Diet diet, List<Menu> menuList) {
        menuList.stream()
                .forEach( (menu) ->MenuDiet.createMenuDiet(menu, diet));
        diet.setCafeteria(cafeteria);
        diet.setDateInfo();
        Diet savedDiet = dietRepository.save(diet);
        return savedDiet;
    }

    @Override
    public Diet addMenu(Diet diet, Menu menu) {
        diet.addMenu(menu);
        return diet;
    }

    @Override
    public Diet removeMenu(Diet diet, Menu menu) {
        MenuDiet menuDiet = menuDietRepository.findFirstByDietAndMenu(diet, menu);
        diet.remove(menuDiet);
        menuDietRepository.delete(menuDiet);
        return diet;
    }

    @Override
    public Diet switchSoldOut(Diet diet) {
        diet.switchSoldOut();
        return diet;
    }
}
