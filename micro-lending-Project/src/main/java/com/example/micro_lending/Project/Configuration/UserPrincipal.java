package com.example.micro_lending.Project.Configuration;

import com.example.micro_lending.Project.Entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

    private final User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Ensure the ROLE_ prefix is included because Spring Security expects it
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().toUpperCase())) // role.getName() should be "ROLE_USER" or "ROLE_ADMIN" in DB
                .collect(Collectors.toSet());
    }



    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // email is used as username
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return user.isEnabled(); }
}
