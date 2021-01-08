package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

//5번 슬라이드
public class Slider_5 extends AppCompatActivity {

    //가입버튼, 취소버튼
    Button goJoin, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_5);

        goJoin = findViewById(R.id.goJoin);
        cancel = findViewById(R.id.cancel);

        //가입버튼 누르면!
        goJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.gwangun_project.Slider_5.this, Slider_5_1.class);
                startActivity(intent);
            }
        });


        //등록 취소 버튼 누르면 메인화면으로 가도록:)
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.gwangun_project.Slider_5.this, com.example.gwangun_project.Slider_4.class);
                startActivity(intent);
            }
        });
    }
}