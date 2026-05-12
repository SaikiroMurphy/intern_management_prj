package re.edu.intern_management_prj.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import lombok.RequiredArgsConstructor;
import re.edu.intern_management_prj.model.dto.request.CreateUserRequest;
import re.edu.intern_management_prj.model.dto.request.UpdateUserRequest;
import re.edu.intern_management_prj.model.dto.response.ApiResponse;
import re.edu.intern_management_prj.model.dto.response.PageResponse;
import re.edu.intern_management_prj.model.dto.response.UserDetailResponse;
import re.edu.intern_management_prj.model.dto.response.UserResponse;
import re.edu.intern_management_prj.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> getAllUsers(
            @PageableDefault(
                page = 0,
                size = 10,
                sort = "id",
                direction = Sort.Direction.DESC
            ) Pageable pageable,
            @RequestParam(required = false) String role) {

        PageResponse<UserResponse> pageResponse = userService.getAllUsers(pageable, role);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<PageResponse<UserResponse>>builder()
                .success(true)
                .message("Lấy danh sách người dùng thành công!")
                .data(pageResponse)
                .build());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDetailResponse>> getUserById(@PathVariable Integer id) {
        UserDetailResponse userDetailResponse = userService.getUserById(id);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<UserDetailResponse>builder()
                .success(true)
                .message("Lấy thông tin người dùng thành công!")
                .data(userDetailResponse)
                .build());
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<UserDetailResponse>> addUser(@Valid @RequestBody CreateUserRequest request) {
        UserDetailResponse userDetailResponse = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<UserDetailResponse>builder()
                .success(true)
                .message("Thêm người dùng thành công!")
                .data(userDetailResponse)
                .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<UserDetailResponse>> updateUser(@PathVariable int id, @RequestBody UpdateUserRequest request) {
        UserDetailResponse userDetailResponse = userService.updateUser(id, request);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<UserDetailResponse>builder()
                .success(true)
                .message("Cập nhật thông tin người dùng thành công!")
                .data(userDetailResponse)
                .build());
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<UserDetailResponse>> updateUserStatus(@PathVariable int id, @RequestBody boolean active) {
        UserDetailResponse userDetailResponse = userService.updateStatus(id, active);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<UserDetailResponse>builder()
                .success(true)
                .message("Cập nhật trạng thái người dùng thành công!")
                .data(userDetailResponse)
                .build());
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<ApiResponse<UserDetailResponse>> updateUserRole(@PathVariable int id, @RequestBody String role) {
        UserDetailResponse userDetailResponse = userService.updateRole(id, role);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<UserDetailResponse>builder()
                .success(true)
                .message("Cập nhật vai trò người dùng thành công!")
                .data(userDetailResponse)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.<String>builder()
                .success(true)
                .message("Xóa người dùng thành công!")
                .data("Người dùng có id " + id + " đã bị xóa.")
                .build());
    }
}
