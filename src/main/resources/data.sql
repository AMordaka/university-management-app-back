INSERT INTO roles(name) VALUES('ROLE_ADMIN');
INSERT INTO roles(name) VALUES('ROLE_TEACHER');
INSERT INTO roles(name) VALUES('ROLE_STUDENT');

INSERT INTO USERS(id,created_at,updated_at,city,email,name,number_street,password,postal_code,street,surname,username) VALUES (null, '2018-08-03', '2018-08-03', 'Wolborz','arek@wp.pl','Arek','100','$2a$10$.MZ.hlndzmYM8MYntstnROZDeRlTSbEUPWCEPzD.Tmd0SH8ZlpS2G','97-110','Lodzka','Kowalski','100100');
INSERT INTO STUDENT(id,user_id) VALUES (null,'1');

INSERT INTO USERS(id,created_at,updated_at,city,email,name,number_street,password,postal_code,street,surname,username) VALUES (null, '2017-02-04', '2018-08-03', 'Moszczenica','mail@wp.pl','Tomasz','100','$2a$10$.MZ.hlndzmYM8MYntstnROZDeRlTSbEUPWCEPzD.Tmd0SH8ZlpS2G','97-110','Lodzka','Kowalski','100101');
INSERT INTO TEACHER(id,user_id) VALUES (null,'2');

INSERT INTO USERS(id,created_at,updated_at,city,email,name,number_street,password,postal_code,street,surname,username) VALUES (null, '2018-09-18', '2018-08-03', 'Moszczenica','mail2@wp.pl','Tomasz','100','$2a$10$.MZ.hlndzmYM8MYntstnROZDeRlTSbEUPWCEPzD.Tmd0SH8ZlpS2G','97-110','Lodzka','Kowalski','100102');
INSERT INTO TEACHER(id,user_id) VALUES (null,'3');

INSERT INTO ITEM(id,created_at,updated_at,grade,subject_name,student_id,teacher_id) VALUES (null,'2018-08-03','2018-08-03','4','Informatyka','1','1');
INSERT INTO ITEM(id,created_at,updated_at,grade,subject_name,student_id,teacher_id) VALUES (null,'2018-08-03','2018-08-03',null,'Matematyka','1','1');
INSERT INTO ITEM(id,created_at,updated_at,grade,subject_name,student_id,teacher_id) VALUES (null,'2018-08-03','2018-08-03',null,'WF','1','1');

INSERT INTO USER_ROLES(user_id,role_id) VALUES('1','3');
INSERT INTO USER_ROLES(user_id,role_id) VALUES('2','2');
INSERT INTO USER_ROLES(user_id,role_id) VALUES('3','1');
