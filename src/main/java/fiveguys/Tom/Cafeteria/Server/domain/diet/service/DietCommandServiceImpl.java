package fiveguys.Tom.Cafeteria.Server.domain.diet.service;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.service.CafeteriaQueryService;
import fiveguys.Tom.Cafeteria.Server.domain.diet.converter.DietConverter;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietRequestDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Meals;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.MenuDiet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.repository.MenuDietRepository;
import fiveguys.Tom.Cafeteria.Server.domain.diet.repository.DietRepository;
import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;
import fiveguys.Tom.Cafeteria.Server.domain.menu.service.MenuQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class DietCommandServiceImpl implements DietCommandService{
    private final DietRepository dietRepository;
    private final CafeteriaQueryService cafeteriaQueryService;
    private final DietQueryService dietQueryService;
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
        if(diet.getMenuDietList().isEmpty()){ // 빈 식단이면 식단자체를 삭제 -> cascade.REMOVE 식단 메뉴 삭제
            dietRepository.delete(diet);
        }
        else{
            menuDietRepository.delete(menuDiet); // 빈 식단이 아니면 식단메뉴만 삭제
        }
        return diet;
    }

    @Override
    public Diet switchSoldOut(Diet diet) {
        diet.switchSoldOut();
        return diet;
    }

    @Override
    public Diet switchDayOff(DietRequestDTO.CheckDayOffDTO checkDayOffDTO) {
        Cafeteria cafeteria = cafeteriaQueryService.findById(checkDayOffDTO.getCafeteriaId());
        List<Diet> dietList = dietQueryService.getDietsOfDay(cafeteria, checkDayOffDTO.getLocalDate());
        if( dietList.isEmpty() ){ // 식단이 없으면 만들어서
            Diet breakfast = Diet.builder()
                    .meals(Meals.BREAKFAST)
                    .localDate(checkDayOffDTO.getLocalDate())
                    .cafeteria(cafeteria)
                    .dayOff(true)
                    .build();
            Diet lunch = Diet.builder()
                    .meals(Meals.LUNCH)
                    .localDate(checkDayOffDTO.getLocalDate())
                    .cafeteria(cafeteria)
                    .dayOff(true)
                    .build();
            createDiet(cafeteria, breakfast, new ArrayList<>());
            createDiet(cafeteria, lunch, new ArrayList<>());
            return breakfast;
        }
        dietList.forEach(diet ->  diet.switchDayOff());
        return dietList.get(0);
    }
}
