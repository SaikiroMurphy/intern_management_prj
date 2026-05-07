package re.edu.intern_management_prj.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends BaseEntity {
    @Id
    private Integer studentId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "student_id")
    private User user;

    @Column(nullable = false, unique = true, length = 20)
    private String studentCode;

    @Column(length = 100)
    private String major;

    @Column(length = 50)
    private String studentClass;

    private LocalDate dateOfBirth;

    @Column(length = 255)
    private String address;
}