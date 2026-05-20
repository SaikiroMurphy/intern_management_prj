package re.edu.intern_management_prj.model.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AssessmentRoundDetailResponse {

    private Integer roundId;

    private String phaseName; // optional (nếu join)

    private String roundName;

    private LocalDate startDate;
    private LocalDate endDate;

    private String description;

    private List<RoundCriteriaResponse> criteria;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime updatedAt;
}
