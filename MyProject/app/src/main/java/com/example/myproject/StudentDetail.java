package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import static com.example.myproject.Common.Common.selItem;
import static com.example.myproject.Common.Common.selItem2;


public class StudentDetail extends AppCompatActivity {

    Button chat;
    ImageView sdetail_imageView;
    TextView et_sdetail_nickname,et_sdetail_subject,et_sdetail_addr,et_dstudent_intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        sdetail_imageView = findViewById(R.id.sdetail_imageView);
        et_sdetail_nickname = findViewById(R.id.et_sdetail_nickname);
        et_sdetail_subject = findViewById(R.id.et_sdetail_subject);
        et_sdetail_addr = findViewById(R.id.et_sdetail_addr);
        et_dstudent_intro = findViewById(R.id.et_dstudent_intro);

        Glide.with(this).load(selItem2.getStudent_image_path()).circleCrop().into(sdetail_imageView);
        et_sdetail_addr.setText(selItem2.getStudent_addr());
        et_dstudent_intro.setText(selItem2.getStudent_intro());
        et_sdetail_subject.setText(selItem2.getStudent_subject());
        et_sdetail_nickname.setText(selItem2.getStudent_nickname());



        //상담하기
        chat = findViewById(R.id.chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentDetail.this, ChatStartStudentActivity.class);

                startActivity(intent);
            }
        });


    }
}