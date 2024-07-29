package com.example.FootballManager_back_end.Service;

import com.example.FootballManager_back_end.Repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TeamService {
    TeamRepository teamRepository;
}
