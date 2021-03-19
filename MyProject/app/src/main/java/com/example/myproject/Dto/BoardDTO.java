package com.example.myproject.Dto;

public class BoardDTO {
    String board_id, board_nickname, board_content, board_write_date;
    int board_readcount;
    String board_image_path, id_image_path ;
    int board_notice, qna_ref_num;
    //board_notice : 공지사항:0, QnA:1
    //qna_ref_num : Question의 board_num
    public BoardDTO(){}
    public BoardDTO(String board_id, String board_nickname,
                    String board_content, String board_write_date, int board_readcount,
                    String board_image_path, int board_notice, int qna_ref_num, String id_image_path) {
        this.board_id = board_id;
        this.board_nickname = board_nickname;
        this.board_content = board_content;
        this.board_write_date = board_write_date;
        this.board_readcount = board_readcount;
        this.board_image_path = board_image_path;
        this.board_notice = board_notice;
        this.qna_ref_num = qna_ref_num;
        this.id_image_path = id_image_path;
    }


    public String getId_image_path() {
        return id_image_path;
    }

    public void setId_image_path(String id_image_path) {
        this.id_image_path = id_image_path;
    }

    public String getBoard_id() {
        return board_id;
    }

    public void setBoard_id(String board_id) {
        this.board_id = board_id;
    }

    public String getBoard_nickname() {
        return board_nickname;
    }

    public void setBoard_nickname(String board_nickname) {
        this.board_nickname = board_nickname;
    }

    public String getBoard_content() {
        return board_content;
    }

    public void setBoard_content(String board_content) {
        this.board_content = board_content;
    }

    public String getBoard_write_date() {
        return board_write_date;
    }

    public void setBoard_write_date(String board_write_date) {
        this.board_write_date = board_write_date;
    }

    public int getBoard_readcount() {
        return board_readcount;
    }

    public void setBoard_readcount(int board_readcount) {
        this.board_readcount = board_readcount;
    }

    public String getBoard_image_path() {
        return board_image_path;
    }

    public void setBoard_image_path(String board_image_path) {
        this.board_image_path = board_image_path;
    }

    public int getBoard_notice() {
        return board_notice;
    }

    public void setBoard_notice(int board_notice) {
        this.board_notice = board_notice;
    }

    public int getQna_ref_num() {
        return qna_ref_num;
    }

    public void setQna_ref_num(int qna_ref_num) {
        this.qna_ref_num = qna_ref_num;
    }
}
