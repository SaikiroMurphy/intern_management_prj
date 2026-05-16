package re.edu.intern_management_prj.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import re.edu.intern_management_prj.model.dto.request.CreateEvaluationCriteriaRequest;
import re.edu.intern_management_prj.model.dto.request.UpdateEvaluationCriteriaRequest;
import re.edu.intern_management_prj.model.dto.response.ApiResponse;
import re.edu.intern_management_prj.model.dto.response.EvaluationCriteriaDetailResponse;
import re.edu.intern_management_prj.model.dto.response.EvaluationCriteriaResponse;
import re.edu.intern_management_prj.model.dto.response.PageResponse;
import re.edu.intern_management_prj.service.EvaluationCriteriaService;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/evaluation-criteria")
public class EvaluationCriteriaController implements IBaseController<CreateEvaluationCriteriaRequest, UpdateEvaluationCriteriaRequest, EvaluationCriteriaResponse, EvaluationCriteriaDetailResponse>{
    private final EvaluationCriteriaService evaluationCriteriaService;

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<EvaluationCriteriaResponse>>> getAll (
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "criterionId",
                    direction = Sort.Direction.DESC
            ) Pageable pageable)  {
        PageResponse<EvaluationCriteriaResponse> crits = evaluationCriteriaService.getAll(pageable);
        
        return ResponseEntity.ok(ApiResponse.<PageResponse<EvaluationCriteriaResponse>>builder()
                .success(true)
                .message("Lấy danh sách tiêu chí đánh giá thành công!")
                .data(crits)
                .build());

    }
    
    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<EvaluationCriteriaDetailResponse>> create (@Valid @RequestBody CreateEvaluationCriteriaRequest req) {
        EvaluationCriteriaDetailResponse res = evaluationCriteriaService.create(req);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<EvaluationCriteriaDetailResponse>builder()
                        .success(true)
                        .message("Tạo tiêu chí đánh giá mới thành công!")
                        .data(res)
                        .build()
        );
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<EvaluationCriteriaDetailResponse>> update(@PathVariable int id, @RequestBody UpdateEvaluationCriteriaRequest updateReq) {
        EvaluationCriteriaDetailResponse res = evaluationCriteriaService.update(id, updateReq);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<EvaluationCriteriaDetailResponse>builder()
                        .success(true)
                        .message("Cập nhật tiêu chí đánh giá thành công!")
                        .data(res)
                        .build());

    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EvaluationCriteriaDetailResponse>> getById(@PathVariable int id) {
        EvaluationCriteriaDetailResponse res = evaluationCriteriaService.getById(id);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<EvaluationCriteriaDetailResponse>builder()
                        .success(true)
                        .message("Lấy tiêu chí đánh giá thành công!")
                        .data(res)
                        .build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable int id) {
        evaluationCriteriaService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
