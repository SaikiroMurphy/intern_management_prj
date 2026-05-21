package re.edu.intern_management_prj.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import re.edu.intern_management_prj.model.entity.User;
import re.edu.intern_management_prj.util.enums.RoleEnum;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsernameOrEmailOrPhoneNumber(String username, String email, String phoneNumber);

    Page<User> findByIsDeletedFalse(Pageable pageable);

    Optional<User> findByUsername(String username);

    Page<User> findByRoleAndIsDeletedFalse(RoleEnum role, Pageable pageable);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmailAndIdNot(String email, Integer id);

    boolean existsByUsernameAndIdNot(String username, Integer id);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id);
}
