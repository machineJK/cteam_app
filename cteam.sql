CREATE TABLE member(
    member_id VARCHAR2(50) PRIMARY KEY NOT NULL,
    member_pw VARCHAR2(50) NOT NULL,
    member_nickname VARCHAR2(30) NOT NULL,
    member_name VARCHAR2(30) NOT NULL,
    member_gender VARCHAR2(20) NOT NULL,
    member_birth VARCHAR2(30) NOT NULL,
    member_email VARCHAR2(30) NOT NULL,
    member_picture VARCHAR2(50) NOT NULL
);

DROP TABlE member;

INSERT INTO member VALUES('ska907','0000','소주바라기','김종건','남자','950106','wai95@naver.com','R.drawble.123');

SELECT * FROM member;

-------------------------------------------------
CREATE TABLE student(
    student_id VARCHAR(50) PRIMARY KEY NOT NULL,
    student_addr VARCHAR2(30) NOT NULL,
    student_subject VARCHAR2(50) NOT NULL,
    student_school VARCHAR2(30) NOT NULL,
    student_intro VARCHAR2(300) NOT NULL
);

DROP TABlE student;

INSERT INTO student VALUES('ska907','광주 북구','과학,수학','초3','안녕하세요 반갑습니다 저는 김종건입니다.');

SELECT * FROM student;
----------------------------------------------------
CREATE TABLE teacher(
    teacher_id VARCHAR2(50) PRIMARY KEY NOT NULL,
    teacher_addr VARCHAR2(30) NOT NULL,
    teacher_univ VARCHAR2(30) NOT NULL,
    teacher_major VARCHAR2(30) NOT NULL,
    teacher_univNum VARCHAR2(30) NOT NULL,
    teacher_payWorkTime VARCHAR2(50) NOT NULL,
    teacher_intro VARCHAR2(300) NOT NULL,
    teacher_subject VARCHAR2(50) NOT NULL,
    teacher_curriculun VARCHAR2(1000) NOT NULL
);

DROP TABlE teacher;

INSERT INTO teacher VALUES('ska907','광주 북구','서울대학교','생명공학과',
                    '20131217','주2회 2시간 20만원','안녕하세요 반갑습니다 저는 김종건입니다.',
                    '과학,수학','커리큘럼커리큘럼커리큘럼커리큘럼커리큘럼');

SELECT * FROM teacher;






