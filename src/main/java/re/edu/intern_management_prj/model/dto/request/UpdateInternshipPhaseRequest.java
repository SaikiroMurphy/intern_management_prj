package re.edu.intern_management_prj.model.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class UpdateInternshipPhaseRequest {
    private String phaseName;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate endDate;

    private String description;
}
