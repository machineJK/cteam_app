package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myproject.Atask.TeacherInsert;

import java.util.concurrent.ExecutionException;

import static com.example.myproject.Common.Common.loginDTO;

public class TeacherForm extends AppCompatActivity {
    String state;

    Button join, cancel;
    Spinner spinner_teacher_subject;
    EditText et_teacher_univ,et_teacher_major,et_teacher_univNum,
            et_teacher_pay,et_teacher_worktime,et_teacher_intro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_form);


        spinner_teacher_subject = findViewById(R.id.spinner_teacher_subject);

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
                if(et_teacher_univ.getText().toString().length() == 0){
                    Toast.makeText(TeacherForm.this, "대학교를 작성하세요!!!", Toast.LENGTH_SHORT).show();
                    et_teacher_univ.requestFocus();
                    return;
                }
                if(et_teacher_major.getText().toString().length() == 0){
                    Toast.makeText(TeacherForm.this, "전공을 작성하세요!!!", Toast.LENGTH_SHORT).show();
                    et_teacher_major.requestFocus();
                    return;
                }
                if(et_teacher_univNum.getText().toString().length() == 0){
                    Toast.makeText(TeacherForm.this, "학번을 작성하세요!!!", Toast.LENGTH_SHORT).show();
                    et_teacher_univNum.requestFocus();
                    return;
                }
                if(et_teacher_pay.getText().toString().length() == 0){
                    Toast.makeText(TeacherForm.this, "페이를 작성하세요!!!", Toast.LENGTH_SHORT).show();
                    et_teacher_pay.requestFocus();
                    return;
                }
                if(et_teacher_worktime.getText().toString().length() == 0){
                    Toast.makeText(TeacherForm.this, "시간을 작성하세요!!!", Toast.LENGTH_SHORT).show();
                    et_teacher_worktime.requestFocus();
                    return;
                }
                if(et_teacher_intro.getText().toString().length() == 0){
                    Toast.makeText(TeacherForm.this, "소개글을 작성하세요!!!", Toast.LENGTH_SHORT).show();
                    et_teacher_intro.requestFocus();
                    return;
                }

                if(loginDTO == null){
                    Toast.makeText(TeacherForm.this, "로그인을 해주세요!!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TeacherForm.this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    String teacher_id = loginDTO.getId();
                    String teacher_univ = et_teacher_univ.getText().toString();
                    String teacher_major = et_teacher_major.getText().toString();
                    String teacher_univNum = et_teacher_univNum.getText().toString();
                    String teacher_subject = spinner_teacher_subject.getSelectedItem().toString();
                    String teacher_worktime = et_teacher_worktime.getText().toString();
                    String teacher_pay = et_teacher_pay.getText().toString();
                    String teacher_intro = et_teacher_intro.getText().toString();
                    String teacher_image_path = loginDTO.getdbImgPath();

                    TeacherInsert teacherInsert = new TeacherInsert(teacher_id, teacher_univ, teacher_major,
                            teacher_univNum, teacher_subject, teacher_worktime, teacher_pay, teacher_intro,
                            teacher_image_path);
                    try {
                        state = teacherInsert.execute().get().trim();
                        Log.d("main:joinact0 : ", state);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(state.equals("1")){
                        Toast.makeText(TeacherForm.this, "삽입성공 !!!", Toast.LENGTH_SHORT).show();
                        Log.d("main:joinact", "삽입성공 !!!");
                        finish();
                    }else{
                        Toast.makeText(TeacherForm.this, "삽입실패 !!!", Toast.LENGTH_SHORT).show();
                        Log.d("main:joinact", "삽입실패 !!!");
                        finish();
                    }


                }


                /*Intent intent = new Intent(TeacherForm.this, Matching.class);
                startActivity(intent);*/
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