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

--------------------------------------------------------------------------------------------------------------------------------------

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

--------------------------------------------------------------------------------------------------------------------------------------

drop function if exists getExamEvaluationStatus;
create or replace function getExamEvaluationStatus(_tid int, _eid int)
  returns boolean as $$
declare
  ecount int;
  scount int;
  cid int;
begin

  select course_id into cid from examinations where id = _eid;

  select count(s.*) into scount
  from student_courses as s,
       courses as c,
       examinations as e,
       student_classes as sc
  where s.course_id = c.id
    and e.course_id = c.id
    and e.id = _eid
    and sc.student_id = s.student_id
    and sc.class_id in (select id from getClassesByTeacherAndCourse(_tid, 1));

  select count(ev.*) into ecount
  from evaluations as ev,
       examinations as ex
  where ex.id = ev.exam_id
    and ex.id = _eid
    and status = true;

  return ecount = scount;
end;
$$
language plpgsql
strict
immutable;

--------------------------------------------------------------------------------------------------------------------------------

drop function if exists teacherExaminationList;
create or replace function teacherExaminationList(_tid int, _len int)
  returns table(
    id     int,
    c_id   int,
    etype  varchar,
    dt     timestamp,
    tmarks int,
    rans   character varying,
    cname  varchar,
    ccode  varchar
  ) as $$
begin
  if _len = 0
  then
    return query select e.*, c.course_name, c.course_code
                 from examinations as e,
                      courses as c
                 where e.course_id = c.id
                   and course_id in (select cid from courseslist(_tid))
                 order by e.datetime desc;
  else
    return query select e.*, c.course_name, c.course_code
                 from examinations as e,
                      courses as c
                 where e.course_id = c.id
                   and course_id in (select cid from courseslist(_tid))
                 order by e.datetime desc
                 limit _len;
  end if;

end;
$$
language plpgsql;

