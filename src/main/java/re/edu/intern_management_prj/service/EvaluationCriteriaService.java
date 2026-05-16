package re.edu.intern_management_prj.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import re.edu.intern_management_prj.model.dto.request.CreateEvaluationCriteriaRequest;
import re.edu.intern_management_prj.model.dto.request.UpdateEvaluationCriteriaRequest;
import re.edu.intern_management_prj.model.dto.response.EvaluationCriteriaDetailResponse;
import re.edu.intern_management_prj.model.dto.response.EvaluationCriteriaResponse;
import re.edu.intern_management_prj.model.dto.response.PageResponse;
import re.edu.intern_management_prj.model.entity.EvaluationCriteria;
import re.edu.intern_management_prj.model.mapper.EvaluationCriteriaMapper;
import re.edu.intern_management_prj.model.mapper.PageMapper;
import re.edu.intern_management_prj.repository.EvaluationCriteriaRepository;

@Service
@RequiredArgsConstructor
public class EvaluationCriteriaService implements IBaseService<CreateEvaluationCriteriaRequest, UpdateEvaluationCriteriaRequest, EvaluationCriteriaResponse, EvaluationCriteriaDetailResponse>{
    private final EvaluationCriteriaRepository evaluationCriteriaRepository;
    private final EvaluationCriteriaMapper evaluationCriteriaMapper;
    private final PageMapper pageMapper;

    @Override
    public PageResponse<EvaluationCriteriaResponse> getAll(Pageable pageable) {
        Page<EvaluationCriteriaResponse> criterias = evaluationCriteriaRepository
                .findAll(pageable).map(
                        crit -> {
                                if (crit.isDeleted()) {
                                    return null;
                                }
                        return evaluationCriteriaMapper.toEvaluationCriteriaResponse(crit);
                        }
                );
        
        return pageMapper.toPageResponse(criterias);
    }
    
    @Override
    public EvaluationCriteriaDetailResponse create(CreateEvaluationCriteriaRequest req) {
        EvaluationCriteria crit = evaluationCriteriaMapper.toEvaluationCriteria(req);

        return evaluationCriteriaMapper.toEvaluationCriteriaDetailResponse(evaluationCriteriaRepository.save(crit));
    }

    @Override
    public EvaluationCriteriaDetailResponse update(int id, UpdateEvaluationCriteriaRequest req) {
        EvaluationCriteria crit = evaluationCriteriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy tiêu chí đánh giá với id " + id));

        if (crit.isDeleted()) {
            throw new EntityNotFoundException("Tiêu chí đánh giá này đã bị xóa!");
        }

        if (req.getCriterionName() != null) {
            if (evaluationCriteriaRepository.existsByCriterionNameAndCriterionIdNot(req.getCriterionName(), id)) {
                throw new IllegalArgumentException("Tên tiêu chí đánh giá đã tồn tại!");
            }
            crit.setCriterionName(req.getCriterionName());
        }

        if (req.getDescription() != null) {
            crit.setDescription(req.getDescription());
        }

        if (req.getMaxScore() != null) {
            crit.setMaxScore(req.getMaxScore());
        }

        evaluationCriteriaRepository.save(crit);
        return evaluationCriteriaMapper.toEvaluationCriteriaDetailResponse(crit);
    }

    public void delete(int id) {
        EvaluationCriteria crit = evaluationCriteriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy tiêu chí đánh giá với id " + id));

        if (crit.isDeleted()) {
            throw new EntityNotFoundException("Tiêu chí đánh giá này đã bị xóa!");
        }
        
        crit.setDeleted(true);
        evaluationCriteriaRepository.save(crit);
    }

    @Override
    public EvaluationCriteriaDetailResponse getById(int id) {
        EvaluationCriteria crit = evaluationCriteriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy tiêu chí đánh giá với id " + id));

        if (crit.isDeleted()) {
            throw new EntityNotFoundException("Tiêu chí đánh giá này đã bị xóa!");
        }

        return evaluationCriteriaMapper.toEvaluationCriteriaDetailResponse(crit);
    }

    
}
