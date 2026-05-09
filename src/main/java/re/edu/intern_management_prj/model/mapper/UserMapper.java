package re.edu.intern_management_prj.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import re.edu.intern_management_prj.model.dto.request.CreateUserRequest;
import re.edu.intern_management_prj.model.dto.request.UpdateUserRequest;
import re.edu.intern_management_prj.model.dto.response.UserDetailResponse;
import re.edu.intern_management_prj.model.dto.response.UserResponse;
import re.edu.intern_management_prj.model.entity.User;
import re.edu.intern_management_prj.util.enums.RoleEnum;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserResponse toUserResponse(User user);
    UserDetailResponse toUserDetailResponse(User user);
    
    @Mapping(source = "password", target = "passwordHash")
    User toUser(CreateUserRequest request);

    @Mapping(source = "password", target = "passwordHash")
    User toUser(UpdateUserRequest request);

    default RoleEnum stringToRoleEnum(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return RoleEnum.fromString(value);
    }

    default String roleEnumToString(RoleEnum role) {
        return role == null ? null : role.name();
    }
}
