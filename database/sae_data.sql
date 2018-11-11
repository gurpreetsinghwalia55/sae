INSERT INTO sae.classes (id, branch, year, sec_from, sec_to) VALUES (1, 'COE', 3, 1, 3);
INSERT INTO sae.classes (id, branch, year, sec_from, sec_to) VALUES (2, 'COE', 3, 4, 6);
INSERT INTO sae.classes (id, branch, year, sec_from, sec_to) VALUES (3, 'COE', 3, 7, 9);
INSERT INTO sae.classes (id, branch, year, sec_from, sec_to) VALUES (4, 'COE', 3, 10, 12);
INSERT INTO sae.classes (id, branch, year, sec_from, sec_to) VALUES (5, 'COE', 3, 13, 15);
INSERT INTO sae.classes (id, branch, year, sec_from, sec_to) VALUES (7, 'COE', 3, 20, 23);
INSERT INTO sae.classes (id, branch, year, sec_from, sec_to) VALUES (6, 'COE', 3, 16, 19);

INSERT INTO sae.courses (id, course_name, course_code) VALUES (1, 'Parallel and distributed computing', 'UCS608');
INSERT INTO sae.courses (id, course_name, course_code) VALUES (2, 'Advanced Data Structures', 'UCS616');
INSERT INTO sae.courses (id, course_name, course_code) VALUES (3, 'Computer Architecture', 'UCS507');
INSERT INTO sae.courses (id, course_name, course_code) VALUES (4, 'Artificial Intelligence', 'UCS521');
INSERT INTO sae.courses (id, course_name, course_code) VALUES (5, 'Software Engineering', 'UCS503');

INSERT INTO sae.examinations (id, course_id, exam_type, datetime, total_marks, ref_ans_sheet) VALUES (1, 1, 'MST', '2018-01-19 12:06:00.000000', 100, '101783015_ans_sheet');
INSERT INTO sae.examinations (id, course_id, exam_type, datetime, total_marks, ref_ans_sheet) VALUES (2, 2, 'MST', '2018-11-20 00:00:00.000000', 100, '101783009_ans_sheet');
INSERT INTO sae.examinations (id, course_id, exam_type, datetime, total_marks, ref_ans_sheet) VALUES (3, 5, 'MST', '2018-11-21 00:00:00.000000', 100, '101783017_ans_sheet');
INSERT INTO sae.examinations (id, course_id, exam_type, datetime, total_marks, ref_ans_sheet) VALUES (4, 1, 'MST', '2018-01-19 00:00:00.000000', 100, '101610027_ans_sheet');
INSERT INTO sae.examinations (id, course_id, exam_type, datetime, total_marks, ref_ans_sheet) VALUES (5, 3, 'MST', '2018-11-25 00:00:00.000000', 100, '101611020_ans_sheet');

INSERT INTO sae.logins (id, teacher_id, password) VALUES (1, 1, 'test123');
INSERT INTO sae.logins (id, teacher_id, password) VALUES (2, 2, 'test123');
INSERT INTO sae.logins (id, teacher_id, password) VALUES (3, 3, 'test123');
INSERT INTO sae.logins (id, teacher_id, password) VALUES (4, 4, 'test123');
INSERT INTO sae.logins (id, teacher_id, password) VALUES (5, 5, 'test123');

INSERT INTO sae.student_classes (id, class_id, student_id) VALUES (1, 1, 1);
INSERT INTO sae.student_classes (id, class_id, student_id) VALUES (2, 7, 2);
INSERT INTO sae.student_classes (id, class_id, student_id) VALUES (3, 3, 3);
INSERT INTO sae.student_classes (id, class_id, student_id) VALUES (4, 6, 4);
INSERT INTO sae.student_classes (id, class_id, student_id) VALUES (5, 1, 5);


