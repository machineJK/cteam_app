package com.example.myproject.Dto;

import java.io.Serializable;

public class Myupdate implements Serializable {

    public String pw, nickname, email, image_path;

    public Myupdate(String pw, String nickname, String email, String image_path) {
        this.pw = pw;
        this.nickname = nickname;
        this.email = email;
        this.image_path = image_path;
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
