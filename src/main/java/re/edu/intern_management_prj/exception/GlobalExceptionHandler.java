package re.edu.intern_management_prj.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.core.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import re.edu.intern_management_prj.model.dto.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED) .body(ApiResponse.<String>builder()
                .success(false)
                .message("Tên đăng nhập hoặc mật khẩu không đúng.")
                .data(e.getMessage())
                .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.<String>builder()
                .success(false)
                .message("Thông tin không hợp lệ.")
                .data(e.getMessage())
                .build());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.<String>builder()
                .success(false)
                .message("Không tìm thấy thực thể.")
                .data(e.getMessage())
                .build());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleBindException(BindException e) {
        Map<String, String> errors = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        error -> error.getField(),
                        error -> error.getDefaultMessage(),
                        (existingMessage, newMessage) -> existingMessage + "; " + newMessage
                ));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.<Map<String, String>>builder()
                .success(false)
                .message("Thông tin không hợp lệ.")
                .data(errors)
                .build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<String>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.<String>builder()
                .success(false)
                .message("Thông tin không hợp lệ.")
                .data(e.getLocalizedMessage())
                .build());
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<ApiResponse<String>> handlePropertyReferenceException(PropertyReferenceException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.<String>builder()
                .success(false)
                .message("Thông tin không hợp lệ.")
                .data(e.getMessage())
                .build());
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidRoleException(InvalidRoleException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.<String>builder()
                .success(false)
                .message("Vai trò không hợp lệ.")
                .data(e.getMessage())
                .build());
    }

        @ExceptionHandler(DuplicateResourceException.class)
        public ResponseEntity<ApiResponse<String>> handleDuplicateException(DuplicateResourceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.<String>builder()
                    .success(false)
                    .message("Dữ liệu đã tồn tại.")
                    .data(e.getMessage())
                    .build());
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValidException(
                        MethodArgumentNotValidException e) {

                Map<String, String> errors = new HashMap<>();

                e.getBindingResult().getFieldErrors().forEach(error -> {
                        errors.merge(
                                        error.getField(),
                                        error.getDefaultMessage(),
                                        (oldMsg, newMsg) -> oldMsg + "; " + newMsg);
                });

                return ResponseEntity.badRequest().body(
                                ApiResponse.<Map<String, String>>builder()
                                                .success(false)
                                                .message("Dữ liệu không hợp lệ.")
                                                .data(errors)
                                                .build());
        }
}
