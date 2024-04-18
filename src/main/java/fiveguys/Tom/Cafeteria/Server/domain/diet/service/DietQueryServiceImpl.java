package fiveguys.Tom.Cafeteria.Server.domain.diet.service;

import fiveguys.Tom.Cafeteria.Server.apiPayload.code.status.ErrorStatus;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Meals;
import fiveguys.Tom.Cafeteria.Server.domain.diet.repository.DietRepository;
import fiveguys.Tom.Cafeteria.Server.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class DietQueryServiceImpl implements DietQueryService{
    private final DietRepository dietRepository;

    @Override
    public Diet getDiet(Long dietId) {
        return dietRepository.findById(dietId).orElseThrow(()-> new GeneralException(ErrorStatus.DIET_NOT_FOUND));
    }

    @Override
    public Diet getDiet(Cafeteria cafeteria, LocalDate localDate, Meals meals) {
        Diet diet = dietRepository.findByCafeteriaAndLocalDateAndMeals(cafeteria, localDate, meals)
                .orElseThrow(() -> new GeneralException(ErrorStatus.DIET_NOT_FOUND));
        return diet;
    }

    @Override
    public List<Diet> getDietListOfWeek(Cafeteria cafeteria, int year, int month, int weekNum, Meals meals) {
        List<Diet> dietList = dietRepository
                .findAllByCafeteriaAndYearAndMonthAndWeekAndMeals(cafeteria, year, month, weekNum, meals);
        dietList.stream()
                .sorted(Comparator.comparing(Diet::getLocalDate));
        return dietList;
    }
}
