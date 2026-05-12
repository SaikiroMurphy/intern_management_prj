package re.edu.intern_management_prj.model.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String major;
    private String studentClass;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;
    private String address;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime updatedAt;

}
