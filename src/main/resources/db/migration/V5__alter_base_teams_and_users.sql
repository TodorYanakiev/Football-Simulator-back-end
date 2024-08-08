ALTER TABLE base_football_teams
DROP COLUMN stadiumName,
DROP COLUMN startBudget,
ADD COLUMN stadium_name VARCHAR(255) NOT NULL,
ADD COLUMN start_budget INT NOT NULL CHECK (start_budget >= 0);

ALTER TABLE users
DROP COLUMN role,
ADD COLUMN role ENUM('USER', 'ADMIN') NOT NULL;