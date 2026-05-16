package re.edu.intern_management_prj.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EvaluationCriteriaResponse {
    private int criterionId;
    private String criterionName;
}
