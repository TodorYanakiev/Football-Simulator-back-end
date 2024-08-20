package com.example.FootballManager_back_end.Repository;

import com.example.FootballManager_back_end.Entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer,Long> {
}
