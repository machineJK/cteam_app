package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

//과외 매칭
public class Matching extends AppCompatActivity {

    Button teacher, student, add, matching, talk, board, my;
    ImageButton imageButton1, imageButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);
        teacher = findViewById(R.id.teacher2);
        student = findViewById(R.id.student);
        add = findViewById(R.id.add2);
        imageButton1 = findViewById(R.id.imageButton1);
        imageButton2 = findViewById(R.id.imageButton2);

        //과외 등록 했을 때 액티비티 넘어가는 것!!!!
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Matching.this, TeacherForm.class);
                startActivity(intent);
            }
        });

        //누르면 선생님 상세 정보로 이동합니다.
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Matching.this, Slider_6.class);
                startActivity(intent);
            }
        });

/*        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Slider_4.this, Slider_6.class);
                startActivity(intent);
            }
        });*/

        //학생 등록 화면 이동
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Matching.this, MatchingStudent.class);
                startActivity(intent);
            }
        });
        
        
        //화면이동
        matching = findViewById(R.id.matchingst_matching);
        talk = findViewById(R.id.matchingst_talk);
        board = findViewById(R.id.matchingst_board);
        my = findViewById(R.id.matchingst_my);
        
        talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Matching.this, Chat.class);
                startActivity(intent);
            }
        });

        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Matching.this, Board.class);
                startActivity(intent);
            }
        });

        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Matching.this, MyInfo.class);
                startActivity(intent);
            }
        });
        
    }
}