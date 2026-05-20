package re.edu.intern_management_prj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import re.edu.intern_management_prj.model.entity.RoundCriteria;

@Repository
public interface RoundCriteriaRepository extends JpaRepository<RoundCriteria, Integer>{
    List<RoundCriteria> findByRoundRoundId(int roundId);
}
