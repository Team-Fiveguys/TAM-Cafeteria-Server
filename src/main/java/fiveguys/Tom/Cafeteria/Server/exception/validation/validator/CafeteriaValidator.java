package fiveguys.Tom.Cafeteria.Server.exception.validation.validator;

import fiveguys.Tom.Cafeteria.Server.exception.validation.annotation.CafeteriaValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import fiveguys.Tom.Cafeteria.Server.apiPayload.code.status.ErrorStatus;
public class CafeteriaValidator implements ConstraintValidator<CafeteriaValidation,String> {

    @Override
    public void initialize(CafeteriaValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.CAFETERIA_IS_EMPTY.toString()).addConstraintViolation();
        }
        else if (value.isBlank()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.CAFETERIA_IS_BLANK.toString()).addConstraintViolation();
        }
        else if (value.length() > 100) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.CAFETERIA_TOO_LONG.toString()).addConstraintViolation();
        }
        return true;
    }
}
