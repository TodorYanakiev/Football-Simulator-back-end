package com.example.FootballManager_back_end.User;

import com.example.FootballManager_back_end.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "The field must not be empty!")
    @Size(max = 50,message = "The maximum length is 50 characters!")
    private String name;
    @NotEmpty(message = "The field must not be empty!")
    @Size(max = 50,message = "The maximum length is 50 characters!")
    private String lastName;
    @NotEmpty(message = "The field must not be empty!")
    private String password;
    @NotEmpty(message = "The field must not be empty!")
    @Size(min = 4, message = "The minimum length is 4 characters!")
    @Size(max = 20, message = "The maximum length is 20 characters!")
    private String username;
    @NotEmpty(message = "The field must not be empty!")
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
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
