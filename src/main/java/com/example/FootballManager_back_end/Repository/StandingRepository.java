package com.example.FootballManager_back_end.Repository;

import com.example.FootballManager_back_end.Entity.Standing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StandingRepository extends JpaRepository<Standing, Long> {
}
