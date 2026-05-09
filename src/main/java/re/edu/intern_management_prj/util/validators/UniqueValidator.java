package re.edu.intern_management_prj.util.validators;

import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import re.edu.intern_management_prj.repository.UserRepository;
import re.edu.intern_management_prj.util.annotations.UniqueConstraint;
import java.lang.reflect.Method;

@Component
@RequiredArgsConstructor
public class UniqueValidator implements ConstraintValidator<UniqueConstraint, Object> {
    private final UserRepository userRepository;
    private String field;

    @Override
    public void initialize(UniqueConstraint annotation) {
        this.field = annotation.field();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null)
            return true;

        try {
            // Tạo tên method: existsByUsername, existsByEmail...
            String methodName = "existsBy" + capitalize(field);

            Method method = UserRepository.class
                    .getMethod(methodName, value.getClass());

            Boolean exists = (Boolean) method.invoke(userRepository, value);

            return !exists;

        } catch (Exception e) {
            throw new RuntimeException("Invalid field: " + field, e);
        }
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
