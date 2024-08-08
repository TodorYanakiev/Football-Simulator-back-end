package com.example.FootballManager_back_end.Repository;

import com.example.FootballManager_back_end.Entity.FootballPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FootballPlayerRepository extends JpaRepository<FootballPlayer, Long> {
}
