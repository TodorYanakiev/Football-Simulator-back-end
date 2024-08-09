package com.example.FootballManager_back_end.Repository;

import com.example.FootballManager_back_end.Entity.FootballPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FootballPlayerRepository extends JpaRepository<FootballPlayer, Long> {
    @Query("SELECT fp FROM FootballPlayer fp WHERE fp.footballTeam.league.id = :leagueId")
    List<FootballPlayer> findByFootballTeam_LeagueId(Long leagueId);
}
