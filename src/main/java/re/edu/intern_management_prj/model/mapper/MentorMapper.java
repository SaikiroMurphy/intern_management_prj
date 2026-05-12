package re.edu.intern_management_prj.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import re.edu.intern_management_prj.model.dto.request.CreateMentorRequest;
import re.edu.intern_management_prj.model.dto.response.MentorDetailResponse;
import re.edu.intern_management_prj.model.dto.response.MentorResponse;
import re.edu.intern_management_prj.model.entity.Mentor;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MentorMapper {
    @Mapping(source = "user.fullName", target = "fullName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phoneNumber", target = "phoneNumber")
    @Mapping(source = "user.username", target = "username")
    MentorDetailResponse toMentorDetailResponse(Mentor mentor);

    @Mapping(source = "user.fullName", target = "fullName")
    MentorResponse toMentorResponse(Mentor mentor);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "mentorId", ignore = true)
    Mentor toMentor(CreateMentorRequest request);
}
