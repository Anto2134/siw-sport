insert into presidente(nome,codice_fiscale,cognome,email,luogo_nascita,data_nascita) values('Adriano', 'ctgn', 'de Laurentis', 'adl@g.com','Roma','21/02/2003');
INSERT INTO presidente (id, nome, cognome, email) VALUES (8, 'Toto', 'cotu', 'tommy@shelby.com');
insert into squadra(id, nome, indirizzo, anno_fondazione,presidente_id) values('12', 'As Roma', 'Via Angelo Bellani', '21/02/2003','8');
insert into giocatore(nome, squadra_id, id, is_svincolato, ruolo, luogo_nascita) values('Rafel Leao', '12', '99', 'false', 'attaccante', 'Portogallo');
insert into giocatore(nome, id, is_svincolato) values('Cristiano', '98', 'true');
insert into giocatore(nome, id, is_svincolato) values('Lionel', '97', 'true');

INSERT INTO presidente (id, nome, cognome, email) VALUES (nextval('hibernate_sequence'), 'Tommy', 'Shelby', 'tommy@shelby.com');
INSERT INTO credentials (id, username, password, ruolo, user_id) VALUES (nextval('credentials_seq'), 'Tommy', '$2b$12$3f2Z2J4f48ih1gAY242rMuuKSZ0/bNUt/DY9Nv0E1dzu0G9a/Tr6y', 'ADMIN', (SELECT id FROM presidente WHERE nome='Tommy' AND cognome='Shelby'));
-- INSERT INTO credentials (id, username, password, ruolo, user_id) VALUES (nextval('credentials_seq'), 'Toto', '$2b$12$3f2Z2J4f48ih1gAY242rMuuKSZ0/bNUt/DY9Nv0E1dzu0G9a/Tr6y', 'USER', (SELECT id FROM presidente WHERE nome='Toto' AND cognome='Cotu'));
INSERT INTO credentials (id, username, password, ruolo, user_id) VALUES (nextval('credentials_seq'), 'Toto', '$2b$12$3f2Z2J4f48ih1gAY242rMuuKSZ0/bNUt/DY9Nv0E1dzu0G9a/Tr6y', 'USER', '8');
INSERT INTO squadra_giocatori(giocatori_id, squadra_id) VALUES ('99', '12');
