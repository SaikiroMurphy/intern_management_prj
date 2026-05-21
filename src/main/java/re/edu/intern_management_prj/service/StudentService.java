package re.edu.intern_management_prj.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import re.edu.intern_management_prj.exception.InvalidRoleException;
import re.edu.intern_management_prj.model.dto.request.CreateStudentRequest;
import re.edu.intern_management_prj.model.dto.request.UpdateStudentRequest;
import re.edu.intern_management_prj.model.dto.response.PageResponse;
import re.edu.intern_management_prj.model.dto.response.StudentDetailResponse;
import re.edu.intern_management_prj.model.dto.response.StudentResponse;
import re.edu.intern_management_prj.model.entity.Student;
import re.edu.intern_management_prj.model.entity.User;
import re.edu.intern_management_prj.model.mapper.PageMapper;
import re.edu.intern_management_prj.model.mapper.StudentMapper;
import re.edu.intern_management_prj.repository.StudentRepository;
import re.edu.intern_management_prj.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class StudentService implements IBaseService<CreateStudentRequest, UpdateStudentRequest, StudentResponse, StudentDetailResponse>{
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final StudentMapper studentMapper;
    private final PageMapper pageMapper;

    @Override
    public PageResponse<StudentResponse> getAll(Pageable pageable) {
        Page<StudentResponse> studentPage;

        User user = authService.getMyInfo();
        if (user.getRole().name().equals("ADMIN")) {
            studentPage = studentRepository.findByUserIsDeletedFalse(pageable).map(studentMapper::toStudentResponse);
        // } else if (user.getRole().name().equals("MENTOR")) {
        //     studentPage = studentRepository.findAll(pageable)
        //             .filter(student -> student.getMentor() != null && student.getMentor().getId().equals(user.getId()))
        //             .map(studentMapper::toStudentResponse);
        } else {
            throw new IllegalArgumentException("Vai trò không hợp lệ!");
            
        }
        return pageMapper.toPageResponse(studentPage);
    }

    @Override
    public StudentDetailResponse create(CreateStudentRequest req) {

        User user = userRepository.findById(req.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("User không tồn tại!"));
        
        if (user.isDeleted()) {
            throw new EntityNotFoundException("User đã bị xóa!");
        }

        if (!"STUDENT".equals(user.getRole().name())) {
            throw new InvalidRoleException("User không phải STUDENT!");
        }

        Student student = studentMapper.toStudent(req);
        student.setUser(user);

        return studentMapper.toStudentDetailResponse(studentRepository.save(student));
    }

    @Override
    public StudentDetailResponse getById(int id) {
        User user = authService.getMyInfo();
        if(user.getId() != id && !"ADMIN".equals(user.getRole().name())) {
            throw new IllegalArgumentException("Bạn không có quyền truy cập thông tin sinh viên này!");
        }

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy sinh viên với id: " + id));
        
        if (student.getUser().isDeleted()) {
            throw new EntityNotFoundException("Sinh viên đã bị xóa!");
        }

        return studentMapper.toStudentDetailResponse(student);
    }

    @Override
    public StudentDetailResponse update(int id, UpdateStudentRequest req) {
        User user = authService.getMyInfo();
        if(user.getId() != id && !"ADMIN".equals(user.getRole().name())) {
            throw new IllegalArgumentException("Bạn không có quyền truy cập thông tin sinh viên này!");
        }

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy sinh viên với id: " + id));
        
        if (student.getUser().isDeleted()) {
            throw new EntityNotFoundException("Sinh viên đã bị xóa!");
        }

        if (req.getMajor() != null) {
            student.setMajor(req.getMajor());
        }
        if (req.getStudentClass() != null) {
            student.setStudentClass(req.getStudentClass());
        }
        if (req.getDateOfBirth() != null) {
            student.setDateOfBirth(req.getDateOfBirth());
        }
        if (req.getAddress() != null) {
            student.setAddress(req.getAddress());
        }

        return studentMapper.toStudentDetailResponse(studentRepository.save(student));
    }
}