INSERT INTO sae.student_courses (id, student_id, course_id) VALUES (1, 1, 1);
INSERT INTO sae.student_courses (id, student_id, course_id) VALUES (2, 1, 5);
INSERT INTO sae.student_courses (id, student_id, course_id) VALUES (3, 2, 4);
INSERT INTO sae.student_courses (id, student_id, course_id) VALUES (4, 3, 2);
INSERT INTO sae.student_courses (id, student_id, course_id) VALUES (5, 2, 2);
INSERT INTO sae.student_courses (id, student_id, course_id) VALUES (6, 3, 5);
INSERT INTO sae.student_courses (id, student_id, course_id) VALUES (7, 4, 1);
INSERT INTO sae.student_courses (id, student_id, course_id) VALUES (8, 4, 2);
INSERT INTO sae.student_courses (id, student_id, course_id) VALUES (9, 5, 1);
INSERT INTO sae.student_courses (id, student_id, course_id) VALUES (10, 5, 3);

INSERT INTO sae.students (id, student_rollno, student_name) VALUES (1, 101783015, 'Gurpreet Singh Walia');
INSERT INTO sae.students (id, student_rollno, student_name) VALUES (2, 101783009, 'Barjinder Pal Singh');
INSERT INTO sae.students (id, student_rollno, student_name) VALUES (3, 101610037, 'Eshaan Mangal');
INSERT INTO sae.students (id, student_rollno, student_name) VALUES (4, 101611020, 'Eklavya Gehlaut');
INSERT INTO sae.students (id, student_rollno, student_name) VALUES (5, 101783017, 'Harjot Singh');


INSERT INTO sae.teacher_classes (id, teacher_id, class_id, course_id) VALUES (1, 1, 7, 1);
INSERT INTO sae.teacher_classes (id, teacher_id, class_id, course_id) VALUES (2, 1, 1, 3);
INSERT INTO sae.teacher_classes (id, teacher_id, class_id, course_id) VALUES (3, 2, 2, 2);
INSERT INTO sae.teacher_classes (id, teacher_id, class_id, course_id) VALUES (4, 2, 4, 5);
INSERT INTO sae.teacher_classes (id, teacher_id, class_id, course_id) VALUES (5, 3, 3, 4);
INSERT INTO sae.teacher_classes (id, teacher_id, class_id, course_id) VALUES (6, 3, 6, 1);
INSERT INTO sae.teacher_classes (id, teacher_id, class_id, course_id) VALUES (7, 4, 5, 5);
INSERT INTO sae.teacher_classes (id, teacher_id, class_id, course_id) VALUES (8, 4, 7, 2);
INSERT INTO sae.teacher_classes (id, teacher_id, class_id, course_id) VALUES (9, 5, 2, 4);
INSERT INTO sae.teacher_classes (id, teacher_id, class_id, course_id) VALUES (10, 5, 4, 3);
INSERT INTO sae.teacher_classes (id, teacher_id, class_id, course_id) VALUES (11, 1, 6, 1);


INSERT INTO sae.teachers (id, teacher_code, teacher_name, designation, contact, email) VALUES (1, '500', 'Parteek Bhatia', 'Professor', '9876034500', 'parteek.bhatia@thapar.edu');
INSERT INTO sae.teachers (id, teacher_code, teacher_name, designation, contact, email) VALUES (2, '501', 'Sanmeet Bhatia', 'Professor', '9876543210', 'sanmeet.bhatia@thapar.edu');
INSERT INTO sae.teachers (id, teacher_code, teacher_name, designation, contact, email) VALUES (3, '502', 'Shreelekha Pandey', 'Professor', '9876543211', 'shreelekha.pandey@thapar.edu');
INSERT INTO sae.teachers (id, teacher_code, teacher_name, designation, contact, email) VALUES (4, '503', 'Vinay Arora', 'Professor', '9876543212', 'Vinay.arora@thapar.edu');
INSERT INTO sae.teachers (id, teacher_code, teacher_name, designation, contact, email) VALUES (5, '503', 'Maninder Singh', 'Professor', '9876543213', 'msingh@thapar.edu');