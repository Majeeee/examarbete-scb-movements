CREATE TABLE roles (
                       id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE
);

-- Skapar användare
CREATE TABLE users (
                       id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       enabled BOOLEAN NOT NULL DEFAULT FALSE
);

-- Koppling mellan användare och roller
CREATE TABLE user_roles (
                            user_id INT REFERENCES users(id),
                            role_id INT REFERENCES roles(id),
                            PRIMARY KEY(user_id, role_id)
);

-- Lägger till standardroller
INSERT INTO roles (name) VALUES ('ROLE_USER'), ('ROLE_ADMIN');