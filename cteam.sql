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

INSERT INTO member VALUES('ska907','0000','���ֹٶ��','������','����','950106','wai95@naver.com','R.drawble.123');

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

INSERT INTO student VALUES('ska907','���� �ϱ�','����,����','��3','�ȳ��ϼ��� �ݰ����ϴ� ���� �������Դϴ�.');

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

INSERT INTO teacher VALUES('ska907','���� �ϱ�','������б�','������а�',
                    '20131217','��2ȸ 2�ð� 20����','�ȳ��ϼ��� �ݰ����ϴ� ���� �������Դϴ�.',
                    '����,����','Ŀ��ŧ��Ŀ��ŧ��Ŀ��ŧ��Ŀ��ŧ��Ŀ��ŧ��');

SELECT * FROM teacher;






