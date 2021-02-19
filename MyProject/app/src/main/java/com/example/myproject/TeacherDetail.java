package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import static com.example.myproject.Common.Common.loginDTO;
import static com.example.myproject.Common.Common.selItem;

public class TeacherDetail extends AppCompatActivity {
    TextView teacher_nickname,teacher_univ,teacher_subject,
            teacher_addr,teacher_pay_worktime,teacher_intro;
    ImageView teacher_picture;
    Button chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_detail);

        //Toast.makeText(this, "" + selItem.getTeacher_id(), Toast.LENGTH_SHORT).show();
        teacher_nickname = findViewById(R.id.et_tdetail_nickname);
        teacher_univ = findViewById(R.id.et_tdetail_univ);
        teacher_subject = findViewById(R.id.et_tdetail_subject);
        teacher_addr = findViewById(R.id.et_tdetail_addr);
        teacher_pay_worktime = findViewById(R.id.et_tdetail_paywork);
        teacher_intro = findViewById(R.id.et_dteacher_intro);
        teacher_picture = findViewById(R.id.tdetail_imageView);

        teacher_nickname.setText(selItem.getTeacher_nickname());
        teacher_univ.setText(selItem.getTeacher_univ());
        teacher_subject.setText(selItem.getTeacher_subject());
        teacher_addr.setText(selItem.getTeacher_addr());
        teacher_pay_worktime.setText(selItem.getTeacher_pay() + " " + selItem.getTeacher_worktime());
        teacher_intro.setText(selItem.getTeacher_intro());
        Glide.with(this).load(selItem.getTeacher_image_path()).circleCrop().into(teacher_picture);


        //상담하기
        chat = findViewById(R.id.btn_goChat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeacherDetail.this, ChatStartActivity.class);
                startActivity(intent);
            }
        });

    }
}