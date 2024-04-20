package fiveguys.Tom.Cafeteria.Server.exception.validation.annotation;

import fiveguys.Tom.Cafeteria.Server.exception.validation.validator.CafeteriaValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.hibernate.validator.internal.constraintvalidators.hv.EANValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EANValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailValidation {

    String message() default ".";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}