package com.example.myproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.Atask.StudentInsert;

import java.util.concurrent.ExecutionException;

import static com.example.myproject.Common.Common.loginDTO;

public class StudentForm extends AppCompatActivity {
    String state;

    Button join, cancel;
    Spinner spinner_student_subject,spinner_student_grade;
    EditText et_student_intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.cstudent_f);

        spinner_student_subject = findViewById(R.id.spinner_student_subject);
        spinner_student_grade = findViewById(R.id.spinner_student_grade);

        ArrayAdapter subjectAdapter = ArrayAdapter.createFromResource(this,R.array.tSubject,
                R.layout.spinner_design);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_student_subject.setAdapter(subjectAdapter);

        ArrayAdapter gradeAdapter = ArrayAdapter.createFromResource(this,R.array.grade,
                R.layout.spinner_design);
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_student_grade.setAdapter(gradeAdapter);


        et_student_intro = findViewById(R.id.et_student_intro);



        join = findViewById(R.id.student_join);
        cancel = findViewById(R.id.student_cancel);
        
        //등록
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_student_intro.getText().toString().length() == 0){
                    Toast.makeText(StudentForm.this, "소개글을 작성하세요!!!", Toast.LENGTH_SHORT).show();
                    et_student_intro.requestFocus();
                    return;
                }

                if(loginDTO == null){
                    Toast.makeText(StudentForm.this, "로그인을 해주세요!!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(StudentForm.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    String student_id = loginDTO.getId();
                    String student_nickname = loginDTO.getNickname();
                    String student_subject = spinner_student_subject.getSelectedItem().toString();
                    String student_grade = spinner_student_grade.getSelectedItem().toString();
                    String student_intro = et_student_intro.getText().toString();
                    String student_image_path = loginDTO.getdbImgPath();
                    String student_addr = loginDTO.getAddr1() + " " + loginDTO.getAddr2();

                    StudentInsert studentInsert = new StudentInsert(student_id,student_subject,
                            student_grade,student_intro,student_image_path,student_addr,student_nickname);

                    try {
                        state = studentInsert.execute().get().trim();
                        Log.d("main:joinact0 : ", state);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(state.equals("1")){
                        Toast.makeText(StudentForm.this, "삽입성공 !!!", Toast.LENGTH_SHORT).show();
                        Log.d("main:joinact", "삽입성공 !!!");
                        Intent intent = new Intent(StudentForm.this, MatchingStudent.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(StudentForm.this, "삽입실패 !!!", Toast.LENGTH_SHORT).show();
                        Log.d("main:joinact", "삽입실패 !!!");
                        Intent intent = new Intent(StudentForm.this, MatchingStudent.class);
                        startActivity(intent);
                        finish();
                    }


                }


            }
        });

        //취소
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentForm.this, MatchingStudent.class);
                startActivity(intent);
                finish();
            }
        });

    }
}