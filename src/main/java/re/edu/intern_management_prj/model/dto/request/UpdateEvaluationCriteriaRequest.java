package re.edu.intern_management_prj.model.dto.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateEvaluationCriteriaRequest {
    private String criterionName;

    private String description;

    private BigDecimal maxScore;
}
