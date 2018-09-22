INSERT INTO roles(name) VALUES('ROLE_ADMIN');
INSERT INTO roles(name) VALUES('ROLE_TEACHER');
INSERT INTO roles(name) VALUES('ROLE_STUDENT');

INSERT INTO USERS(id,created_at,updated_at,city,email,name,number_street,password,postal_code,street,surname,username) VALUES (null, '2018-08-03', '2018-08-03', 'Wolborz','arek@wp.pl','Arek','100','$2a$10$.MZ.hlndzmYM8MYntstnROZDeRlTSbEUPWCEPzD.Tmd0SH8ZlpS2G','97-110','Lodzka','Kowalski','100100');
INSERT INTO STUDENT(id,user_id) VALUES (null,'1');

INSERT INTO USERS(id,created_at,updated_at,city,email,name,number_street,password,postal_code,street,surname,username) VALUES (null, '2017-02-04', '2018-08-03', 'Moszczenica','mail@wp.pl','Tomasz','100','$2a$10$.MZ.hlndzmYM8MYntstnROZDeRlTSbEUPWCEPzD.Tmd0SH8ZlpS2G','97-110','Lodzka','Kowalski','100101');
INSERT INTO TEACHER(id,user_id) VALUES (null,'2');

INSERT INTO USERS(id,created_at,updated_at,city,email,name,number_street,password,postal_code,street,surname,username) VALUES (null, '2018-09-18', '2018-08-03', 'Moszczenica','mail2@wp.pl','Tomasz','100','$2a$10$.MZ.hlndzmYM8MYntstnROZDeRlTSbEUPWCEPzD.Tmd0SH8ZlpS2G','97-110','Lodzka','Kowalski','100102');
INSERT INTO TEACHER(id,user_id) VALUES (null,'3');

INSERT INTO USERS(id,created_at,updated_at,city,email,name,number_street,password,postal_code,street,surname,username) VALUES (null, '2018-08-03', '2018-08-03', 'Wolborz','student1@wp.pl','Student1','100','$2a$10$.MZ.hlndzmYM8MYntstnROZDeRlTSbEUPWCEPzD.Tmd0SH8ZlpS2G','97-110','Lodzka','Student1','200000');
INSERT INTO STUDENT(id,user_id) VALUES (null,'4');
INSERT INTO USERS(id,created_at,updated_at,city,email,name,number_street,password,postal_code,street,surname,username) VALUES (null, '2018-08-03', '2018-08-03', 'Wolborz','student2@wp.pl','Student2','100','$2a$10$.MZ.hlndzmYM8MYntstnROZDeRlTSbEUPWCEPzD.Tmd0SH8ZlpS2G','97-110','Lodzka','Student2','200001');
INSERT INTO STUDENT(id,user_id) VALUES (null,'5');
INSERT INTO USERS(id,created_at,updated_at,city,email,name,number_street,password,postal_code,street,surname,username) VALUES (null, '2018-08-03', '2018-08-03', 'Wolborz','student3@wp.pl','Student3','100','$2a$10$.MZ.hlndzmYM8MYntstnROZDeRlTSbEUPWCEPzD.Tmd0SH8ZlpS2G','97-110','Lodzka','Student3','200002');
INSERT INTO STUDENT(id,user_id) VALUES (null,'6');
INSERT INTO USERS(id,created_at,updated_at,city,email,name,number_street,password,postal_code,street,surname,username) VALUES (null, '2018-08-03', '2018-08-03', 'Wolborz','student4@wp.pl','Student4','100','$2a$10$.MZ.hlndzmYM8MYntstnROZDeRlTSbEUPWCEPzD.Tmd0SH8ZlpS2G','97-110','Lodzka','Student4','200003');
INSERT INTO STUDENT(id,user_id) VALUES (null,'7');

INSERT INTO ITEM (id, created_at, updated_at, subject_name, teacher_id) VALUES (null, '2018-09-20 17:51:31.811', '2018-09-20 17:51:31.811', 'Informatyka', '1');


INSERT INTO item_student (item_student_id, grade, item_id, student_id) VALUES (null, null, '1', '1');

INSERT INTO USER_ROLES(user_id,role_id) VALUES('1','3');
INSERT INTO USER_ROLES(user_id,role_id) VALUES('4','3');
INSERT INTO USER_ROLES(user_id,role_id) VALUES('5','3');
INSERT INTO USER_ROLES(user_id,role_id) VALUES('6','3');
INSERT INTO USER_ROLES(user_id,role_id) VALUES('7','3');
INSERT INTO USER_ROLES(user_id,role_id) VALUES('2','2');
INSERT INTO USER_ROLES(user_id,role_id) VALUES('3','1');
