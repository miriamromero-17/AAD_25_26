CREATE TABLE IF NOT EXISTS messages (
    id BIGSERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    synced BOOLEAN NOT NULL DEFAULT false,
    synced_at TIMESTAMP NULL
);

INSERT INTO messages (content, created_at, synced)
VALUES ('Mensaje de prueba 1', NOW(), false);

INSERT INTO messages (content, created_at, synced)
VALUES ('Mensaje de prueba 2', NOW(), false);