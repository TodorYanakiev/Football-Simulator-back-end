CREATE TABLE base_football_teams (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    abbreviation VARCHAR(4) NOT NULL,
    stadiumName VARCHAR(255) NOT NULL,
    startBudget INT NOT NULL CHECK (startBudget >= 0)
);
