package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myproject.Atask.IdCheck;

import java.util.concurrent.ExecutionException;

import static com.example.myproject.Common.Common.loginDTO;
import static com.example.myproject.Common.Common.selItem;
import static com.example.myproject.Common.Common.checkDTO;

public class TeacherDetail extends AppCompatActivity {
    TextView teacher_nickname,teacher_univ,teacher_subject,
            teacher_addr,teacher_pay_worktime,teacher_intro;
    ImageView teacher_picture;
    Button chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_detail);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.cteacher);

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
        teacher_pay_worktime.setText(selItem.getTeacher_worktime() + "주 " + selItem.getTeacher_pay() + "만원");
        teacher_intro.setText(selItem.getTeacher_intro());
        Glide.with(this).load(selItem.getTeacher_image_path()).circleCrop().into(teacher_picture);


        //상담하기
        chat = findViewById(R.id.btn_goChat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDTO = null;
                //student_id 중복체크
                IdCheck student_id_check = new IdCheck(loginDTO.getId(),"s");
                try {
                    student_id_check.execute().get();
                    if(checkDTO.getIdchk() == 0){
                        Toast.makeText(TeacherDetail.this, "먼저 학생으로 등록해주세요!!!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TeacherDetail.this, StudentForm.class);
                        startActivity(intent);
                        finish();
                    }else{
                        //Toast.makeText(TeacherDetail.this, "이 아이디의 학생아이디는 존재!", Toast.LENGTH_SHORT).show();
                        if(!loginDTO.getId().equals(selItem.getTeacher_id())){
                            Intent intent = new Intent(TeacherDetail.this, ChatStartActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                } catch (ExecutionException e) {
                    e.getMessage();
                } catch (InterruptedException e) {
                    e.getMessage();
                }

            }
        });

    }
}