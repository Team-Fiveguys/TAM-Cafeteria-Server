package fiveguys.Tom.Cafeteria.Server.domain.diet.service;

import fiveguys.Tom.Cafeteria.Server.apiPayload.code.status.ErrorStatus;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.repository.CafeteriaRepository;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Meals;
import fiveguys.Tom.Cafeteria.Server.domain.diet.repository.DietRepository;
import fiveguys.Tom.Cafeteria.Server.domain.diet.repository.DietRepositoryCustom;
import fiveguys.Tom.Cafeteria.Server.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;


@Service
@RequiredArgsConstructor
public class DietQueryServiceImpl implements DietQueryService{
    private final DietRepository dietRepository;
    private final DietRepositoryCustom dietRepositoryCustom;
    private final CafeteriaRepository cafeteriaRepository;


    @Override
    public Diet getDiet(Long cafeteriaId, LocalDate localDate, Meals meals) {
        Cafeteria cafeteria = cafeteriaRepository.getReferenceById(cafeteriaId);
        Diet diet = dietRepository.findByCafeteriaAndLocalDateAndMealsWithMenuDietAndMenu(cafeteria, localDate, meals)
                .orElseThrow(() -> new GeneralException(ErrorStatus.DIET_NOT_FOUND));
        return diet;
    }

    @Override
    public boolean existsDiet(Long cafeteriaId, LocalDate localDate, Meals meals) {
        Cafeteria cafeteria = cafeteriaRepository.getReferenceById(cafeteriaId);
        return dietRepository.existsByCafeteriaAndLocalDateAndMeals(cafeteria, localDate, meals);
    }

    @Override
    public List<Diet> getThreeWeeksDiet(Long cafeteriaId) {
        return dietRepositoryCustom.findDietsByThreeWeeks(cafeteriaId);
    }

}
