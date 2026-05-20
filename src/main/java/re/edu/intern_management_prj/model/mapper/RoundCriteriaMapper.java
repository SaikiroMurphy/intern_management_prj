package re.edu.intern_management_prj.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import re.edu.intern_management_prj.model.dto.request.RoundCriteriaRequest;
import re.edu.intern_management_prj.model.dto.response.RoundCriteriaResponse;
import re.edu.intern_management_prj.model.entity.EvaluationCriteria;
import re.edu.intern_management_prj.model.entity.RoundCriteria;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoundCriteriaMapper {
    @Mapping(target = "round", ignore = true)
    @Mapping(target = "criterion", source = "criterionId")
    RoundCriteria toRoundCriteria(RoundCriteriaRequest req);

    @Mapping(target = "roundName", source = "round.roundName")
    @Mapping(target = "criterionName", source = "criterion.criterionName")
    RoundCriteriaResponse toResponse(RoundCriteria entity);

    default EvaluationCriteria map(Integer criterionId) {

        if (criterionId == null) {
            return null;
        }

        EvaluationCriteria criterion = new EvaluationCriteria();
        criterion.setCriterionId(criterionId);

        return criterion;
    }
}
