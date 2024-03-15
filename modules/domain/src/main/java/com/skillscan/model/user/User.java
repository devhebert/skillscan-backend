package com.skillscan.model.user;

import com.skillscan.model.enums.JobTitle;
import com.skillscan.model.enums.Roles;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@Document(collection = "user")
public class User implements UserDetails {
    @Id
    private UUID id;
    private String username;
    private String password;
    private Roles role;
    private JobTitle jobTitle;
    private LocalDateTime createAt;

    public User() {
    }

    public User(String username, String password, Roles role, JobTitle jobTitle) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.role = role;
        this.jobTitle = jobTitle;
        this.createAt = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_COMMON"));

        if (this.role == Roles.COMMON) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        if (this.role == Roles.USER || this.role == Roles.COMMON) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        if (this.role == Roles.ADMIN || this.role == Roles.SYSADMIN) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        if (this.role == Roles.SYSADMIN) {
            authorities.add(new SimpleGrantedAuthority("ROLE_SYSADMIN"));
        }

        return authorities;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
