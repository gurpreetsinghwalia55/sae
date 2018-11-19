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
  c_row  classes%rowtype;
  status boolean;
begin

  for c_row in select * from getClassesByTeacherAndExam(_tid, _eid) loop
    select getClassEvaluationStatus(c_row.id, _eid) into status;
    if status = false
    then
      return false;
    end if;
  end loop;

  return true;
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

--------------------------------------------------------------------------------------------------------------------------------

drop function if exists getStudentsByClass;
create or replace function getStudentsByClass(_cid int)
  returns table(
    id     int,
    rollno integer,
    name   varchar
  )
as $$
begin
  return query select s.*
               from students s
                      inner join student_classes sc on s.id = sc.student_id
               where sc.class_id = _cid;
end;
$$
language plpgsql
strict
immutable;

--------------------------------------------------------------------------------------------------------------------------------

drop function if exists getStudentEvaluationDetail;
create or replace function getStudentEvaluationDetail(_sid int, _eid int)
  returns table(
    id             int,
    exam_id        int,
    teacher_id     int,
    student_id     int,
    marks_obtained int,
    datetime       timestamp,
    ans_sheet      text,
    status         boolean,
    total_marks    int
  )
as $$
begin
  return query select ev.*, ex.total_marks
               from evaluations ev,
                    examinations ex
               where ev.exam_id = ex.id
                 and ex.id = _eid
                 and ev.student_id = _sid;
end;
$$
language plpgsql
strict
immutable;

--------------------------------------------------------------------------------------------------------------------------------

drop function if exists getClassEvaluationStatus;
create or replace function getClassEvaluationStatus(_cid int, _eid int)
  returns boolean as $$
declare
  s_row students%rowtype;
begin
  for s_row in select s.*
               from students s
                      inner join student_classes sc on s.id = sc.student_id
               where sc.class_id = _cid loop
    if not exists(select * from evaluations where exam_id = _eid
                                              and student_id = s_row.id)
    then
      return false;
    end if;
  end loop;

  return true;
end;
$$
language plpgsql
strict
immutable;

--------------------------------------------------------------------------------------------------------------------------------------

drop function if exists getClassesByTeacherAndExam;
create or replace function getClassesByTeacherAndExam(_tid int, _eid int)
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
                    classes as c,
                    examinations as e
               where tc.class_id = c.id
                 and e.course_id = tc.course_id
                 and tc.teacher_id = _tid
                 and e.id = _eid;
end;
$$
language plpgsql
strict
immutable;

--------------------------------------------------------------------------------------------------------------------------------------

drop function if exists addReferenceAnswerSheet;
create or replace function addReferenceAnswerSheet(_eid int, _file text)
  returns text as $$
begin
  update examinations set ref_ans_sheet = _file where id = _eid;
  return _file;
end;
$$
language plpgsql
security definer;

--------------------------------------------------------------------------------------------------------------------------------------

drop function if exists addStudentAnswerSheet;
create or replace function addStudentAnswerSheet(_eid int, _file text)
  returns text as $$
begin
  update evaluations set ans_sheet = _file where id = _eid;
  return _file;
end;
$$
language plpgsql
security definer;

--------------------------------------------------------------------------------------------------------------------------------------

drop function if exists createEvaluation;
create or replace function createEvaluation(
  _eid int,
  _tid int,
  _sid int,
  _m   int,
  _d   timestamp,
  _ans text
)
  returns boolean as $$
begin
  insert into evaluations (exam_id, teacher_id, student_id, marks_obtained, datetime, ans_sheet, status)
  values (_eid, _tid, _sid, _m, _d, _ans, true);
  return true;
end;
$$
language plpgsql
security definer;