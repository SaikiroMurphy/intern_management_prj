package re.edu.intern_management_prj.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import re.edu.intern_management_prj.exception.DuplicateResourceException;
import re.edu.intern_management_prj.model.dto.request.AssessmentRoundRequest;
import re.edu.intern_management_prj.model.dto.request.RoundCriteriaRequest;
import re.edu.intern_management_prj.model.dto.response.AssessmentRoundDetailResponse;
import re.edu.intern_management_prj.model.dto.response.AssessmentRoundResponse;
import re.edu.intern_management_prj.model.dto.response.PageResponse;
import re.edu.intern_management_prj.model.entity.AssessmentRound;
import re.edu.intern_management_prj.model.entity.EvaluationCriteria;
import re.edu.intern_management_prj.model.entity.InternshipPhase;
import re.edu.intern_management_prj.model.entity.RoundCriteria;
import re.edu.intern_management_prj.model.mapper.AssessmentRoundMapper;
import re.edu.intern_management_prj.model.mapper.PageMapper;
import re.edu.intern_management_prj.model.mapper.RoundCriteriaMapper;
import re.edu.intern_management_prj.repository.AssessmentRoundRepository;
import re.edu.intern_management_prj.repository.EvaluationCriteriaRepository;
import re.edu.intern_management_prj.repository.InternshipPhaseRepository;
import re.edu.intern_management_prj.repository.RoundCriteriaRepository;

@Service
@RequiredArgsConstructor
public class AssessmentRoundService implements IBaseService<AssessmentRoundRequest, AssessmentRoundRequest, AssessmentRoundResponse, AssessmentRoundDetailResponse> {
    private final AssessmentRoundRepository assessmentRoundRepository;
    private final RoundCriteriaRepository roundCriteriaRepository;
    private final AssessmentRoundMapper assessmentRoundMapper;
    private final RoundCriteriaMapper roundCriteriaMapper;
    private final InternshipPhaseRepository phaseRepository;
    private final EvaluationCriteriaRepository evaluationCriteriaRepository;
    private final PageMapper pageMapper;

    @Override
    public PageResponse<AssessmentRoundResponse> getAll(Pageable pageable) {

        Page<AssessmentRound> page = assessmentRoundRepository.findAll(pageable);

        Page<AssessmentRoundResponse> res = page.map(round -> {
            if (round.isDeleted()) {
                return null;
            }
            return assessmentRoundMapper.toRoundResponse(round);
        });

        return pageMapper.toPageResponse(res);
    }

    @Override
    @Transactional
    public AssessmentRoundDetailResponse create(AssessmentRoundRequest createReq) {
        InternshipPhase phase = phaseRepository.findById(createReq.getPhaseId())
            .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy giai đoạn thực tập phù hợp!"));
        if (phase.isDeleted()) {
            throw new EntityNotFoundException("Giai đoạn thực tập đã bị xóa!");
        }

        if (createReq.getStartDate().isBefore(phase.getStartDate())
                || createReq.getEndDate().isAfter(phase.getEndDate())) {

            throw new IllegalArgumentException(
                    "Thời gian đợt đánh giá phải nằm trong giai đoạn thực tập.");
        }

        Set<Integer> criterionIds = new HashSet<>();

        for (RoundCriteriaRequest criterionReq : createReq.getCriteria()) {

            Integer criterionId = criterionReq.getCriterionId();

            if (!criterionIds.add(criterionId)) {
                throw new DuplicateResourceException(
                        "Tiêu chí ID " + criterionId + " bị trùng."
                );
            }
        }

        AssessmentRound round = assessmentRoundMapper.toAssessmentRound(createReq);

        round.setPhase(phase);

        AssessmentRound savedRound = assessmentRoundRepository.save(round);

        List<RoundCriteria> criteriaList = createReq.getCriteria()
                .stream()
                .map(req -> {
                    RoundCriteria rc = roundCriteriaMapper.toRoundCriteria(req);

                    rc.setRound(savedRound);

                    EvaluationCriteria criterion = evaluationCriteriaRepository.findById(req.getCriterionId())
                            .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy tiêu chí với id: " + req.getCriterionId()));

                    rc.setCriterion(criterion);

                    return rc;
                })
                .toList();
        roundCriteriaRepository.saveAll(criteriaList);

        AssessmentRoundDetailResponse detailRes = assessmentRoundMapper.toRoundDetailResponse(savedRound);
        detailRes.setCriteria(criteriaList.stream().map(roundCriteriaMapper::toResponse).toList());
        return detailRes;
    }

    @Override
    public AssessmentRoundDetailResponse update(int id, AssessmentRoundRequest updateReq) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public AssessmentRoundDetailResponse getById(int id) {
        AssessmentRound round = assessmentRoundRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không có đợt đánh giá với id: " + id));

        if (round.isDeleted()) {
            throw new EntityNotFoundException("Đợt đánh giá đã bị xóa!");
        }

        return assessmentRoundMapper.toRoundDetailResponse(round);
    }

    public void delete(int id) {
        AssessmentRound round = assessmentRoundRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không có đợt đánh giá với id: " + id));

        if (round.isDeleted()) {
            throw new EntityNotFoundException("Đợt đánh giá đã bị xóa!");
        }

        round.setDeleted(true);
        assessmentRoundRepository.save(round);
    }
}
