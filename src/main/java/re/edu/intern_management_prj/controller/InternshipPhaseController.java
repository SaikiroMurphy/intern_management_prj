package re.edu.intern_management_prj.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import re.edu.intern_management_prj.model.dto.request.CreateInternshipPhaseRequest;
import re.edu.intern_management_prj.model.dto.request.UpdateInternshipPhaseRequest;
import re.edu.intern_management_prj.model.dto.response.ApiResponse;
import re.edu.intern_management_prj.model.dto.response.InternshipPhaseDetailResponse;
import re.edu.intern_management_prj.model.dto.response.InternshipPhaseResponse;
import re.edu.intern_management_prj.model.dto.response.PageResponse;
import re.edu.intern_management_prj.service.InternshipPhaseService;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/internship-phases")
public class InternshipPhaseController implements IBaseController<CreateInternshipPhaseRequest, UpdateInternshipPhaseRequest, InternshipPhaseResponse, InternshipPhaseDetailResponse>{
    private final InternshipPhaseService internshipPhaseService;

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<InternshipPhaseResponse>>> getAll(
            @PageableDefault(
                page = 0,
                size = 10,
                sort = "phaseId",
                direction = Sort.Direction.DESC
            ) Pageable pageable) {
        
        PageResponse<InternshipPhaseResponse> pageResponse = internshipPhaseService.getAll(pageable);
        return ResponseEntity.ok(ApiResponse.<PageResponse<InternshipPhaseResponse>>builder()
                .success(true)
                .message("Lấy danh sách giai đoạn thực tập thành công!")
                .data(pageResponse)
                .build());
    }
    
    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<InternshipPhaseDetailResponse>> create(@Valid @RequestBody CreateInternshipPhaseRequest req) {
        InternshipPhaseDetailResponse res = internshipPhaseService.create(req);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<InternshipPhaseDetailResponse>builder()
                .success(true)
                .message("Tạo giai đoạn thưc tập mới thành công!")
                .data(res)
                .build()
        );
    }
    
    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<InternshipPhaseDetailResponse>> update(@PathVariable int id, @RequestBody UpdateInternshipPhaseRequest req) {
        InternshipPhaseDetailResponse res = internshipPhaseService.update(id, req);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<InternshipPhaseDetailResponse>builder()
                .success(true)
                .message("Cập nhật giai đoạn thưc tập thành công!")
                .data(res)
                .build());

    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InternshipPhaseDetailResponse>> getById(@PathVariable int id) {
        InternshipPhaseDetailResponse res = internshipPhaseService.getById(id);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<InternshipPhaseDetailResponse>builder()
                .success(true)
                .message("Lấy thông tin giai đoạn thưc tập thành công!")
                .data(res)
                .build());

    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable int id) {
        internshipPhaseService.deletePhase(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
