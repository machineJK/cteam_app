package com.example.myproject.Dto;

public class MemberDTO {
    String id, pw, nickname, name, gender,
            birth, email, addr1, addr2, dbImgPath;
    //소셜로그인 정보 임시 저장용
    public MemberDTO(){}

    //회원가입
    public MemberDTO(String id, String pw, String nickname, String name, String gender,
                     String birth, String email, String addr1, String addr2, String dbImgPath) {
        this.id = id;
        this.pw = pw;
        this.nickname = nickname;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.email = email;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.dbImgPath = dbImgPath;
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

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getdbImgPath() {
        return dbImgPath;
    }

    public void setdbImgPath(String dbImgPath) {
        this.dbImgPath = dbImgPath;
    }
}