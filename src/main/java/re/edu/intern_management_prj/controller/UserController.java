package re.edu.intern_management_prj.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import lombok.RequiredArgsConstructor;
import re.edu.intern_management_prj.model.dto.request.CreateUserRequest;
import re.edu.intern_management_prj.model.dto.response.ApiResponse;
import re.edu.intern_management_prj.model.dto.response.PageResponse;
import re.edu.intern_management_prj.model.dto.response.UserDetailResponse;
import re.edu.intern_management_prj.model.dto.response.UserResponse;
import re.edu.intern_management_prj.model.mapper.PageMapper;
import re.edu.intern_management_prj.model.mapper.UserMapper;
import re.edu.intern_management_prj.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PageMapper pageMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> getAllUsers(
            @PageableDefault(
                page = 0,
                size = 10,
                sort = "id",
                direction = Sort.Direction.DESC
            ) Pageable pageable,
            @RequestParam(required = false) String role) {

        Page<UserResponse> userPage = userService.getAllUsers(pageable, role).map(userMapper::toUserResponse);

        PageResponse<UserResponse> pageResponse = pageMapper.toPageResponse(userPage);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<PageResponse<UserResponse>>builder()
                .success(true)
                .message("Lấy danh sách người dùng thành công!")
                .data(pageResponse)
                .build());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDetailResponse>> getUserById(@PathVariable Integer id) {
        UserDetailResponse userDetailResponse = userMapper.toUserDetailResponse(userService.getUserById(id));

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<UserDetailResponse>builder()
                .success(true)
                .message("Lấy thông tin người dùng thành công!")
                .data(userDetailResponse)
                .build());
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<UserDetailResponse>> addUser(@Valid @ModelAttribute CreateUserRequest request) {
        UserDetailResponse userDetailResponse = userMapper.toUserDetailResponse(userService.createUser(userMapper.toUser(request)));

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<UserDetailResponse>builder()
                .success(true)
                .message("Thêm người dùng thành công!")
                .data(userDetailResponse)
                .build());
    }
    
}
