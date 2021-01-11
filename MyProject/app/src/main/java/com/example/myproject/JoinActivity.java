package com.example.myproject;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class JoinActivity extends AppCompatActivity {

    Button btnJoin,btnJoinCancel;
    Spinner spinnerYear,spinnerMonth,spinnerDay;
    EditText eT_member_id,eT_member_pw,eT_member_nickname,eT_member_name,eT_member_email;
    String member_gender;
    RadioGroup rg_gender;
    RadioButton rb_male,rb_female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //생년월일 스피너
        spinnerYear = findViewById(R.id.spinnerYear);
        spinnerMonth = findViewById(R.id.spinnerMonth);
        spinnerDay = findViewById(R.id.spinnerDay);

        ArrayAdapter yearAdapter = ArrayAdapter.createFromResource(this,R.array.year,
                                        R.layout.spinner_design);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(yearAdapter);

        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(this, R.array.month,
                R.layout.spinner_design);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(monthAdapter);

        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String monthtext = spinnerMonth.getSelectedItem().toString();
                int month = Integer.parseInt(monthtext);
                if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 ||
                        month == 10 || month == 12){    //31일
                    ArrayAdapter dayAdapter = ArrayAdapter.createFromResource(JoinActivity.this,
                            R.array.day31,R.layout.spinner_design);
                    dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerDay.setAdapter(dayAdapter);

                }else if(month == 4 || month == 6 || month == 9 || month == 11){   //30일
                    ArrayAdapter dayAdapter = ArrayAdapter.createFromResource(JoinActivity.this,
                            R.array.day30,R.layout.spinner_design);
                    dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerDay.setAdapter(dayAdapter);
                }else if(month == 2){   //28일
                    ArrayAdapter dayAdapter = ArrayAdapter.createFromResource(JoinActivity.this,
                            R.array.day28,R.layout.spinner_design);
                    dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerDay.setAdapter(dayAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        
        //회원가입 데이터
        eT_member_id = findViewById(R.id.member_id);
        eT_member_pw = findViewById(R.id.member_pw);
        eT_member_nickname = findViewById(R.id.member_nickname);
        eT_member_name = findViewById(R.id.member_name);
        eT_member_email = findViewById(R.id.member_email);
        rg_gender = findViewById(R.id.rg_gender);
        rb_male = findViewById(R.id.rb_male);
        rb_female = findViewById(R.id.rb_female);

        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_male){
                    member_gender = rb_male.getText().toString();
                }else if(checkedId == R.id.rb_female){
                    member_gender = rb_female.getText().toString();
                }
            }
        });


        btnJoin = findViewById(R.id.btnJoin);
        btnJoinCancel = findViewById(R.id.btnJoinCancel);

        //회원가입 버튼
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //반드시 개인정보를 입력하게 하기
                if(eT_member_id.getText().toString().length() == 0){
                    Toast.makeText(JoinActivity.this, "아이디를 작성하세요!!!", Toast.LENGTH_SHORT).show();
                    eT_member_id.requestFocus();
                    return;
                }
                if(eT_member_pw.getText().toString().length() == 0){
                    Toast.makeText(JoinActivity.this, "비밀번호를 작성하세요!!!", Toast.LENGTH_SHORT).show();
                    eT_member_pw.requestFocus();
                    return;
                }
                if(eT_member_nickname.getText().toString().length() == 0){
                    Toast.makeText(JoinActivity.this, "닉네임을 작성하세요!!!", Toast.LENGTH_SHORT).show();
                    eT_member_nickname.requestFocus();
                    return;
                }
                if(eT_member_name.getText().toString().length() == 0){
                    Toast.makeText(JoinActivity.this, "이름은 작성하세요!!!", Toast.LENGTH_SHORT).show();
                    eT_member_name.requestFocus();
                    return;
                }
                if(eT_member_email.getText().toString().length() == 0){
                    Toast.makeText(JoinActivity.this, "이메일을 작성하세요!!!", Toast.LENGTH_SHORT).show();
                    eT_member_email.requestFocus();
                    return;
                }
                String member_id = eT_member_id.getText().toString();
                String member_pw = eT_member_pw.getText().toString();
                String member_nickname = eT_member_nickname.getText().toString();
                String member_name = eT_member_name.getText().toString();
                String member_email = eT_member_email.getText().toString();
                String member_birth = spinnerYear.getSelectedItem().toString() + "." + spinnerMonth.getSelectedItem().toString()
                                        + "." + spinnerDay.getSelectedItem().toString();





            }
        });

        //회원가입 취소 버튼
        btnJoinCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}