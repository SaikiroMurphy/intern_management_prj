package re.edu.intern_management_prj.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import re.edu.intern_management_prj.model.dto.request.CreateStudentRequest;
import re.edu.intern_management_prj.model.dto.response.StudentDetailResponse;
import re.edu.intern_management_prj.model.dto.response.StudentResponse;
import re.edu.intern_management_prj.model.entity.Student;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {
    @Mapping(source = "user.fullName", target = "fullName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phoneNumber", target = "phoneNumber")
    @Mapping(source = "user.username", target = "username")
    StudentDetailResponse toStudentDetailResponse(Student student);

    @Mapping(source = "user.fullName", target = "fullName")
    StudentResponse toStudentResponse(Student student);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "studentId", ignore = true)
    Student toStudent(CreateStudentRequest request);
}
