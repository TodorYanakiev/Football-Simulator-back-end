package com.example.FootballManager_back_end.Repository;

import com.example.FootballManager_back_end.Entity.League;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<League, Long> {
}
