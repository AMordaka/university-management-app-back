INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
INSERT INTO roles(name) VALUES('ROLE_TEACHER');

INSERT INTO users(id, created_at, updated_at, city, email, name, number_street, password, postal_code, street, surname, username) VALUES (null, '2018-07-23 11:07:57.469', '2018-07-23 11:07:57.469', 'Wolborz', 'student@student.pl', 'Arkadiusz', '100', '$2a$10$TV6rSJdq9tiLbZN/zvTOiedQ9okCgxsdWvaj61b1tKbOay9EbPABS', '97-320', 'Sezamkowa', 'Nazwisko', '200400');
INSERT INTO user_roles (user_id, role_id) VALUES ('1', '1');

INSERT INTO users(id, created_at, updated_at, city, email, name, number_street, password, postal_code, street, surname, username) VALUES (null, '2018-07-23 11:07:57.469', '2018-07-23 11:07:57.469', 'Wolborz', 'admin@admin.pl', 'Arkadiusz', '100', '$2a$10$TV6rSJdq9tiLbZN/zvTOiedQ9okCgxsdWvaj61b1tKbOay9EbPABS', '97-320', 'Sezamkowa', 'Nazwisko', '200200');
INSERT INTO user_roles(user_id, role_id) VALUES ('2', '2');

INSERT INTO users(id, created_at, updated_at, city, email, name, number_street, password, postal_code, street, surname, username) VALUES (null, '2018-07-23 11:07:57.469', '2018-07-23 11:07:57.469', 'Wolborz', 'teacher@teacher.pl', 'Arkadiusz', '100', '$2a$10$TV6rSJdq9tiLbZN/zvTOiedQ9okCgxsdWvaj61b1tKbOay9EbPABS', '97-320', 'Sezamkowa', 'Nazwisko', '200100');
INSERT INTO user_roles(user_id, role_id) VALUES ('3', '3');

--password arekmordaka
--jdbc:h2:mem:testdb
--root: sa / password:

INSERT INTO items(id, created_at, updated_at, grade, subject_name, student_id, teacher_id) VALUES (null, '2018-07-23 11:07:57.469', '2018-07-23 11:07:57.469', '5', 'Matematyka', '1', '3');