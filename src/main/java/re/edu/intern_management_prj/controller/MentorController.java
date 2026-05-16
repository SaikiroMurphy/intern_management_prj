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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mentors")
public class MentorController implements IBaseController<CreateMentorRequest, UpdateMentorRequest, MentorResponse, MentorDetailResponse>{
    private final MentorService mentorService;

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<MentorResponse>>> getAll(
            @PageableDefault(
                page = 0,
                size = 10,
                sort = "mentorId",
                direction = Sort.Direction.DESC
            ) Pageable pageable) {
        PageResponse<MentorResponse> pageResponse = mentorService.getAll(pageable);
        return ResponseEntity.ok(ApiResponse.<PageResponse<MentorResponse>>builder()
                .success(true)
                .message("Lấy danh sách giáo viên hướng dẫn thành công!")
                .data(pageResponse)
                .build());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MentorDetailResponse>> getById(@PathVariable int id) {
        MentorDetailResponse mentorResponse = mentorService.getById(id);
        return ResponseEntity.ok(ApiResponse.<MentorDetailResponse>builder()
                .success(true)
                .message("Lấy thông tin giáo viên hướng dẫn thành công!")
                .data(mentorResponse)
                .build());
    }
    
    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<MentorDetailResponse>> create(@RequestBody CreateMentorRequest req) {
        MentorDetailResponse mentorResponse = mentorService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<MentorDetailResponse>builder()
                .success(true)
                .message("Tạo giáo viên hướng dẫn thành công!")
                .data(mentorResponse)
                .build());
    }
    
    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<MentorDetailResponse>> update(@PathVariable int id, @RequestBody UpdateMentorRequest req) {
        MentorDetailResponse mentorResponse = mentorService.update(id, req);
        return ResponseEntity.ok(ApiResponse.<MentorDetailResponse>builder()
                .success(true)
                .message("Cập nhật thông tin giáo viên hướng dẫn thành công!")
                .data(mentorResponse)
                .build());
    }
}
