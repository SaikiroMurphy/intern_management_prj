package re.edu.intern_management_prj.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import re.edu.intern_management_prj.repository.MentorRepository;
import re.edu.intern_management_prj.util.annotations.UniqueConstraint;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateMentorRequest {
    @NotNull(message = "ID giảng viên không được để trống!")
    @UniqueConstraint(field = "id", repository = MentorRepository.class, message = "ID giảng viên đã tồn tại!")
    private int mentorId;

    private String academicRank;
    
    private String department;

}
