package re.edu.intern_management_prj.config.security;

import java.util.Collection;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import re.edu.intern_management_prj.model.entity.User;

@Builder
public class CustomUserDetails implements UserDetails{
    private User user;
    private Collection<? extends GrantedAuthority> auth;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return auth;
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public boolean isActive() {
        return user.isActive();
    }
}
