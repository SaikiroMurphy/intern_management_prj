package re.edu.intern_management_prj.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import re.edu.intern_management_prj.model.dto.request.CreateUserRequest;
import re.edu.intern_management_prj.model.dto.request.UpdateUserRequest;
import re.edu.intern_management_prj.model.dto.response.PageResponse;
import re.edu.intern_management_prj.model.dto.response.UserDetailResponse;
import re.edu.intern_management_prj.model.dto.response.UserResponse;
import re.edu.intern_management_prj.model.entity.User;
import re.edu.intern_management_prj.model.mapper.PageMapper;
import re.edu.intern_management_prj.model.mapper.UserMapper;
import re.edu.intern_management_prj.repository.UserRepository;
import re.edu.intern_management_prj.util.enums.RoleEnum;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final PageMapper pageMapper;

    public PageResponse<UserResponse> getAll(Pageable pageable, String role) {
        Page<User> userPage;

        if (role != null) {
            RoleEnum roleEnum = RoleEnum.valueOf(role.toUpperCase());
            userPage = userRepository.findByRole(roleEnum, pageable);
        } else {
            userPage = userRepository.findAll(pageable);
        }

        Page<UserResponse> responsePage = userPage.map(user -> {
            if (user.isDeleted()) {
                return null;
            }
            return userMapper.toUserResponse(user);
        });

        return pageMapper.toPageResponse(responsePage);
    }

    public UserDetailResponse getById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy người dùng với id: " + id));
        if (user.isDeleted()) {
            throw new EntityNotFoundException("Người dùng đã bị xóa!");
        }
        return userMapper.toUserDetailResponse(user);
    }

    public UserDetailResponse create(CreateUserRequest request) {
        User user = userMapper.toUser(request);
        if (userRepository.findByUsernameOrEmailOrPhoneNumber(user.getUsername(), user.getEmail(), user.getPhoneNumber()).isPresent()) {
            throw new IllegalArgumentException("Tên đăng nhập, Email hoặc số điện thoại đã tồn tại!");
        }
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        User savedUser = userRepository.save(user);
        return userMapper.toUserDetailResponse(savedUser);
    }

    public UserDetailResponse update(int id, UpdateUserRequest req) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy người dùng với id: " + id));
        if (user.isDeleted()) {
            throw new EntityNotFoundException("Người dùng đã bị xóa!");
        }

        if (req.getUsername() != null) {
            if (userRepository.existsByUsernameAndIdNot(req.getUsername(), id)) {
                throw new IllegalArgumentException("Tên đăng nhập đã tồn tại!");
            }
            user.setUsername(req.getUsername());
        }

        if (req.getEmail() != null) {
            if (userRepository.existsByEmailAndIdNot(req.getEmail(), id)) {
                throw new IllegalArgumentException("Email đã tồn tại!");
            }
            user.setEmail(req.getEmail());
        }

        if (req.getPhoneNumber() != null) {
            if (userRepository.existsByPhoneNumberAndIdNot(req.getPhoneNumber(), id)) {
                throw new IllegalArgumentException("Số điện thoại đã tồn tại!");
            }
            user.setPhoneNumber(req.getPhoneNumber());
        }

        if (req.getFullName() != null) {
            user.setFullName(req.getFullName());
        }

        if (req.getPassword() != null) {
            user.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        }

        if (req.getRole() != null) {
            user.setRole(userMapper.stringToRoleEnum(req.getRole()));
        }

        User updatedUser = userRepository.save(user);
        return userMapper.toUserDetailResponse(updatedUser);
    }

    public UserDetailResponse updateStatus(int id, boolean active) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy người dùng với id: " + id));
        if (user.isDeleted()) {
            throw new EntityNotFoundException("Người dùng đã bị xóa!");
        }
        user.setActive(active);
        User updatedUser = userRepository.save(user);
        return userMapper.toUserDetailResponse(updatedUser);
    }

    public UserDetailResponse updateRole(int id, String role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy người dùng với id: " + id));
        if (user.isDeleted()) {
            throw new EntityNotFoundException("Người dùng đã bị xóa!");
        }
        if (user.getRole() == RoleEnum.ADMIN) {
            throw new IllegalArgumentException("Không thể thay đổi vai trò của ADMIN!");
        }
        user.setRole(userMapper.stringToRoleEnum(role));
        User updatedUser = userRepository.save(user);
        return userMapper.toUserDetailResponse(updatedUser);
    }

    public void delete(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy người dùng với id: " + id));
        if (user.isDeleted()) {
            throw new EntityNotFoundException("Người dùng đã bị xóa!");
        }
        if (user.getRole() == RoleEnum.ADMIN) {
            throw new IllegalArgumentException("Không thể xóa người dùng có vai trò ADMIN!");
        }
        user.setDeleted(true);
        userRepository.save(user);
    }
}
