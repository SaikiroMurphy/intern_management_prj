package re.edu.intern_management_prj.util.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import re.edu.intern_management_prj.util.validators.UniqueValidator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
public @interface UniqueConstraint {

    String message() default "Already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String field();

    Class<?> repository();
}
