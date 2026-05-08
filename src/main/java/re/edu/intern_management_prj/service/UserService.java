package re.edu.intern_management_prj.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import re.edu.intern_management_prj.model.entity.User;
import re.edu.intern_management_prj.repository.UserRepository;
import re.edu.intern_management_prj.util.enums.RoleEnum;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Page<User> getAllUsers(Pageable pageable, String role) {
        if (role != null) {
            RoleEnum roleEnum = RoleEnum.valueOf(role.toUpperCase());
            return userRepository.findByRole(roleEnum, pageable);
        } else {
            return userRepository.findAll(pageable);
        }
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy người dùng với id: " + id));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
}
