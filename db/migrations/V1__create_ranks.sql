CREATE TYPE rank_t AS ENUM ('OWNER', 'ADMIN', 'MOD', 'MVP', 'VIP');

CREATE TABLE player_ranks (
    uuid UUID PRIMARY KEY,
    rank rank_t NOT NULL
);