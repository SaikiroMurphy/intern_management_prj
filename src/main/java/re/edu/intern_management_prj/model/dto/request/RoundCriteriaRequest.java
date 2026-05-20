package re.edu.intern_management_prj.model.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoundCriteriaRequest {
    @NotNull(message = "ID tiêu chí không được để trống!")
    private Integer criterionId;

    @NotNull(message = "Trọng số tiêu chí không được để trống!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Trọng số phải lớn hơn 0!")
    @DecimalMax(value = "1.0", message = "Trọng số phải nhỏ hơn 1!")
    @Digits(integer = 1, fraction = 1)
    private BigDecimal weight;
}
