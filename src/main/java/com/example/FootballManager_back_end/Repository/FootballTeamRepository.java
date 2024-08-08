package com.example.FootballManager_back_end.Repository;

import com.example.FootballManager_back_end.Entity.FootballTeam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FootballTeamRepository extends JpaRepository<FootballTeam, Long> {
}
