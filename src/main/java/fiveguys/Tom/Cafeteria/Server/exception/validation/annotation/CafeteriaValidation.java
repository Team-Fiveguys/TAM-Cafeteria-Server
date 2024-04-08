package fiveguys.Tom.Cafeteria.Server.exception.validation.annotation;

import fiveguys.Tom.Cafeteria.Server.exception.validation.validator.CafeteriaValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CafeteriaValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CafeteriaValidation {

    String message() default ".";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}