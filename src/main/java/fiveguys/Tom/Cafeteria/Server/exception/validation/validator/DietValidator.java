package fiveguys.Tom.Cafeteria.Server.exception.validation.validator;

import fiveguys.Tom.Cafeteria.Server.domain.diet.dto.DietRequestDTO;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.service.DietQueryService;
import fiveguys.Tom.Cafeteria.Server.exception.validation.annotation.EnrollDietValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
/**
 * 이미 식단이 등록 되어있는지 아닌지 유효성을 검증, 이미 등록되어있다면 false
**/
@Component
@RequiredArgsConstructor
public class DietValidator implements ConstraintValidator<EnrollDietValidation, DietRequestDTO.DietCreateDTO> {
    private final DietQueryService dietQueryService;

    @Override
    public void initialize(EnrollDietValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(DietRequestDTO.DietCreateDTO value, ConstraintValidatorContext context) {
        Diet diet = dietQueryService.getDiet(value.getCafeteriaId(), value.getDate(), value.getMeals());
        if(diet != null){
            return false;
        }
        return true;
    }
}
