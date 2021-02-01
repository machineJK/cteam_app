package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Board extends AppCompatActivity {
    Button matching, talk, board, my, writeform;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        writeform = findViewById(R.id.board_writeform);
        writeform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Board.this, BoardWriteForm.class);
                startActivity(intent);
            }
        });
        
        
        
        
        
        //화면 이동
        matching = findViewById(R.id.board_matching);
        talk = findViewById(R.id.board_talk);
        board = findViewById(R.id.board_board);
        my = findViewById(R.id.board_my);

        matching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Board.this, Matching.class);
                startActivity(intent);
            }
        });

        talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Board.this, ChatListActivity.class);
                startActivity(intent);
            }
        });

        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Board.this, MyInfo.class);
                startActivity(intent);
            }
        });

    }
}