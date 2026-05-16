package re.edu.intern_management_prj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import re.edu.intern_management_prj.model.entity.EvaluationCriteria;

@Repository
public interface EvaluationCriteriaRepository extends JpaRepository<EvaluationCriteria, Integer>{
    boolean existsByCriterionName(String criterionName);

    boolean existsByCriterionNameAndCriterionIdNot(String criterionName, Integer criterionId);
}
