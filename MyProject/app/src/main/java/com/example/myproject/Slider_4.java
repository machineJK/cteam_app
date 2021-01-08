package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;


//5번 슬라이드
public class Slider_4 extends AppCompatActivity {

    Button teacher, student, add;
    ImageButton imageButton1, imageButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_4);

        teacher = findViewById(R.id.teacher);
        student = findViewById(R.id.student);
        add = findViewById(R.id.add);
        imageButton1 = findViewById(R.id.imageButton1);
        imageButton2 = findViewById(R.id.imageButton2);

        //과외 등록 했을 때 액티비티 넘어가는 것!!!!
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.gwangun_project.Slider_4.this, Slider_5.class);
                startActivity(intent);
            }
        });

        //누르면 선생님 상세 정보로 이동합니다.
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.gwangun_project.Slider_4.this, Slider_6.class);
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


    }
}