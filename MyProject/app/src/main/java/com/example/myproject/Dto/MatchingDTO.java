package com.example.myproject.Dto;

import java.io.Serializable;

public class MatchingDTO implements Serializable {
    private String teacher_id,student_id,teacher_value,student_value,admin_value,teacher_nickname,student_nickname;

    public MatchingDTO(String teacher_id, String student_id, String teacher_value,
                       String student_value, String admin_value,String teacher_nickname,String student_nickname) {
        this.teacher_id = teacher_id;
        this.student_id = student_id;
        this.teacher_value = teacher_value;
        this.student_value = student_value;
        this.admin_value = admin_value;
        this.teacher_nickname = teacher_nickname;
        this.student_nickname = student_nickname;
    }

    public String getTeacher_nickname() {
        return teacher_nickname;
    }

    public void setTeacher_nickname(String teacher_nickname) {
        this.teacher_nickname = teacher_nickname;
    }

    public String getStudent_nickname() {
        return student_nickname;
    }

    public void setStudent_nickname(String student_nickname) {
        this.student_nickname = student_nickname;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getTeacher_value() {
        return teacher_value;
    }

    public void setTeacher_value(String teacher_value) {
        this.teacher_value = teacher_value;
    }

    public String getStudent_value() {
        return student_value;
    }

    public void setStudent_value(String student_value) {
        this.student_value = student_value;
    }

    public String getAdmin_value() {
        return admin_value;
    }

    public void setAdmin_value(String admin_value) {
        this.admin_value = admin_value;
    }
}
