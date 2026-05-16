package re.edu.intern_management_prj.service;

import org.springframework.data.domain.Pageable;

import re.edu.intern_management_prj.model.dto.response.PageResponse;

public interface IBaseService <CreateReq, UpdateReq, Res, DetailRes>{
    PageResponse<Res> getAll(Pageable pageable);

    DetailRes create(CreateReq createReq);

    DetailRes update(int id, UpdateReq updateReq);

    DetailRes getById(int id);
}
