package re.edu.intern_management_prj.model.entity;

import jakarta.persistence.*;
import lombok.*;
import re.edu.intern_management_prj.util.enums.StatusEnum;

import java.time.LocalDateTime;

@Entity
@Table(name = "internship_assignments", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "student_id", "phase_id" })
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int assignmentId;

    // FK -> Students
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // FK -> Mentors
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id", nullable = false)
    private Mentor mentor;

    // FK -> InternshipPhases
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phase_id", nullable = false)
    private InternshipPhase phase;

    @Column
    private LocalDateTime assignedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEnum status;
}