CREATE TABLE leagues (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    league_status ENUM('NOT_STARTED', 'STARTED', 'FINISHED') NOT NULL
);

CREATE TABLE teams (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    abbreviation VARCHAR(255) NOT NULL,
    stadium_name VARCHAR(255) NOT NULL,
    budget INT NOT NULL,
    league_id BIGINT,
    user_id BIGINT,
    CONSTRAINT fk_teams_league FOREIGN KEY (league_id) REFERENCES leagues(id),
    CONSTRAINT fk_teams_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE players (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    nationality VARCHAR(255) NOT NULL,
    age TINYINT NOT NULL CHECK (age >= 15),
    shirt_number TINYINT NOT NULL CHECK (shirt_number BETWEEN 1 AND 99),
    position ENUM('GK', 'LB', 'LCB', 'CB', 'RCB', 'RB', 'LM', 'LCM', 'CM', 'RCM', 'RM', 'RF', 'LCF', 'CF', 'RCF', 'LF') NOT NULL,
    football_team_id BIGINT,
    price INT NOT NULL CHECK (price >= 0),
    defending TINYINT NOT NULL CHECK (defending BETWEEN 1 AND 99),
    speed TINYINT NOT NULL CHECK (speed BETWEEN 1 AND 99),
    dribble TINYINT NOT NULL CHECK (dribble BETWEEN 1 AND 99),
    scoring TINYINT NOT NULL CHECK (scoring BETWEEN 1 AND 99),
    passing TINYINT NOT NULL CHECK (passing BETWEEN 1 AND 99),
    stamina TINYINT NOT NULL CHECK (stamina BETWEEN 1 AND 99),
    positioning TINYINT NOT NULL CHECK (positioning BETWEEN 1 AND 99),
    goalkeeping TINYINT NOT NULL CHECK (goalkeeping BETWEEN 1 AND 99),
    CONSTRAINT fk_players_football_team FOREIGN KEY (football_team_id) REFERENCES teams(id)
);

CREATE TABLE game_weeks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    week_number TINYINT NOT NULL,
    league_id BIGINT,
    game_week_status ENUM('NOT_STARTED', 'STARTED', 'FINISHED') NOT NULL,
    CONSTRAINT fk_game_weeks_league FOREIGN KEY (league_id) REFERENCES leagues(id)
);

CREATE TABLE matches (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    home_team_id BIGINT,
    away_team_id BIGINT,
    home_team_score SMALLINT,
    away_team_score SMALLINT,
    home_attacks SMALLINT,
    away_attacks SMALLINT,
    danger_home_attacks SMALLINT,
    danger_away_attacks SMALLINT,
    home_shots SMALLINT,
    away_shots SMALLINT,
    home_shots_on_target SMALLINT,
    away_shots_on_target SMALLINT,
    half_times_passed TINYINT,
    first_half_time_minutes_passed TINYINT,
    second_half_time_minutes_passed TINYINT,
    remaining_home_substitutions TINYINT,
    remaining_away_substitutions TINYINT,
    stadium_name VARCHAR(255) NOT NULL,
    game_week_id BIGINT,
    match_status ENUM('NOT_STARTED', 'STARTED', 'FINISHED') NOT NULL,
    CONSTRAINT fk_matches_home_team FOREIGN KEY (home_team_id) REFERENCES teams(id),
    CONSTRAINT fk_matches_away_team FOREIGN KEY (away_team_id) REFERENCES teams(id),
    CONSTRAINT fk_matches_game_week FOREIGN KEY (game_week_id) REFERENCES game_weeks(id)
);

CREATE TABLE standings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    football_team_id BIGINT,
    league_id BIGINT,
    points TINYINT NOT NULL CHECK (points >= 0),
    scored_goals SMALLINT NOT NULL CHECK (scored_goals >= 0),
    conceded_goals SMALLINT NOT NULL CHECK (conceded_goals >= 0),
    played_matches TINYINT NOT NULL CHECK (played_matches >= 0),
    CONSTRAINT fk_standings_football_team FOREIGN KEY (football_team_id) REFERENCES teams(id),
    CONSTRAINT fk_standings_league FOREIGN KEY (league_id) REFERENCES leagues(id)
);

ALTER TABLE users
DROP COLUMN lastName,
ADD COLUMN last_name VARCHAR(255),
ADD COLUMN team_id BIGINT,
ADD CONSTRAINT fk_users_team FOREIGN KEY (team_id) REFERENCES teams(id);