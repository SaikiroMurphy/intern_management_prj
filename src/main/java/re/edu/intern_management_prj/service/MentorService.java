package re.edu.intern_management_prj.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import re.edu.intern_management_prj.model.dto.request.CreateMentorRequest;
import re.edu.intern_management_prj.model.dto.request.UpdateMentorRequest;
import re.edu.intern_management_prj.model.dto.response.MentorDetailResponse;
import re.edu.intern_management_prj.model.dto.response.MentorResponse;
import re.edu.intern_management_prj.model.dto.response.PageResponse;
import re.edu.intern_management_prj.model.entity.Mentor;
import re.edu.intern_management_prj.model.entity.User;
import re.edu.intern_management_prj.model.mapper.MentorMapper;
import re.edu.intern_management_prj.model.mapper.PageMapper;
import re.edu.intern_management_prj.repository.MentorRepository;
import re.edu.intern_management_prj.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class MentorService implements IBaseService<CreateMentorRequest, UpdateMentorRequest, MentorResponse, MentorDetailResponse>{
    private final MentorRepository mentorRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final MentorMapper mentorMapper;
    private final PageMapper pageMapper;

    @Override
    public PageResponse<MentorResponse> getAll(Pageable pageable) {
        Page<MentorResponse> mentorPage = mentorRepository.findByUserIsDeletedFalse(pageable).map(mentorMapper::toMentorResponse);
        return pageMapper.toPageResponse(mentorPage);
    }

    @Override
    public MentorDetailResponse getById(int id) {
        User user = authService.getMyInfo();
        if (user.getId() != id && !user.getRole().name().equals("ADMIN") && !user.getRole().name().equals("STUDENT")) {
            throw new IllegalArgumentException("Vai trò không hợp lệ!");
        }

        Mentor mentor = mentorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy mentor với id: " + id));
        
        if (mentor.getUser().isDeleted()) {
            throw new EntityNotFoundException("Mentor đã bị xóa!");
        }

        return mentorMapper.toMentorDetailResponse(mentor);
    }

    @Override
    public MentorDetailResponse create(CreateMentorRequest req) {
        User user = userRepository.findById(req.getMentorId())
                .orElseThrow(() -> new EntityNotFoundException("User không tồn tại!"));

        if (user.isDeleted()) {
            throw new EntityNotFoundException("User đã bị xóa!");
        }

        if (!"MENTOR".equals(user.getRole().name())) {
            throw new IllegalArgumentException("User không có vai trò là MENTOR!");
        }

        Mentor mentor = mentorMapper.toMentor(req);
        mentor.setUser(user);

        return mentorMapper.toMentorDetailResponse(mentorRepository.save(mentor));
    }

    @Override
    public MentorDetailResponse update(int id, UpdateMentorRequest req) {
        User user = authService.getMyInfo();
        if (user.getId() != id && !user.getRole().name().equals("ADMIN")) {
            throw new IllegalArgumentException("Bạn không có quyền cập nhật thông tin mentor này!");
        }

        Mentor mentor = mentorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy mentor với id: " + id));
        
        if (mentor.getUser().isDeleted()) {
            throw new EntityNotFoundException("Mentor đã bị xóa!");
        }

        if (req.getDepartment() != null) {
            mentor.setDepartment(req.getDepartment());
        }

        if (req.getAcademicRank() != null) {
            mentor.setAcademicRank(req.getAcademicRank());
            
        }

        return mentorMapper.toMentorDetailResponse(mentorRepository.save(mentor));
    }
}
