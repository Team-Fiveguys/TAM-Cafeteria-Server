package fiveguys.Tom.Cafeteria.Server.domain.diet.service;

import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.MenuDiet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.repository.DietRepository;
import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;
import fiveguys.Tom.Cafeteria.Server.domain.menu.service.MenuQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DietCommandServiceImpl implements DietCommandService{
    private final DietRepository dietRepository;
    private final MenuQueryService menuQueryService;

    @Override
    public Diet createDiet(Diet diet, List<Long> menuIdList) {
        Diet savedDiet = dietRepository.save(diet);
        menuIdList.stream()
                .forEach( id -> {
                    MenuDiet.createMenuDiet(menuQueryService.findById(id), savedDiet);
                });
        return savedDiet;
    }
}
