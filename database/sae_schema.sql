drop table if exists "logins";
CREATE TABLE "logins" (
	"id" serial NOT NULL,
	"teacher_id" integer NOT NULL,
	"password" VARCHAR(10) NOT NULL,
	CONSTRAINT logins_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);


drop table if exists "teachers";
CREATE TABLE "teachers" (
	"id" serial NOT NULL UNIQUE,
	"teacher_code" VARCHAR(10) NOT NULL,
	"teacher_name" VARCHAR(40) NOT NULL,
	"designation" VARCHAR(40) NOT NULL,
	"contact" varchar(10) NOT NULL UNIQUE,
	"email" VARCHAR(60) NOT NULL UNIQUE,
	CONSTRAINT teachers_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);


drop table if exists "courses";
CREATE TABLE "courses" (
	"id" serial NOT NULL UNIQUE,
	"course_name" VARCHAR(60) NOT NULL,
	"course_code" VARCHAR(7) NOT NULL UNIQUE,
	CONSTRAINT courses_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



drop table if exists "classes";
CREATE TABLE "classes" (
	"id" serial NOT NULL,
	"branch" VARCHAR(5) NOT NULL,
	"year" integer NOT NULL,
	"sec_from" integer NOT NULL,
	"sec_to" integer NOT NULL,
	CONSTRAINT classes_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);


drop table if exists "teacher_classes";
CREATE TABLE "teacher_classes" (
	"id" serial NOT NULL,
	"teacher_id" integer NOT NULL,
	"class_id" integer NOT NULL,
  "course_id" integer NOT NULL,
	CONSTRAINT teacher_classes_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);


drop table if exists "students";
CREATE TABLE "students" (
	"id" serial NOT NULL,
	"student_rollno" integer NOT NULL UNIQUE,
	"student_name" VARCHAR(50) NOT NULL,
	CONSTRAINT students_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);


drop table if exists "student_courses";
CREATE TABLE "student_courses" (
	"id" serial NOT NULL,
	"student_id" integer NOT NULL UNIQUE,
	"course_id" integer NOT NULL UNIQUE,
	CONSTRAINT student_courses_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);


drop table if exists "student_classes";
CREATE TABLE "student_classes" (
	"id" serial NOT NULL UNIQUE,
	"class_id" integer NOT NULL,
	"student_id" integer NOT NULL,
	CONSTRAINT student_classes_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);


drop table if exists "examinations";
CREATE TABLE "examinations" (
	"id" serial NOT NULL,
	"course_id" integer NOT NULL,
	"exam_type" VARCHAR(10) NOT NULL,
	"datetime" TIMESTAMP NOT NULL,
	"total_marks" integer NOT NULL,
	"ref_ans_sheet" VARCHAR(100),
	CONSTRAINT examinations_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);


drop table if exists "evaluations";
CREATE TABLE "evaluations" (
	"id" serial NOT NULL,
	"exam_id" integer NOT NULL,
	"teacher_id" integer NOT NULL,
	"student_id" integer NOT NULL,
	"marks_obtained" integer NOT NULL,
	"datetime" TIMESTAMP NOT NULL,
	"ans_sheet" text NOT NULL,
  "status" boolean NOT NULL,
	CONSTRAINT evaluations_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



ALTER TABLE "logins" ADD CONSTRAINT "logins_fk0" FOREIGN KEY ("teacher_id") REFERENCES "teachers"("id");

ALTER TABLE "teacher_classes" ADD CONSTRAINT "teacher_classes_fk0" FOREIGN KEY ("teacher_id") REFERENCES "teachers"("id");
ALTER TABLE "teacher_classes" ADD CONSTRAINT "teacher_classes_fk1" FOREIGN KEY ("class_id") REFERENCES "classes"("id");


ALTER TABLE "student_courses" ADD CONSTRAINT "student_courses_fk0" FOREIGN KEY ("student_id") REFERENCES "students"("id");
ALTER TABLE "student_courses" ADD CONSTRAINT "student_courses_fk1" FOREIGN KEY ("course_id") REFERENCES "courses"("id");

ALTER TABLE "student_classes" ADD CONSTRAINT "student_classes_fk0" FOREIGN KEY ("class_id") REFERENCES "classes"("id");
ALTER TABLE "student_classes" ADD CONSTRAINT "student_classes_fk1" FOREIGN KEY ("student_id") REFERENCES "students"("id");

ALTER TABLE "examinations" ADD CONSTRAINT "examinations_fk0" FOREIGN KEY ("course_id") REFERENCES "courses"("id");

ALTER TABLE "evaluations" ADD CONSTRAINT "evaluations_fk0" FOREIGN KEY ("exam_id") REFERENCES "examinations"("id");
ALTER TABLE "evaluations" ADD CONSTRAINT "evaluations_fk1" FOREIGN KEY ("teacher_id") REFERENCES "teachers"("id");
ALTER TABLE "evaluations" ADD CONSTRAINT "evaluations_fk2" FOREIGN KEY ("student_id") REFERENCES "students"("id");

ALTER TABLE "student_courses" DROP CONSTRAINT "student_courses_student_id_key";
ALTER TABLE "student_courses" DROP CONSTRAINT "student_courses_course_id_key";

ALTER TABLE "teacher_classes" ADD CONSTRAINT "teacher_classes_fk_course" FOREIGN KEY ("course_id") REFERENCES "courses"("id");

ALTER TABLE "teachers" ADD CONSTRAINT "teachers_uk_code" UNIQUE ("teacher_code");