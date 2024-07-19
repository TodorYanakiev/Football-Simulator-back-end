package com.example.FootballManager_back_end.user;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
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
  private String lastname;
  @NotEmpty(message = "The field must not be empty!")
  private String password;
  @NotEmpty(message = "The field must not be empty!")
  @Size(min = 4, message = "The minimum length is 4 characters!")
  @Size(max = 20, message = "The maximum length is 20 characters!")
  private String username;
  @NotEmpty(message = "The field must not be empty!")
  private String email;
  @Enumerated(EnumType.STRING)
  private Role role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getPassword() {
    return password;
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
