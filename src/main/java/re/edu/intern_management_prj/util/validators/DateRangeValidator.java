package re.edu.intern_management_prj.util.validators;

import java.time.LocalDate;

import org.springframework.beans.BeanWrapperImpl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import re.edu.intern_management_prj.util.annotations.DateRange;

public class DateRangeValidator
        implements ConstraintValidator<DateRange, Object> {

    private String startField;
    private String endField;

    @Override
    public void initialize(DateRange constraintAnnotation) {
        this.startField = constraintAnnotation.start();
        this.endField = constraintAnnotation.end();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        try {
            BeanWrapperImpl bean = new BeanWrapperImpl(obj);

            LocalDate start = (LocalDate) bean.getPropertyValue(startField);
            LocalDate end = (LocalDate) bean.getPropertyValue(endField);

            if (start == null || end == null) {
                return true;
            }

            if (start.isAfter(end)) {
                context.disableDefaultConstraintViolation();

                context.buildConstraintViolationWithTemplate(
                        "startDate không được sau endDate!")
                        .addPropertyNode("endDate")
                        .addConstraintViolation();

                return false;
            }

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
