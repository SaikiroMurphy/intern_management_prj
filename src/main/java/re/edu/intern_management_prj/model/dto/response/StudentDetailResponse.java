package re.edu.intern_management_prj.model.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class StudentDetailResponse {
    private Integer studentId;
    private String studentCode;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String major;
    private String studentClass;
    private String dateOfBirth;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
