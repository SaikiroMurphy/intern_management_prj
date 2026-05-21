package re.edu.intern_management_prj.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import re.edu.intern_management_prj.model.entity.InternshipPhase;

@Repository
public interface InternshipPhaseRepository extends JpaRepository<InternshipPhase, Integer> {
    boolean existsByPhaseNameAndPhaseIdNot(String phaseName, Integer phaseId);

    boolean existsByPhaseName(String phaseName);

    Page<InternshipPhase> findByIsDeletedFalse(Pageable pageable);
}
