package re.edu.intern_management_prj.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import re.edu.intern_management_prj.model.dto.request.CreateMentorRequest;
import re.edu.intern_management_prj.model.dto.request.UpdateMentorRequest;
import re.edu.intern_management_prj.model.dto.response.ApiResponse;
import re.edu.intern_management_prj.model.dto.response.MentorDetailResponse;
import re.edu.intern_management_prj.model.dto.response.MentorResponse;
import re.edu.intern_management_prj.model.dto.response.PageResponse;
import re.edu.intern_management_prj.service.MentorService;

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
@RequestMapping("/api/mentors")
public class MentorController {
    private final MentorService mentorService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<MentorResponse>>> getAllMentors(
            @PageableDefault(
                page = 0,
                size = 10,
                sort = "mentorId",
                direction = Sort.Direction.DESC
            ) Pageable pageable) {
        PageResponse<MentorResponse> pageResponse = mentorService.getAllMentors(pageable);
        return ResponseEntity.ok(ApiResponse.<PageResponse<MentorResponse>>builder()
                .success(true)
                .message("Lấy danh sách giáo viên hướng dẫn thành công!")
                .data(pageResponse)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MentorDetailResponse>> getMentorById(@PathVariable Integer id) {
        MentorDetailResponse mentorResponse = mentorService.getMentorById(id);
        return ResponseEntity.ok(ApiResponse.<MentorDetailResponse>builder()
                .success(true)
                .message("Lấy thông tin giáo viên hướng dẫn thành công!")
                .data(mentorResponse)
                .build());
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<MentorDetailResponse>> createMentor(@RequestBody CreateMentorRequest req) {
        MentorDetailResponse mentorResponse = mentorService.createMentor(req);
        return ResponseEntity.ok(ApiResponse.<MentorDetailResponse>builder()
                .success(true)
                .message("Tạo giáo viên hướng dẫn thành công!")
                .data(mentorResponse)
                .build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MentorDetailResponse>> updateMentor(@PathVariable Integer id, @RequestBody UpdateMentorRequest req) {
        MentorDetailResponse mentorResponse = mentorService.updateMentor(id, req);
        return ResponseEntity.ok(ApiResponse.<MentorDetailResponse>builder()
                .success(true)
                .message("Cập nhật thông tin giáo viên hướng dẫn thành công!")
                .data(mentorResponse)
                .build());
    }
}
