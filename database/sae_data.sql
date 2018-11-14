INSERT INTO sae.classes (branch, year, sec_from, sec_to) VALUES ('COE', 3, 1, 3);
INSERT INTO sae.classes (branch, year, sec_from, sec_to) VALUES ('COE', 3, 4, 6);
INSERT INTO sae.classes (branch, year, sec_from, sec_to) VALUES ('COE', 3, 7, 9);
INSERT INTO sae.classes (branch, year, sec_from, sec_to) VALUES ('COE', 3, 10, 12);
INSERT INTO sae.classes (branch, year, sec_from, sec_to) VALUES ('COE', 3, 13, 15);
INSERT INTO sae.classes (branch, year, sec_from, sec_to) VALUES ('COE', 3, 20, 23);
INSERT INTO sae.classes (branch, year, sec_from, sec_to) VALUES ('COE', 3, 16, 19);

INSERT INTO sae.courses (course_name, course_code) VALUES ('Parallel and distributed computing', 'UCS608');
INSERT INTO sae.courses (course_name, course_code) VALUES ('Advanced Data Structures', 'UCS616');
INSERT INTO sae.courses (course_name, course_code) VALUES ('Computer Architecture', 'UCS507');
INSERT INTO sae.courses (course_name, course_code) VALUES ('Artificial Intelligence', 'UCS521');
INSERT INTO sae.courses (course_name, course_code) VALUES ('Software Engineering', 'UCS503');
INSERT INTO sae.courses (course_name, course_code) VALUES ('Machine Learning', 'UCS501');
INSERT INTO sae.courses (course_name, course_code) VALUES ('Theory of Computation', 'UCS701');

INSERT INTO sae.students (student_rollno, student_name) VALUES (101783015, 'Gurpreet Singh Walia');
INSERT INTO sae.students (student_rollno, student_name) VALUES (101783009, 'Barjinder Pal Singh');
INSERT INTO sae.students (student_rollno, student_name) VALUES (101610037, 'Eshaan Mangal');
INSERT INTO sae.students (student_rollno, student_name) VALUES (101611020, 'Eklavya Gehlaut');
INSERT INTO sae.students (student_rollno, student_name) VALUES (101783017, 'Harjot Singh');

INSERT INTO sae.teachers (teacher_code, teacher_name, designation, contact, email) VALUES ('500', 'Parteek Bhatia', 'Professor', '9876034500', 'parteek.bhatia@thapar.edu');
INSERT INTO sae.teachers (teacher_code, teacher_name, designation, contact, email) VALUES ('501', 'Sanmeet Bhatia', 'Professor', '9876543210', 'sanmeet.bhatia@thapar.edu');
INSERT INTO sae.teachers (teacher_code, teacher_name, designation, contact, email) VALUES ('502', 'Shreelekha Pandey', 'Professor', '9876543211', 'shreelekha.pandey@thapar.edu');
INSERT INTO sae.teachers (teacher_code, teacher_name, designation, contact, email) VALUES ('503', 'Vinay Arora', 'Professor', '9876543212', 'Vinay.arora@thapar.edu');
INSERT INTO sae.teachers (teacher_code, teacher_name, designation, contact, email) VALUES ('504', 'Maninder Singh', 'Professor', '9876543213', 'msingh@thapar.edu');

INSERT INTO sae.logins (teacher_id, password) VALUES (1, 'test123');
INSERT INTO sae.logins (teacher_id, password) VALUES (2, 'test123');
INSERT INTO sae.logins (teacher_id, password) VALUES (3, 'test123');
INSERT INTO sae.logins (teacher_id, password) VALUES (4, 'test123');
INSERT INTO sae.logins (teacher_id, password) VALUES (5, 'test123');

INSERT INTO sae.student_classes (class_id, student_id) VALUES (3, 1);
INSERT INTO sae.student_classes (class_id, student_id) VALUES (3, 2);
INSERT INTO sae.student_classes (class_id, student_id) VALUES (3, 3);
INSERT INTO sae.student_classes (class_id, student_id) VALUES (3, 4);
INSERT INTO sae.student_classes (class_id, student_id) VALUES (3, 5);

