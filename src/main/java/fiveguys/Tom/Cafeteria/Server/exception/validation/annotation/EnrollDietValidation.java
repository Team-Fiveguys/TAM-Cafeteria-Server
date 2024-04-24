package fiveguys.Tom.Cafeteria.Server.exception.validation.annotation;

import fiveguys.Tom.Cafeteria.Server.exception.validation.validator.DietValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DietValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface EnrollDietValidation {

    String message() default "DIET_IS_ALREADY_ENROLLED";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
