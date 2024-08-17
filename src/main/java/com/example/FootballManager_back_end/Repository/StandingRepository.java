package com.example.FootballManager_back_end.Repository;

import com.example.FootballManager_back_end.DTO.Response.StandingResponse;
import com.example.FootballManager_back_end.Entity.Standing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StandingRepository extends JpaRepository<Standing, Long> {
    @Query("SELECT s FROM Standing s WHERE s.league.id = :leagueId ORDER BY s.points DESC, s.scoredGoals DESC")
    List<Standing> findSortedStandingsByLeagueId(@Param("leagueId") Long leagueId);

}