INSERT INTO sae.student_courses (student_id, course_id) VALUES (1, 1);
INSERT INTO sae.student_courses (student_id, course_id) VALUES (1, 2);
INSERT INTO sae.student_courses (student_id, course_id) VALUES (1, 3);
INSERT INTO sae.student_courses (student_id, course_id) VALUES (1, 4);
INSERT INTO sae.student_courses (student_id, course_id) VALUES (1, 5);
INSERT INTO sae.student_courses (student_id, course_id) VALUES (2, 1);
INSERT INTO sae.student_courses (student_id, course_id) VALUES (2, 2);
INSERT INTO sae.student_courses (student_id, course_id) VALUES (2, 3);
INSERT INTO sae.student_courses (student_id, course_id) VALUES (2, 4);
INSERT INTO sae.student_courses (student_id, course_id) VALUES (2, 5);
INSERT INTO sae.student_courses (student_id, course_id) VALUES (3, 1);
INSERT INTO sae.student_courses (student_id, course_id) VALUES (3, 2);
INSERT INTO sae.student_courses (student_id, course_id) VALUES (3, 3);
INSERT INTO sae.student_courses (student_id, course_id) VALUES (3, 4);
INSERT INTO sae.student_courses (student_id, course_id) VALUES (3, 5);
INSERT INTO sae.student_courses (student_id, course_id) VALUES (4, 1);
INSERT INTO sae.student_courses (student_id, course_id) VALUES (4, 2);
INSERT INTO sae.student_courses (student_id, course_id) VALUES (4, 3);
INSERT INTO sae.student_courses (student_id, course_id) VALUES (4, 4);
INSERT INTO sae.student_courses (student_id, course_id) VALUES (4, 5);
INSERT INTO sae.student_courses (student_id, course_id) VALUES (5, 1);
INSERT INTO sae.student_courses (student_id, course_id) VALUES (5, 2);
INSERT INTO sae.student_courses (student_id, course_id) VALUES (5, 3);
INSERT INTO sae.student_courses (student_id, course_id) VALUES (5, 4);
INSERT INTO sae.student_courses (student_id, course_id) VALUES (5, 5);

INSERT INTO sae.teacher_classes (teacher_id, class_id, course_id) VALUES (1, 1, 1);
INSERT INTO sae.teacher_classes (teacher_id, class_id, course_id) VALUES (1, 2, 2);
INSERT INTO sae.teacher_classes (teacher_id, class_id, course_id) VALUES (1, 3, 5);
INSERT INTO sae.teacher_classes (teacher_id, class_id, course_id) VALUES (1, 4, 4);
INSERT INTO sae.teacher_classes (teacher_id, class_id, course_id) VALUES (1, 5, 3);
INSERT INTO sae.teacher_classes (teacher_id, class_id, course_id) VALUES (1, 6, 1);
INSERT INTO sae.teacher_classes (teacher_id, class_id, course_id) VALUES (1, 7, 2);

INSERT INTO sae.examinations (course_id, exam_type, datetime, total_marks, ref_ans_sheet) VALUES (1, 'MST', '2018-09-24 13:00:00', 20, null);
INSERT INTO sae.examinations (course_id, exam_type, datetime, total_marks, ref_ans_sheet) VALUES (3, 'MST', '2018-09-25 13:00:00', 25, null);
INSERT INTO sae.examinations (course_id, exam_type, datetime, total_marks, ref_ans_sheet) VALUES (5, 'MST', '2018-09-26 13:00:00', 25, null);
INSERT INTO sae.examinations (course_id, exam_type, datetime, total_marks, ref_ans_sheet) VALUES (2, 'MST', '2018-09-28 13:00:00', 25, null);
INSERT INTO sae.examinations (course_id, exam_type, datetime, total_marks, ref_ans_sheet) VALUES (4, 'MST', '2018-09-29 13:00:00', 25, null);