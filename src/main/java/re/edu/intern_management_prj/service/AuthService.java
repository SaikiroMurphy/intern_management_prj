package re.edu.intern_management_prj.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import re.edu.intern_management_prj.config.jwt.JwtService;
import re.edu.intern_management_prj.config.security.CustomUserDetails;
import re.edu.intern_management_prj.model.entity.User;
import re.edu.intern_management_prj.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public String login(String loginInfo) {
        User user = userRepository.findByUsernameOrEmailOrPhoneNumber(loginInfo, loginInfo, loginInfo)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng!"));
        
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
        
        CustomUserDetails userDetails = CustomUserDetails.builder()
                .user(user)
                .auth(authorities)
                .build();
        
        return jwtService.generateToken(userDetails);
    }

    public User getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() == null) {
            throw new UsernameNotFoundException("Không có người dùng đã xác thực.");
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof CustomUserDetails userDetails)) {
            throw new UsernameNotFoundException("Thông tin người dùng không hợp lệ.");
        }

        User user = userDetails.getUser();
        return user;
    }
}
