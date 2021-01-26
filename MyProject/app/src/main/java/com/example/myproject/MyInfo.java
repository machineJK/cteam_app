package com.example.myproject;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;


public class MyInfo extends AppCompatActivity {

    Button matching, talk, board, my, modify;
    ImageView imageView6;
    ImageButton imageButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);


        imageView6 = findViewById(R.id.imageView6);
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
                Intent intent = new Intent(MyInfo.this, Chat.class);
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