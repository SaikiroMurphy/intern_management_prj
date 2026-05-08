package re.edu.intern_management_prj.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import re.edu.intern_management_prj.model.entity.User;
import re.edu.intern_management_prj.util.enums.RoleEnum;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsernameOrEmailOrPhoneNumber(String username, String email, String phoneNumber);

    Optional<User> findByUsername(String username);

    Page<User> findByRole(RoleEnum role, Pageable pageable);
}
