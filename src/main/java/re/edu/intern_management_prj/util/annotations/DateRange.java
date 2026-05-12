package re.edu.intern_management_prj.util.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import re.edu.intern_management_prj.util.validators.DateRangeValidator;

@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
public @interface DateRange {
    String message() default "startDate không được sau endDate!";

    String start();

    String end();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
