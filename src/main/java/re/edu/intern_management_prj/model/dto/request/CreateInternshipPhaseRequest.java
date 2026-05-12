package re.edu.intern_management_prj.model.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import re.edu.intern_management_prj.repository.InternshipPhaseRepository;
import re.edu.intern_management_prj.util.annotations.UniqueConstraint;
import re.edu.intern_management_prj.util.annotations.DateRange;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@DateRange(start = "startDate", end = "endDate")
public class CreateInternshipPhaseRequest {
    @NotBlank(message = "Tên giai đoạn không được để trống!")
    @UniqueConstraint(field = "phaseName", repository = InternshipPhaseRepository.class, message = "Tên giai đoạn đã tồn tại!")
    private String phaseName;

    @NotNull(message = "Ngày bắt đầu không được để trống!")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDate;

    @NotNull(message = "Ngày kết thúc không được để trống!")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate endDate;

    private String description;
}
