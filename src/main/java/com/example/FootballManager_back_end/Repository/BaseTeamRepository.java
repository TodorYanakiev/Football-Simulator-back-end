package com.example.FootballManager_back_end.Repository;

import com.example.FootballManager_back_end.Entity.BaseTeam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseTeamRepository extends JpaRepository<BaseTeam, Long> {
}
