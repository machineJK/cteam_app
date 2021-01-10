package com.example.myproject.MemberDTO;

public class MemberDTO {
    String member_id,member_pw, member_nickname, member_name,
            member_gender, member_birth, member_email, member_picture;

    public MemberDTO(String member_id, String member_pw, String member_nickname, String member_name,
                     String member_gender, String member_birth, String member_email, String member_picture) {
        this.member_id = member_id;
        this.member_pw = member_pw;
        this.member_nickname = member_nickname;
        this.member_name = member_name;
        this.member_gender = member_gender;
        this.member_birth = member_birth;
        this.member_email = member_email;
        this.member_picture = member_picture;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_pw() {
        return member_pw;
    }

    public void setMember_pw(String member_pw) {
        this.member_pw = member_pw;
    }

    public String getMember_nickname() {
        return member_nickname;
    }

    public void setMember_nickname(String member_nickname) {
        this.member_nickname = member_nickname;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_gender() {
        return member_gender;
    }

    public void setMember_gender(String member_gender) {
        this.member_gender = member_gender;
    }

    public String getMember_birth() {
        return member_birth;
    }

    public void setMember_birth(String member_birth) {
        this.member_birth = member_birth;
    }

    public String getMember_email() {
        return member_email;
    }

    public void setMember_email(String member_email) {
        this.member_email = member_email;
    }

    public String getMember_picture() {
        return member_picture;
    }

    public void setMember_picture(String member_picture) {
        this.member_picture = member_picture;
    }
}
