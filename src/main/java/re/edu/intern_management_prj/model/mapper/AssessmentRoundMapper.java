package re.edu.intern_management_prj.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import re.edu.intern_management_prj.model.dto.request.AssessmentRoundRequest;
import re.edu.intern_management_prj.model.dto.response.AssessmentRoundDetailResponse;
import re.edu.intern_management_prj.model.dto.response.AssessmentRoundResponse;
import re.edu.intern_management_prj.model.entity.AssessmentRound;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AssessmentRoundMapper {

    @Mapping(target = "phase", ignore = true)
    AssessmentRound toAssessmentRound(AssessmentRoundRequest request);

    @Mapping(source = "phase.phaseName", target = "phaseName")
    AssessmentRoundResponse toRoundResponse(AssessmentRound entity);

    @Mapping(source = "phase.phaseName", target = "phaseName")
    AssessmentRoundDetailResponse toRoundDetailResponse(AssessmentRound entity);
}
