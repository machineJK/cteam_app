package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myproject.Atask.IdCheck;
import com.example.myproject.Dto.ChatDTO;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static com.example.myproject.Common.Common.loginDTO;
import static com.example.myproject.Common.Common.selItem;
import static com.example.myproject.Common.Common.selItem2;
import static com.example.myproject.Common.Common.checkDTO;

public class StudentDetail extends AppCompatActivity {

    Button chat;
    ImageView sdetail_imageView;
    TextView et_sdetail_nickname,et_sdetail_subject,et_sdetail_addr,et_dstudent_intro;

    private DatabaseReference myRef;
    private DatabaseReference toRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.cstudent);

        sdetail_imageView = findViewById(R.id.sdetail_imageView);
        et_sdetail_nickname = findViewById(R.id.et_sdetail_nickname);
        et_sdetail_subject = findViewById(R.id.et_sdetail_subject);
        et_sdetail_addr = findViewById(R.id.et_sdetail_addr);
        et_dstudent_intro = findViewById(R.id.et_dstudent_intro);

        if(! selItem2.getStudent_image_path().contains("http") ){
            selItem2.setStudent_image_path("http://112.164.58.217:8080/tutors/" + selItem2.getStudent_image_path());
        }
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
                checkDTO = null;
                //student_id 중복체크
                IdCheck teacher_id_check = new IdCheck(loginDTO.getId(),"t");
                try {
                    teacher_id_check.execute().get();
                    if(checkDTO.getIdchk() == 0){
                        Toast.makeText(StudentDetail.this, "먼저 선생으로 등록해주세요!!!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(StudentDetail.this, TeacherForm.class);
                        startActivity(intent);
                        finish();
                    }else{
                        //Toast.makeText(TeacherDetail.this, "이 아이디의 학생아이디는 존재!", Toast.LENGTH_SHORT).show();
                        if(!loginDTO.getId().equals(selItem2.getStudent_id())){

                            ChatDTO dto = new ChatDTO();
                            dto.setNickname(selItem2.getStudent_nickname());
                            if(selItem2.getStudent_intro() == ""){
                                dto.setMsg("안녕하세요 " + selItem2.getStudent_subject() + "를 배우고 싶어요");
                            }else {
                                dto.setMsg(selItem2.getStudent_intro());
                            }

                            long now = System.currentTimeMillis();
                            Date mDate = new Date(now);
                            SimpleDateFormat simpleDate = new SimpleDateFormat("hh:mm:aa");
                            String getTime = simpleDate.format(mDate);
                            dto.setDate(getTime);

                            myRef = database.getReference(loginDTO.getId() + "1").child(selItem2.getStudent_id() + "2");
                            toRef = database.getReference(selItem2.getStudent_id() + "2").child(loginDTO.getId() + "1");

                            myRef.push().setValue(dto);
                            toRef.push().setValue(dto);

                            Intent intent = new Intent(StudentDetail.this, ChatStartStudentActivity.class);
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