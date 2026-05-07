package re.edu.intern_management_prj.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "internship_phases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InternshipPhase extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer phaseId;

    @Column(nullable = false, unique = true, length = 100)
    private String phaseName;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column
    private String description;
}
