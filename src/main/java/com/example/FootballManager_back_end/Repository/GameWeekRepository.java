package com.example.FootballManager_back_end.Repository;

import com.example.FootballManager_back_end.Entity.GameWeek;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameWeekRepository extends JpaRepository<GameWeek, Long> {
}
