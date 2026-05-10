package re.edu.intern_management_prj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import re.edu.intern_management_prj.model.entity.Mentor;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Integer> {

}
