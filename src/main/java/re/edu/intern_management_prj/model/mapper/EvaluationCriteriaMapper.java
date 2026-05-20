package re.edu.intern_management_prj.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import re.edu.intern_management_prj.model.dto.request.CreateEvaluationCriteriaRequest;
import re.edu.intern_management_prj.model.dto.response.EvaluationCriteriaDetailResponse;
import re.edu.intern_management_prj.model.dto.response.EvaluationCriteriaResponse;
import re.edu.intern_management_prj.model.entity.EvaluationCriteria;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EvaluationCriteriaMapper {
    EvaluationCriteriaResponse toEvaluationCriteriaResponse(EvaluationCriteria crit);

    EvaluationCriteriaDetailResponse toEvaluationCriteriaDetailResponse(EvaluationCriteria crit);

    EvaluationCriteria toEvaluationCriteria(CreateEvaluationCriteriaRequest req);
}
