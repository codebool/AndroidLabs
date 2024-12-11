CREATE TABLE scores (
    id INTEGER PRIMARY KEY,
    score INTEGER
);

CREATE INDEX idx_scores_score ON scores(score);

ALTER TABLE scores ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;


INSERT INTO scores (score) VALUES (100);
INSERT INTO scores (score) VALUES (200);

DELETE FROM scores;

DROP TABLE IF EXISTS scores;
