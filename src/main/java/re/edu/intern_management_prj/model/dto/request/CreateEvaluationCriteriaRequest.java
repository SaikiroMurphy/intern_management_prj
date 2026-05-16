package re.edu.intern_management_prj.model.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import re.edu.intern_management_prj.repository.EvaluationCriteriaRepository;
import re.edu.intern_management_prj.util.annotations.UniqueConstraint;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CreateEvaluationCriteriaRequest {
    @NotBlank(message = "Tên tiêu chí đánh giá không được để trống!")
    @UniqueConstraint(field = "criterionName", repository = EvaluationCriteriaRepository.class, message = "Tiêu chí đánh giá đã tồn tại!")
    private String criterionName;

    private String description;

    @NotNull(message = "Điểm tối đa không được để trống!")
    @Digits(integer = 3, fraction = 2)
    private BigDecimal maxScore;
}
