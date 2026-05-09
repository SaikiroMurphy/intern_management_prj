package re.edu.intern_management_prj.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentResponse {
    private Integer studentId;
    private String studentCode;
    private String fullName;
    private String email;
    private String major;
    private String studentClass;
}
