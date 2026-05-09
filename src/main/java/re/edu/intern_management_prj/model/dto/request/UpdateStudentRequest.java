package re.edu.intern_management_prj.model.dto.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateStudentRequest {
    private String studentCode;
    private String major;
    private String studentClass;
    private LocalDate dateOfBirth;
    private String address;
}
