package re.edu.intern_management_prj.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "Tên đăng nhập, email hoặc số điện thoại không được để trống!")
    private String loginInfo;

    @NotBlank(message = "Mật khẩu không được để trống!")
    private String password;
}
