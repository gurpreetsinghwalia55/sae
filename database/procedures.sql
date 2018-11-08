--Login authentication
drop function if exists login_auth;
CREATE OR REPLACE function login_auth
  (
    username varchar(10),
    pwd      varchar(10)
  )
  RETURNS INTEGER
LANGUAGE plpgsql

AS $$

DECLARE
  status INTEGER :=0;

BEGIN

  IF EXISTS(select l.*
            from teachers t,
                 logins l
            where t.id = l.teacher_id
              and t.teacher_code = username
              and l.password = pwd)
  THEN
    status := 1;
  ELSE
    status := 0;
  END IF;

  RETURN status;
END
$$;

--Teacher Profile Information

drop function if exists profileTeacherInfo;
create or replace function profileTeacherInfo(_code varchar)
  returns table(
    tid      int,
    tcode    varchar(10),
    tname    varchar(40),
    tdes     varchar(40),
    tcontact varchar(10),
    tmail    varchar(60)
  ) as $$
begin
  return query select * from teachers where teacher_code = _code;
end;
$$
language plpgsql
strict
immutable;

--Teacher Examination list
drop function if exists teacherExaminationList;
create or replace function teacherExaminationList(_code varchar)
  returns table(
    id     integer,
    cid    integer,
    cname  varchar,
    ccode  varchar,
    etype  varchar,
    dt     timestamp,
    tmarks int
  ) as $$
begin
  return query select examinations.id,
                      examinations.course_id,
                      courses.course_name,
                      courses.course_code,
                      examinations.exam_type,
                      examinations.datetime,
                      examinations.total_marks
               from (((teachers INNER JOIN teacher_classes ON teachers.id = teacher_classes.id) INNER JOIN courses ON
                 courses.id = teacher_classes.id) INNER JOIN examinations ON courses.id = examinations.course_id)
               where teachers.teacher_code = _code;
end;
$$
language plpgsql;

-- for insertion and checking
select *
from teacherExaminationList('500');

--------------------------------------------------------------------------------------------------------------------------------

drop function if exists coursesList;
create or replace function coursesList(_id int)
  returns table(
    cid         int,
    course_name varchar,
    course_code varchar
  )
as $$
begin
  return query select *
               from courses
               where id in (select distinct course_id from teacher_classes where teacher_id = _id);
end;
$$
language plpgsql;

--------------------------------------------------------------------------------------------------------------------------------------

drop function if exists getClassesByTeacherAndCourse;
create or replace function getClassesByTeacherAndCourse(_tid int, _cid int)
  returns table(
    id       int,
    branch   varchar,
    year     int,
    sec_from int,
    sec_to   int
  )
as $$
begin
  return query select c.*
               from teacher_classes as tc,
                    classes as c
               where teacher_id = _tid
                 and course_id = _cid
                 and tc.class_id = c.id;
end;
$$
language plpgsql
strict
immutable;