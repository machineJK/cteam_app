package com.example.myproject.Dto;

import java.io.Serializable;
import java.sql.Date;

public class TeacherDTO implements Serializable {
    private String teacher_id, teacher_univ, teacher_major, teacher_univnum,
            teacher_subject, teacher_worktime, teacher_pay, teacher_intro, teacher_image_path;
    private int teacher_matching;
    private Date teacher_date;

    public TeacherDTO(String teacher_id, String teacher_univ, String teacher_major,
                      String teacher_univnum, String teacher_subject, String teacher_worktime,
                      String teacher_pay, String teacher_intro, int teacher_matching,
                      Date teacher_date, String teacher_image_path) {
        this.teacher_id = teacher_id;
        this.teacher_univ = teacher_univ;
        this.teacher_major = teacher_major;
        this.teacher_univnum = teacher_univnum;
        this.teacher_subject = teacher_subject;
        this.teacher_worktime = teacher_worktime;
        this.teacher_pay = teacher_pay;
        this.teacher_intro = teacher_intro;
        this.teacher_matching = teacher_matching;
        this.teacher_date = teacher_date;
        this.teacher_image_path = teacher_image_path;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getTeacher_univ() {
        return teacher_univ;
    }

    public void setTeacher_univ(String teacher_univ) {
        this.teacher_univ = teacher_univ;
    }

    public String getTeacher_major() {
        return teacher_major;
    }

    public void setTeacher_major(String teacher_major) {
        this.teacher_major = teacher_major;
    }

    public String getTeacher_univnum() {
        return teacher_univnum;
    }

    public void setTeacher_univnum(String teacher_univnum) {
        this.teacher_univnum = teacher_univnum;
    }

    public String getTeacher_subject() {
        return teacher_subject;
    }

    public void setTeacher_subject(String teacher_subject) {
        this.teacher_subject = teacher_subject;
    }

    public String getTeacher_worktime() {
        return teacher_worktime;
    }

    public void setTeacher_worktime(String teacher_worktime) {
        this.teacher_worktime = teacher_worktime;
    }

    public String getTeacher_pay() {
        return teacher_pay;
    }

    public void setTeacher_pay(String teacher_pay) {
        this.teacher_pay = teacher_pay;
    }

    public String getTeacher_intro() {
        return teacher_intro;
    }

    public void setTeacher_intro(String teacher_intro) {
        this.teacher_intro = teacher_intro;
    }

    public int getTeacher_matching() {
        return teacher_matching;
    }

    public void setTeacher_matching(int teacher_matching) {
        this.teacher_matching = teacher_matching;
    }

    public Date getTeacher_date() {
        return teacher_date;
    }

    public void setTeacher_date(Date teacher_date) {
        this.teacher_date = teacher_date;
    }

    public String getTeacher_image_path() {
        return teacher_image_path;
    }

    public void setTeacher_image_path(String teacher_image_path) {
        this.teacher_image_path = teacher_image_path;
    }
}
