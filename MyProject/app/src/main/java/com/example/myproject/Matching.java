package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import static com.example.myproject.Common.Common.loginDTO;

//과외 매칭
public class Matching extends AppCompatActivity {

    Button teacher, student, add;
    ImageButton imageButton1, imageButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);
        teacher = findViewById(R.id.teacher);
        student = findViewById(R.id.student);
        add = findViewById(R.id.add);
        imageButton1 = findViewById(R.id.imageButton1);
        imageButton2 = findViewById(R.id.imageButton2);

        //과외 등록 했을 때 액티비티 넘어가는 것!!!!
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Matching.this, Slider_5.class);
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


    }
}