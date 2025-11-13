-- Script limpo para restaurar o banco no Neon
-- Database: neondb (já existe no Neon)

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;
SET search_path TO public;

-- Drop tables if exist (ordem reversa para respeitar FK)
DROP TABLE IF EXISTS phase_user CASCADE;
DROP TABLE IF EXISTS phase_transition CASCADE;
DROP TABLE IF EXISTS phase_correct_diagrams CASCADE;
DROP TABLE IF EXISTS phase_character_dialogues CASCADE;
DROP TABLE IF EXISTS phase CASCADE;
DROP TABLE IF EXISTS user_gamemap CASCADE;
DROP TABLE IF EXISTS inventory_items CASCADE;
DROP TABLE IF EXISTS inventory CASCADE;
DROP TABLE IF EXISTS game_map CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS avatar CASCADE;
DROP TABLE IF EXISTS "character" CASCADE;
DROP TABLE IF EXISTS item CASCADE;
DROP TABLE IF EXISTS tips CASCADE;

-- Create tables
CREATE TABLE avatar (
    id SERIAL PRIMARY KEY,
    file_path VARCHAR(255)
);

CREATE TABLE "character" (
    id SERIAL PRIMARY KEY,
    file_path VARCHAR(255),
    name VARCHAR(255)
);

CREATE TABLE game_map (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    created_at TIMESTAMP,
    created_by_user_id INTEGER
);

CREATE TABLE inventory (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL
);

CREATE TABLE inventory_items (
    id SERIAL PRIMARY KEY,
    inventory_id INTEGER NOT NULL,
    item_name VARCHAR(255) NOT NULL,
    quantity INTEGER NOT NULL
);

CREATE TABLE item (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255),
    file_path VARCHAR(255),
    price INTEGER NOT NULL,
    title VARCHAR(255)
);

CREATE TABLE phase (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255),
    diagram_initial JSON,
    max_time INTEGER NOT NULL,
    mode VARCHAR(255) CHECK (mode IN ('BASIC', 'INTERMEDIATE', 'ADVANCED')),
    title VARCHAR(255),
    type VARCHAR(255) CHECK (type IN ('BUILD', 'FIX', 'COMPLETE')),
    character_id INTEGER,
    gamemap_id INTEGER,
    parent_phase_id INTEGER,
    node_type VARCHAR(255) CHECK (node_type IN ('ACTIVITY', 'DECISION')),
    diagram_type VARCHAR(255) CHECK (diagram_type IN ('CLASS', 'USE_CASE'))
);

CREATE TABLE phase_character_dialogues (
    phase_id INTEGER NOT NULL,
    character_dialogues VARCHAR(255),
    dialogue_order INTEGER NOT NULL
);

CREATE TABLE phase_correct_diagrams (
    phase_id INTEGER NOT NULL,
    diagram_json TEXT
);

CREATE TABLE phase_transition (
    id SERIAL PRIMARY KEY,
    option_text VARCHAR(255),
    from_phase_id INTEGER,
    to_phase_id INTEGER
);

CREATE TABLE phase_user (
    id SERIAL PRIMARY KEY,
    coins INTEGER NOT NULL,
    is_current BOOLEAN NOT NULL,
    reputation INTEGER NOT NULL,
    status VARCHAR(255) CHECK (status IN ('LOCKED', 'COMPLETED', 'AVAILABLE')),
    user_diagram JSON,
    phase_id INTEGER,
    user_id INTEGER,
    accuracy INTEGER NOT NULL,
    is_completed BOOLEAN NOT NULL
);

CREATE TABLE tips (
    id SERIAL PRIMARY KEY,
    tip TEXT
);

CREATE TABLE user_gamemap (
    user_id INTEGER NOT NULL,
    gamemap_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, gamemap_id)
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    coins INTEGER NOT NULL,
    email VARCHAR(255),
    name VARCHAR(255),
    password VARCHAR(255),
    reputation INTEGER NOT NULL,
    avatar_id INTEGER
);

-- Add foreign keys
ALTER TABLE game_map ADD CONSTRAINT fk_game_map_user FOREIGN KEY (created_by_user_id) REFERENCES users(id);
ALTER TABLE inventory ADD CONSTRAINT fk_inventory_user FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE inventory_items ADD CONSTRAINT fk_inventory_items_inventory FOREIGN KEY (inventory_id) REFERENCES inventory(id);
ALTER TABLE phase ADD CONSTRAINT fk_phase_character FOREIGN KEY (character_id) REFERENCES "character"(id);
ALTER TABLE phase ADD CONSTRAINT fk_phase_gamemap FOREIGN KEY (gamemap_id) REFERENCES game_map(id);
ALTER TABLE phase ADD CONSTRAINT fk_phase_parent FOREIGN KEY (parent_phase_id) REFERENCES phase(id);
ALTER TABLE phase_character_dialogues ADD CONSTRAINT fk_phase_dialogues_phase FOREIGN KEY (phase_id) REFERENCES phase(id);
ALTER TABLE phase_correct_diagrams ADD CONSTRAINT fk_phase_diagrams_phase FOREIGN KEY (phase_id) REFERENCES phase(id);
ALTER TABLE phase_transition ADD CONSTRAINT fk_transition_from FOREIGN KEY (from_phase_id) REFERENCES phase(id);
ALTER TABLE phase_transition ADD CONSTRAINT fk_transition_to FOREIGN KEY (to_phase_id) REFERENCES phase(id);
ALTER TABLE phase_user ADD CONSTRAINT fk_phase_user_phase FOREIGN KEY (phase_id) REFERENCES phase(id);
ALTER TABLE phase_user ADD CONSTRAINT fk_phase_user_user FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE user_gamemap ADD CONSTRAINT fk_user_gamemap_user FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE user_gamemap ADD CONSTRAINT fk_user_gamemap_gamemap FOREIGN KEY (gamemap_id) REFERENCES game_map(id);
ALTER TABLE users ADD CONSTRAINT fk_users_avatar FOREIGN KEY (avatar_id) REFERENCES avatar(id);

-- Insert sample data
INSERT INTO avatar (file_path) VALUES 
('1760816214484_homem1.png'),
('1760816220108_homem2.png'),
('1760816223692_homem3.png'),
('1760816227036_mulher1.png'),
('1760816231011_mulher2.png'),
('1760816234979_robo1.png');

INSERT INTO "character" (file_path, name) VALUES
('1760816298979_character_teacher_02.png', 'Professor'),
('1760816309634_character_teacher_03.png', 'Mentor'),
('1761079332726_marina.png', 'Marina'),
('1761079362672_cto.png', 'CTO'),
('1761079470300_orientador.png', 'Orientador'),
('1761079511609_COO.png', 'COO'),
('1761079559030_Diretor.png', 'Diretor'),
('1761179747273_gerente.png', 'Gerente'),
('1762655571362_jef.png', 'Jef');

INSERT INTO item (description, file_path, price, title) VALUES
('Item de ajuda', 'item1.png', 100, 'Dica Básica'),
('Item premium', 'item2.png', 500, 'Dica Avançada');

-- Create default user for testing
INSERT INTO users (coins, email, name, password, reputation, avatar_id) VALUES
(1000, 'test@example.com', 'Usuário Teste', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 100, 1);

