package com.example.myproject;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static com.example.myproject.Common.Common.loginDTO;


public class MyInfo extends AppCompatActivity {

    Button modify, btnLogout;
    ImageButton matching, talk, board, my;
    ImageView imageView6;
    ImageButton imageButton;
    TextView my_nickname;
    String nickname;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        my_nickname = findViewById(R.id.my_nickname);
        nickname = loginDTO.getNickname();
        my_nickname.setText(nickname);


        imageView6 = findViewById(R.id.imageView6);
        Glide.with(this).load(loginDTO.getdbImgPath()).circleCrop().into(imageView6);
        modify = findViewById(R.id.my_modify);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInfo.this, ModifyMyInfo.class);
                startActivity(intent);
            }
        });
        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInfo.this, TeacherDetail.class);
                startActivity(intent);
            }
        });
        
        //로그아웃
        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginDTO = null;
                Intent intent = new Intent(MyInfo.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });



        
        //화면이동
        matching = findViewById(R.id.my_matching);
        talk = findViewById(R.id.my_talk);
        board = findViewById(R.id.my_board);
        my = findViewById(R.id.my_my);

        matching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInfo.this, Matching.class);
                startActivity(intent);
            }
        });

        talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInfo.this, ChatListActivity.class);
                startActivity(intent);
            }
        });

        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInfo.this, Board.class);
                startActivity(intent);
            }
        });


    }
}