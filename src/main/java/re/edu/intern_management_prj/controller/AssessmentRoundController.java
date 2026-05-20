package re.edu.intern_management_prj.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import re.edu.intern_management_prj.model.dto.request.AssessmentRoundRequest;
import re.edu.intern_management_prj.model.dto.response.ApiResponse;
import re.edu.intern_management_prj.model.dto.response.AssessmentRoundDetailResponse;
import re.edu.intern_management_prj.model.dto.response.AssessmentRoundResponse;
import re.edu.intern_management_prj.model.dto.response.PageResponse;
import re.edu.intern_management_prj.service.AssessmentRoundService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assessment-rounds")
public class AssessmentRoundController implements IBaseController<AssessmentRoundRequest, AssessmentRoundRequest, AssessmentRoundResponse, AssessmentRoundDetailResponse>{
    private final AssessmentRoundService assessmentRoundService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<AssessmentRoundResponse>>> getAll(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "roundId",
                    direction = Sort.Direction.DESC
            )Pageable pageable) {
        PageResponse<AssessmentRoundResponse> res = assessmentRoundService.getAll(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<PageResponse<AssessmentRoundResponse>>builder()
                        .success(true)
                        .message("Lấy danh sách các đợt đánh giá thành công!")
                        .data(res)
                        .build());

    }

    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<AssessmentRoundDetailResponse>> create(
            @Valid @RequestBody AssessmentRoundRequest createReq) {
        AssessmentRoundDetailResponse res = assessmentRoundService.create(createReq);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<AssessmentRoundDetailResponse>builder()
                        .success(true)
                        .message("Tạo đợt đánh giá mới thành công!")
                        .data(res)
                        .build());

    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<AssessmentRoundDetailResponse>> update(@PathVariable int id, @Valid @RequestBody AssessmentRoundRequest updateReq) {
        AssessmentRoundDetailResponse res = assessmentRoundService.update(id, updateReq);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<AssessmentRoundDetailResponse>builder()
                        .success(true)
                        .message("Cập nhật đợt đánh giá thành công!")
                        .data(res)
                        .build());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AssessmentRoundDetailResponse>> getById(@PathVariable int id) {
        AssessmentRoundDetailResponse res = assessmentRoundService.getById(id);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<AssessmentRoundDetailResponse>builder()
                        .success(true)
                        .message("Lấy thông tin đợt đánh giá thành công!")
                        .data(res)
                        .build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable int id) {
        assessmentRoundService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
