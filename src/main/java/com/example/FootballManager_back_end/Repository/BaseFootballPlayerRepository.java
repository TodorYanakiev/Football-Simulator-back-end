package com.example.FootballManager_back_end.Repository;

import com.example.FootballManager_back_end.Entity.BaseFootballPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseFootballPlayerRepository  extends JpaRepository<BaseFootballPlayer, Long> {
}
