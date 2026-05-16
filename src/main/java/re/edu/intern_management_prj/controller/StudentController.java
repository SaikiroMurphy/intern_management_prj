package re.edu.intern_management_prj.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import re.edu.intern_management_prj.model.dto.request.CreateStudentRequest;
import re.edu.intern_management_prj.model.dto.request.UpdateStudentRequest;
import re.edu.intern_management_prj.model.dto.response.ApiResponse;
import re.edu.intern_management_prj.model.dto.response.PageResponse;
import re.edu.intern_management_prj.model.dto.response.StudentDetailResponse;
import re.edu.intern_management_prj.model.dto.response.StudentResponse;
import re.edu.intern_management_prj.service.StudentService;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentController implements IBaseController<CreateStudentRequest, UpdateStudentRequest, StudentResponse, StudentDetailResponse>{
    private final StudentService studentService;

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<StudentResponse>>> getAll(
            @PageableDefault(
                page = 0,
                size = 10,
                sort = "studentId",
                direction = Sort.Direction.DESC
            ) Pageable pageable) {
        PageResponse<StudentResponse> pageResponse = studentService.getAll(pageable);

        return ResponseEntity.ok(ApiResponse.<PageResponse<StudentResponse>>builder()
                .success(true)
                .message("Lấy danh sách sinh viên thành công!")
                .data(pageResponse)
                .build());
    }
    
    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<StudentDetailResponse>> create(@Valid @RequestBody CreateStudentRequest req) {
        StudentDetailResponse studentResponse = studentService.create(req);

        return ResponseEntity.ok(ApiResponse.<StudentDetailResponse>builder()
                .success(true)
                .message("Tạo sinh viên thành công!")
                .data(studentResponse)
                .build());
    }
    
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDetailResponse>> getById(@PathVariable int id) {
        StudentDetailResponse studentResponse = studentService.getById(id);

        return ResponseEntity.ok(ApiResponse.<StudentDetailResponse>builder()
                .success(true)
                .message("Lấy thông tin sinh viên thành công!")
                .data(studentResponse)
                .build());
    }
    
    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDetailResponse>> update(@PathVariable int id, @RequestBody UpdateStudentRequest request) {
        StudentDetailResponse studentResponse = studentService.update(id, request);

        return ResponseEntity.ok(ApiResponse.<StudentDetailResponse>builder()
                .success(true)
                .message("Cập nhật thông tin sinh viên thành công!")
                .data(studentResponse)
                .build());
    }
}
