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
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<StudentResponse>>> getAllStudents(
            @PageableDefault(
                page = 0,
                size = 10,
                sort = "studentId",
                direction = Sort.Direction.DESC
            ) Pageable pageable) {
        PageResponse<StudentResponse> pageResponse = studentService.getAllStudents(pageable);

        return ResponseEntity.ok(ApiResponse.<PageResponse<StudentResponse>>builder()
                .success(true)
                .message("Lấy danh sách sinh viên thành công!")
                .data(pageResponse)
                .build());
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<StudentDetailResponse>> createStudent(@Valid @RequestBody CreateStudentRequest req) {
        StudentDetailResponse studentResponse = studentService.createStudent(req);

        return ResponseEntity.ok(ApiResponse.<StudentDetailResponse>builder()
                .success(true)
                .message("Tạo sinh viên thành công!")
                .data(studentResponse)
                .build());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDetailResponse>> getStudentById(@PathVariable int id) {
        StudentDetailResponse studentResponse = studentService.getStudentById(id);

        return ResponseEntity.ok(ApiResponse.<StudentDetailResponse>builder()
                .success(true)
                .message("Lấy thông tin sinh viên thành công!")
                .data(studentResponse)
                .build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDetailResponse>> updateStudent(@PathVariable int id, @RequestBody UpdateStudentRequest request) {
        StudentDetailResponse studentResponse = studentService.updateStudent(id, request);

        return ResponseEntity.ok(ApiResponse.<StudentDetailResponse>builder()
                .success(true)
                .message("Cập nhật thông tin sinh viên thành công!")
                .data(studentResponse)
                .build());
    }
}
