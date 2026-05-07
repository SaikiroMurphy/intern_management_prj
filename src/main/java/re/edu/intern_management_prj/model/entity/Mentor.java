package re.edu.intern_management_prj.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mentors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mentor extends BaseEntity {

    @Id
    private Integer mentorId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "mentor_id")
    private User user;

    @Column(length = 100)
    private String department;

    @Column(length = 50)
    private String academicRank;
}
