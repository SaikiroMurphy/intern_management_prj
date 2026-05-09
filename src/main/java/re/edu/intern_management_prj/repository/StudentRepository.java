package re.edu.intern_management_prj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import re.edu.intern_management_prj.model.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    boolean existsByStudentId(Integer studentId);

    boolean existsByStudentCode(String studentCode);
}
