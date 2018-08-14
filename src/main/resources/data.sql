INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_TEACHER');

INSERT INTO USERS(id,created_at,updated_at,city,email,name,number_street,password,postal_code,street,surname,username) VALUES (null, '2018-08-03', '2018-08-03', 'Wolborz','arek@wp.pl','Arek','100','password','97-110','Lodzka','Kowalski','200200');
INSERT INTO STUDENT(id,user_id) VALUES (null,'1');

INSERT INTO USERS(id,created_at,updated_at,city,email,name,number_street,password,postal_code,street,surname,username) VALUES (null, '2018-08-03', '2018-08-03', 'Moszczenica','mail@wp.pl','Tomasz','100','password','97-110','Lodzka','Kowalski','150000');
INSERT INTO TEACHER(id,user_id) VALUES (null,'2');

INSERT INTO USERS(id,created_at,updated_at,city,email,name,number_street,password,postal_code,street,surname,username) VALUES (null, '2018-08-03', '2018-08-03', 'Moszczenica','mail2@wp.pl','Tomasz','100','password','97-110','Lodzka','Kowalski','150001');
INSERT INTO TEACHER(id,user_id) VALUES (null,'3');

INSERT INTO ITEM(id,created_at,updated_at,grade,subject_name,student_id,teacher_id) VALUES (null,'2018-08-03','2018-08-03','4','Informatyka','1','1');
INSERT INTO ITEM(id,created_at,updated_at,grade,subject_name,student_id,teacher_id) VALUES (null,'2018-08-03','2018-08-03','4','Matematyka','1','1');
INSERT INTO ITEM(id,created_at,updated_at,grade,subject_name,student_id,teacher_id) VALUES (null,'2018-08-03','2018-08-03',null,'WF','1','2');

