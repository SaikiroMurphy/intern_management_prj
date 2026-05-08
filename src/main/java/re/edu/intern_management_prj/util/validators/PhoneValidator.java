package re.edu.intern_management_prj.util.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import re.edu.intern_management_prj.util.annotations.PhoneConstraint;

public class PhoneValidator
        implements ConstraintValidator<PhoneConstraint, String> {

    private static final String PHONE_REGEX = "^(0|\\+84)[0-9]{9}$";

    @Override
    public boolean isValid(String value,
            ConstraintValidatorContext context) {

        if (value == null || value.isBlank()) {
            return true;
        }

        return value.matches(PHONE_REGEX);
    }
}
