package re.edu.intern_management_prj.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import re.edu.intern_management_prj.model.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsernameOrEmailOrPhoneNumber(String username, String email, String phoneNumber);

    Optional<User> findByUsername(String username);
}
