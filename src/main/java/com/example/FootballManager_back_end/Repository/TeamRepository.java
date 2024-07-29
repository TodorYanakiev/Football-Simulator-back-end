package com.example.FootballManager_back_end.Repository;

import com.example.FootballManager_back_end.Entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
