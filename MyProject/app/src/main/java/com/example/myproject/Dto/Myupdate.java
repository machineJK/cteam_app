package com.example.myproject.Dto;

import java.io.Serializable;

public class Myupdate implements Serializable {

    public String id, pw, nickname, name,
            gender, birth, email, image_path;

    public Myupdate(String id, String pw, String nickname, String name, String gender, String birth, String email, String image_path) {
        this.id = id;
        this.pw = pw;
        this.nickname = nickname;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.email = email;
        this.image_path = image_path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
