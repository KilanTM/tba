


-- Inventory table
CREATE TABLE inv
(
    item_id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(1000)
);

-- Player table
CREATE TABLE player
(
    id               SERIAL PRIMARY KEY,
    current_location INT REFERENCES locations(id),
    username         VARCHAR(20) NOT NULL,
    password         VARCHAR(1000) NOT NULL,
    inv INT []-- Using an array of item IDs for the player's inventory
);

