package com.example.myproject.Dto;

import java.io.Serializable;

public class StudentDTO implements Serializable {
    private String student_id,student_subject,student_grade,student_intro,
                student_image_path,student_date,student_addr,student_nickname;

    private int student_matching;

    public StudentDTO(String student_id, String student_subject,
                      String student_grade, String student_intro,
                      String student_image_path, int student_matching, String student_date,
                      String student_addr,String student_nickname) {
        this.student_id = student_id;
        this.student_subject = student_subject;
        this.student_grade = student_grade;
        this.student_intro = student_intro;
        this.student_image_path = student_image_path;
        this.student_date = student_date;
        this.student_matching = student_matching;
        this.student_addr = student_addr;
        this.student_nickname = student_nickname;
    }

    public String getStudent_nickname() {
        return student_nickname;
    }

    public void setStudent_nickname(String student_nickname) {
        this.student_nickname = student_nickname;
    }

    public String getStudent_addr() {
        return student_addr;
    }

    public void setStudent_addr(String student_addr) {
        this.student_addr = student_addr;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudent_subject() {
        return student_subject;
    }

    public void setStudent_subject(String student_subject) {
        this.student_subject = student_subject;
    }

    public String getStudent_grade() {
        return student_grade;
    }

    public void setStudent_grade(String student_grade) {
        this.student_grade = student_grade;
    }

    public String getStudent_intro() {
        return student_intro;
    }

    public void setStudent_intro(String student_intro) {
        this.student_intro = student_intro;
    }

    public String getStudent_image_path() {
        return student_image_path;
    }

    public void setStudent_image_path(String student_image_path) {
        this.student_image_path = student_image_path;
    }

    public String getStudent_date() {
        return student_date;
    }

    public void setStudent_date(String student_date) {
        this.student_date = student_date;
    }

    public int getStudent_matching() {
        return student_matching;
    }

    public void setStudent_matching(int student_matching) {
        this.student_matching = student_matching;
    }
}
