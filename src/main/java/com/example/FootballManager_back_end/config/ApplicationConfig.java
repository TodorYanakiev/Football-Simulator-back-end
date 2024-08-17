package com.example.FootballManager_back_end.config;

import com.example.FootballManager_back_end.DTO.*;
import com.example.FootballManager_back_end.Entity.*;
import com.example.FootballManager_back_end.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository repository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationEntryPoint myJwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }
    
    /*@Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }*/
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Standing.class, StandingDTO.class)
                .addMapping(Standing::getFootballTeam, StandingDTO::setFootballTeam)
                .addMapping(Standing::getLeague, StandingDTO::setLeague)
                .addMapping(Standing::getPoints, StandingDTO::setPoints)
                .addMapping(Standing::getScoredGoals, StandingDTO::setScoredGoals)
                .addMapping(Standing::getConcededGoals, StandingDTO::setConcededGoals)
                .addMapping(Standing::getPlayedMatches, StandingDTO::setPlayedMatches);

        modelMapper.createTypeMap(FootballTeam.class, FootballTeamDTO.class)
                .addMapping(FootballTeam::getBaseTeam, FootballTeamDTO::setBaseTeam)
                /*.addMapping(FootballTeam::getPlayerList, FootballTeamDTO::setPlayerList)*/
                .addMapping(FootballTeam::getLeague, FootballTeamDTO::setLeague)
                .addMapping(FootballTeam::getStanding, FootballTeamDTO::setStanding)
                .addMapping(FootballTeam::getUser, FootballTeamDTO::setUser)
                .addMapping(FootballTeam::getBudget, FootballTeamDTO::setBudget);

        modelMapper.createTypeMap(League.class, LeagueDTO.class)
                /*.addMapping(League::getFootballTeamList, LeagueDTO::setFootballTeamList)
                .addMapping(League::getGameWeekList, LeagueDTO::setGameWeekList)
                .addMapping(League::getStandings, LeagueDTO::setStandings)*/
                .addMapping(League::getLeagueStatus, LeagueDTO::setLeagueStatus)
                .addMapping(League::getName, LeagueDTO::setName);

        modelMapper.createTypeMap(GameWeek.class, GameWeekDTO.class)
                /*.addMapping(GameWeek::getMatchList, GameWeekDTO::setMatchList)*/
                .addMapping(GameWeek::getLeague, GameWeekDTO::setLeague)
                .addMapping(GameWeek::getGameWeekStatus, GameWeekDTO::setGameWeekStatus);

        modelMapper.createTypeMap(FootballMatch.class, FootballMatchDTO.class)
                .addMapping(FootballMatch::getHomeTeam, FootballMatchDTO::setHomeTeam)
                .addMapping(FootballMatch::getAwayTeam, FootballMatchDTO::setAwayTeam)
                .addMapping(FootballMatch::getGameWeek, FootballMatchDTO::setGameWeek)
                .addMapping(FootballMatch::getMatchStatus, FootballMatchDTO::setMatchStatus);

        modelMapper.createTypeMap(BaseFootballPlayer.class, BaseFootballPlayerDTO.class)
                .addMapping(BaseFootballPlayer::getFirstName, BaseFootballPlayerDTO::setFirstName)
                .addMapping(BaseFootballPlayer::getLastName, BaseFootballPlayerDTO::setLastName)
                .addMapping(BaseFootballPlayer::getNationality, BaseFootballPlayerDTO::setNationality)
                .addMapping(BaseFootballPlayer::getShirtNumber, BaseFootballPlayerDTO::setShirtNumber)
                .addMapping(BaseFootballPlayer::getPosition, BaseFootballPlayerDTO::setPosition);

        return modelMapper;
    }
}
