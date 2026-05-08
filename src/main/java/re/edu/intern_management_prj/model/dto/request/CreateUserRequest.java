package re.edu.intern_management_prj.model.dto.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import re.edu.intern_management_prj.util.annotations.PhoneConstraint;
import re.edu.intern_management_prj.util.annotations.RoleConstraint;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateUserRequest {
    @NotBlank(message = "Tên đăng nhập không được để trống!")
    @Length(min = 3, max = 50, message = "Tên đăng nhập phải có độ dài từ 3 đến 50 ký tự!")
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống!")
    @Length(min = 6, max = 100, message = "Mật khẩu phải có độ dài từ 6 đến 100 ký tự!")
    private String password;

    @NotBlank(message = "Họ tên không được để trống!")
    private String fullName;

    @Email(message = "Email không hợp lệ!")
    @NotBlank(message = "Email không được để trống!")
    private String email;

    @PhoneConstraint(message = "Số điện thoại không hợp lệ!")
    @NotBlank(message = "Số điện thoại không được để trống!")
    private String phoneNumber;

    @NotBlank(message = "Vai trò không được để trống!")
    @RoleConstraint(message = "Vai trò không hợp lệ! Vai trò phải là ADMIN, MENTOR hoặc STUDENT!")
    private String role;
}
