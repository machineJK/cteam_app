package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

//4번 슬라이드
public class Slider_3 extends AppCompatActivity {

    //버튼 변수 선언!!!
    private Button join, reset, out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_3);

        //버튼들 찾아 놓기!
        join = findViewById(R.id.join);
        reset = findViewById(R.id.reset);
        out = findViewById(R.id.out);

        //가입 버튼을 눌렀을 때 발생하는 이벤트는 5번째 슬라이드로 넘어가게 하는 것...
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.myproject.Slider_3.this, Matching.class);
                startActivity(intent);
            }
        });


    }
}