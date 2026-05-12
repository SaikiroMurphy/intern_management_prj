package re.edu.intern_management_prj.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import re.edu.intern_management_prj.repository.StudentRepository;
import re.edu.intern_management_prj.util.annotations.UniqueConstraint;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateStudentRequest {
    @NotBlank(message = "ID sinh viên không được để trống!")
    @UniqueConstraint(field = "studentId", repository = StudentRepository.class, message = "ID sinh viên đã tồn tại!")
    private Integer studentId;

    @NotBlank(message = "Mã sinh viên không được để trống!")
    @Pattern(regexp = "^K\\d{2}[A-Z]{2,6}\\d{3}$", message = "Mã sinh viên không hợp lệ! (Ví dụ: K23SE123)")
    @UniqueConstraint(field = "studentCode", repository = StudentRepository.class, message = "Mã sinh viên đã tồn tại!")
    private String studentCode;

    private String major;
    private String studentClass;
    private String dateOfBirth;
    private String address;
}
