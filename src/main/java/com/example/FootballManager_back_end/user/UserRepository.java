package com.example.FootballManager_back_end.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);
  User findByUsername(String username); // if does not work try with Optional
}
