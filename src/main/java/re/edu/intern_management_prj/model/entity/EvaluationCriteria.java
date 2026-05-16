package re.edu.intern_management_prj.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "evaluation_criteria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationCriteria extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer criterionId;

    @Column(nullable = false, unique = true, length = 200)
    private String criterionName;

    @Column
    private String description;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal maxScore;

    @Column
    private boolean isDeleted = false;
}