package re.edu.intern_management_prj.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;
import re.edu.intern_management_prj.model.dto.request.CreateInternshipPhaseRequest;
import re.edu.intern_management_prj.model.dto.request.UpdateInternshipPhaseRequest;
import re.edu.intern_management_prj.model.dto.response.InternshipPhaseResponse;
import re.edu.intern_management_prj.model.dto.response.InternshipPhaseDetailResponse;
import re.edu.intern_management_prj.model.dto.response.PageResponse;
import re.edu.intern_management_prj.model.entity.InternshipPhase;
import re.edu.intern_management_prj.model.mapper.InternshipPhaseMapper;
import re.edu.intern_management_prj.model.mapper.PageMapper;
import re.edu.intern_management_prj.repository.InternshipPhaseRepository;

@Service
@RequiredArgsConstructor
public class InternshipPhaseService {
    private final InternshipPhaseRepository internshipPhaseRepository;
    private final PageMapper pageMapper;
    private final InternshipPhaseMapper internshipPhaseMapper;

    public PageResponse<InternshipPhaseResponse> getAllPhases(Pageable pageable) {
                Page<InternshipPhaseResponse> internshipPhasePage = internshipPhaseRepository.findAll(pageable).map(internshipPhase -> {
            if (internshipPhase.isDeleted()) {
                return null;
            }
            return internshipPhaseMapper.toInternshipPhaseResponse(internshipPhase);
        });
        return pageMapper.toPageResponse(internshipPhasePage);

    }

    public InternshipPhaseDetailResponse createInternshipPhase(CreateInternshipPhaseRequest req) {
        InternshipPhase internshipPhase = internshipPhaseMapper.toInternshipPhase(req);

        return internshipPhaseMapper.toInternshipPhaseDetailResponse(internshipPhaseRepository.save(internshipPhase));
    }

    public InternshipPhaseDetailResponse updateInternshipPhase(UpdateInternshipPhaseRequest req, int id) {
        InternshipPhase phase = internshipPhaseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy giai đoạn thực tập với id:" + id));

        if (phase.isDeleted()) {
            throw new EntityNotFoundException("Giai đoạn thực tập này đã bị xóa!");
        }

        if (req.getPhaseName() != null) {
            if (internshipPhaseRepository.existsByPhaseNameAndPhaseIdNot(req.getPhaseName(), id)) {
                throw new IllegalArgumentException("Tên giai đoạn thực tập đã tồn tại!");
            }
            phase.setPhaseName(req.getPhaseName());
        }

        if (req.getStartDate() != null) {
            phase.setStartDate(req.getStartDate());
        }

        if (req.getEndDate() != null) {
           phase.setEndDate(req.getEndDate()); 
        }

        if (req.getDescription() != null) {
            phase.setDescription(req.getDescription());
        }

        LocalDate newStart = req.getStartDate() != null ? req.getStartDate() : phase.getStartDate();
        LocalDate newEnd = req.getEndDate() != null ? req.getEndDate() : phase.getEndDate();
        if (newStart != null && newEnd != null && newStart.isAfter(newEnd)) {
            throw new IllegalArgumentException("startDate không được sau endDate!");
        }

        internshipPhaseRepository.save(phase);
        return internshipPhaseMapper.toInternshipPhaseDetailResponse(phase);
    }

    public InternshipPhaseDetailResponse getPhaseById(int id) {
        InternshipPhase phase = internshipPhaseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy giai đoạn thực tập với id: " + id));

        if (phase.isDeleted()) {
            throw new EntityNotFoundException("Giai đonaj thực tập này đã bị xóa!");
        }

        return internshipPhaseMapper.toInternshipPhaseDetailResponse(phase);
    }

    public void deletePhase(int id) {
        InternshipPhase phase = internshipPhaseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy giai đoạn thực tập với id: " + id));

        if (phase.isDeleted()) {
            throw new EntityNotFoundException("Giai đoạn thực tập này đã bị xóa!");
        }

        phase.setDeleted(true);
        internshipPhaseRepository.save(phase);
    }
}
