package re.edu.intern_management_prj.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import re.edu.intern_management_prj.model.dto.response.ApiResponse;
import re.edu.intern_management_prj.model.dto.response.PageResponse;

public interface IBaseController<CreateReq, UpdateReq, Res, DetailRes> {
    ResponseEntity<ApiResponse<PageResponse<Res>>> getAll(Pageable pageable);

    ResponseEntity<ApiResponse<DetailRes>> create(CreateReq createReq);

    ResponseEntity<ApiResponse<DetailRes>> update(int id, UpdateReq updateReq);

    ResponseEntity<ApiResponse<DetailRes>> getById(int id);

}
