package re.edu.intern_management_prj.util.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import re.edu.intern_management_prj.util.annotations.RoleConstraint;
import re.edu.intern_management_prj.util.enums.RoleEnum;

public class RoleValidator
        implements ConstraintValidator<RoleConstraint, String> {

    @Override
    public boolean isValid(String value,
            ConstraintValidatorContext context) {

        if (value == null || value.isBlank()) {
            return true;
        }

        for (RoleEnum role : RoleEnum.values()) {
            if (role.name().equalsIgnoreCase(value)) {
                return true;
            }
        }

        return false;
    }
}
