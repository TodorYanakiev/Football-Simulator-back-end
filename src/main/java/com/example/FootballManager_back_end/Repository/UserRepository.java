package com.example.FootballManager_back_end.Repository;

import java.util.Optional;

import com.example.FootballManager_back_end.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);
  Optional<User> findByUsername(String username); // if does not work try with Optional
}
