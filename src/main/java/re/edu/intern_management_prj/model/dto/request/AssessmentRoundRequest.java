package re.edu.intern_management_prj.model.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import re.edu.intern_management_prj.util.annotations.DateRange;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@DateRange(start = "startDate", end = "endDate")
public class AssessmentRoundRequest {
    @NotNull(message = "ID giai đoạn thực tập không được để trống!")
    private Integer phaseId;

    @NotBlank(message = "Tên đợt đánh giá không được để trống!")
    private String roundName;

    @NotNull(message = "Ngày bắt đầu không được để trống!")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDate;

    @NotNull(message = "Ngày kết thúc không được để trống!")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate endDate;

    private String description;

    @NotEmpty(message = "Danh sách tiêu chí không được để trống!")
    @Valid
    private List<RoundCriteriaRequest> criteria;
}
