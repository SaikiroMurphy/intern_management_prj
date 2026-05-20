package re.edu.intern_management_prj.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import re.edu.intern_management_prj.model.entity.RoundCriteria;

@Repository
public interface RoundCriteriaRepository extends JpaRepository<RoundCriteria, Integer>{

    @Query("""
        SELECT COUNT(rc)
        FROM RoundCriteria rc
        WHERE rc.round.roundId = :roundId
    """)
    Integer countByRoundId(@Param("roundId") Integer roundId);

    @Query("""
        SELECT COALESCE(SUM(rc.weight), 0)
        FROM RoundCriteria rc
        WHERE rc.round.roundId = :roundId
    """)
    BigDecimal sumWeightByRoundId(@Param("roundId") Integer roundId);
}
