package re.edu.intern_management_prj.model.mapper;

import org.mapstruct.Mapper;
// import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import re.edu.intern_management_prj.model.dto.request.CreateInternshipPhaseRequest;
import re.edu.intern_management_prj.model.dto.response.InternshipPhaseDetailResponse;
import re.edu.intern_management_prj.model.dto.response.InternshipPhaseResponse;
import re.edu.intern_management_prj.model.entity.InternshipPhase;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InternshipPhaseMapper {
    InternshipPhaseResponse toInternshipPhaseResponse(InternshipPhase internshipPhase);

    InternshipPhaseDetailResponse toInternshipPhaseDetailResponse(InternshipPhase internshipPhase);

    InternshipPhase toInternshipPhase(CreateInternshipPhaseRequest request);
    
    // InternshipPhase toInternshipPhase(UpdateInternshipPhaseRequest request);
    
}
