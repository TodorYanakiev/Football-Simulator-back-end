package com.example.FootballManager_back_end.Repository;

import com.example.FootballManager_back_end.Entity.FootballMatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FootballMatchRepository extends JpaRepository<FootballMatch, Long> {
}
