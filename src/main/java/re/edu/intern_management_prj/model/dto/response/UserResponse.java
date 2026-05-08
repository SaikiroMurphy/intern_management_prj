package re.edu.intern_management_prj.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserResponse {
    private Integer id;
    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String role;
    private boolean isActive;
}
