package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class TeacherForm extends AppCompatActivity {
    Button join, cancel;
    Spinner spinner_teacher_location, spinner_teacher_subject;
    EditText et_teacher_univ,et_teacher_major,et_teacher_univNum,
            et_teacher_pay,et_teacher_worktime,et_teacher_intro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_form);

        spinner_teacher_location = findViewById(R.id.spinner_teacher_location);
        spinner_teacher_subject = findViewById(R.id.spinner_teacher_subject);

        ArrayAdapter locationAdapter = ArrayAdapter.createFromResource(this,R.array.tLocation,
                R.layout.spinner_design);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_teacher_location.setAdapter(locationAdapter);

        ArrayAdapter subjectAdapter = ArrayAdapter.createFromResource(this,R.array.tSubject,
                R.layout.spinner_design);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_teacher_subject.setAdapter(subjectAdapter);


        et_teacher_univ = findViewById(R.id.et_teacher_univ);
        et_teacher_major = findViewById(R.id.et_teacher_major);
        et_teacher_univNum = findViewById(R.id.et_teacher_univNum);
        et_teacher_pay = findViewById(R.id.et_teacher_pay);
        et_teacher_worktime = findViewById(R.id.et_teacher_worktime);
        et_teacher_intro = findViewById(R.id.et_teacher_intro);











        //메인 화면이동
        join = findViewById(R.id.teacher_join);
        cancel = findViewById(R.id.teacher_cancel);

        //선생 등록 완료(Matching.java 로 이동)
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherForm.this, Matching.class);
                startActivity(intent);
            }
        });

        //취소
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}