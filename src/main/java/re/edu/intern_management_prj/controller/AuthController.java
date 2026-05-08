package re.edu.intern_management_prj.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import re.edu.intern_management_prj.model.dto.request.LoginRequest;
import re.edu.intern_management_prj.model.dto.response.ApiResponse;
import re.edu.intern_management_prj.model.dto.response.UserResponse;
import re.edu.intern_management_prj.model.entity.User;
import re.edu.intern_management_prj.model.mapper.UserMapper;
import re.edu.intern_management_prj.service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody LoginRequest request) {
        String token = authService.login(request.getLoginInfo());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<String>builder()
                .success(true)
                .message("Đăng nhập thành công!")
                .data(token)
                .build());
    }
    
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMyInfo() {
        User user = authService.getMyInfo();
        UserResponse userInfo = userMapper.toUserResponse(user);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<UserResponse>builder()
                .success(true)
                .message("Lấy thông tin thành công!")
                .data(userInfo)
                .build());
    }
    
}
