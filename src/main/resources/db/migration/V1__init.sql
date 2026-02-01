-- V1__init.sql
CREATE TABLE IF NOT EXISTS roles (
                                     id BIGSERIAL PRIMARY KEY,
                                     name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users (
                                     id BIGSERIAL PRIMARY KEY,
                                     username VARCHAR(100) NOT NULL UNIQUE,
                                     email VARCHAR(150) NOT NULL UNIQUE,
                                     password VARCHAR(255) NOT NULL,
                                     first_name VARCHAR(100),
                                     last_name VARCHAR(100),
                                     enabled BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS user_roles (
                                          id BIGSERIAL PRIMARY KEY,
                                          user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                                          role_id BIGINT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
                                          UNIQUE (user_id, role_id)
);

CREATE TABLE IF NOT EXISTS permissions (
                                           id BIGSERIAL PRIMARY KEY,
                                           name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS role_permissions (
                                                id BIGSERIAL PRIMARY KEY,
                                                role_id BIGINT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
                                                permission_id BIGINT NOT NULL REFERENCES permissions(id) ON DELETE CASCADE,
                                                UNIQUE (role_id, permission_id)
);

CREATE TABLE IF NOT EXISTS movementsRecord (
                                               id BIGSERIAL PRIMARY KEY,
                                               region_code VARCHAR(10) NOT NULL,
                                               year INTEGER NOT NULL,
                                               date DATE NOT NULL,
                                               sex VARCHAR(10),
                                               age VARCHAR(20),
                                               move_type VARCHAR(20) NOT NULL,
                                               inflow INTEGER,
                                               outflow INTEGER
);

CREATE INDEX IF NOT EXISTS idx_movements_region ON movementsRecord(region_code);
CREATE INDEX IF NOT EXISTS idx_movements_year ON movementsRecord(year);
CREATE INDEX IF NOT EXISTS idx_movements_type ON movementsRecord(move_type);


-- INSERT INTO roles (name) VALUES ('ROLE_USER') ON CONFLICT DO NOTHING;
-- INSERT INTO roles (name) VALUES ('ROLE_ADMIN') ON CONFLICT DO NOTHING;
