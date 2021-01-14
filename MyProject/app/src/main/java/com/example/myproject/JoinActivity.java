
package com.example.myproject;

<<<<<<< HEAD
import android.os.Bundle;
=======
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
>>>>>>> d6c1b696d214179798eac2ae5177cbfa7b55f979
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
<<<<<<< HEAD
=======
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
>>>>>>> d6c1b696d214179798eac2ae5177cbfa7b55f979
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
<<<<<<< HEAD
=======
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myproject.Atask.JoinInsert;

import java.util.concurrent.ExecutionException;
>>>>>>> d6c1b696d214179798eac2ae5177cbfa7b55f979

public class JoinActivity extends AppCompatActivity {

    Button btnJoin, btnJoinCancel;
    Spinner spinnerYear, spinnerMonth, spinnerDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //생년월일 스피너
        spinnerYear = findViewById(R.id.spinnerYear);
        spinnerMonth = findViewById(R.id.spinnerMonth);
        spinnerDay = findViewById(R.id.spinnerDay);

        ArrayAdapter yearAdapter = ArrayAdapter.createFromResource(this, R.array.year,
                R.layout.spinner_design);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(yearAdapter);

        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(this, R.array.month,
                R.layout.spinner_design);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(monthAdapter);

        ArrayAdapter dayAdapter = ArrayAdapter.createFromResource(this, R.array.day,
                R.layout.spinner_design);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(dayAdapter);

<<<<<<< HEAD
        //
        btnJoin = findViewById(R.id.btnJoin);
        btnJoinCancel = findViewById(R.id.btnJoinCancel);
=======
        //기본으로 지정
        gender = "남자";
        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_male){
                    gender = rb_male.getText().toString();
                }else if(checkedId == R.id.rb_female){
                    gender = rb_female.getText().toString();
                }
            }
        });


        btnJoin = findViewById(R.id.btnJoin);
        btnJoinCancel = findViewById(R.id.btnJoinCancel);


        //체크용
        findViewById(R.id.btnCheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원가입 데이터
                String id = et_id.getText().toString();
                String pw = et_pw.getText().toString();
                String nickname = et_nickname.getText().toString();
                String name = et_name.getText().toString();
                //gender는 위에 있음
                String email = et_email.getText().toString();
                String birth = spinnerYear.getSelectedItem().toString() + "." + spinnerMonth.getSelectedItem().toString()
                        + "." + spinnerDay.getSelectedItem().toString();
                String addr1 = spinnerAddr1.getSelectedItem().toString();
                String addr2 = spinnerAddr2.getSelectedItem().toString();
                String picture = "default.jpg";

                Toast.makeText(JoinActivity.this, "" + id, Toast.LENGTH_SHORT).show();
                Toast.makeText(JoinActivity.this, "" + pw, Toast.LENGTH_SHORT).show();
                Toast.makeText(JoinActivity.this, "" + nickname, Toast.LENGTH_SHORT).show();
                Toast.makeText(JoinActivity.this, "" + name, Toast.LENGTH_SHORT).show();
                Toast.makeText(JoinActivity.this, "" + gender, Toast.LENGTH_SHORT).show();
                Toast.makeText(JoinActivity.this, "" + birth, Toast.LENGTH_SHORT).show();
                Toast.makeText(JoinActivity.this, "" + email, Toast.LENGTH_SHORT).show();
                Toast.makeText(JoinActivity.this, "" + addr1, Toast.LENGTH_SHORT).show();
                Toast.makeText(JoinActivity.this, "" + addr2, Toast.LENGTH_SHORT).show();
                Toast.makeText(JoinActivity.this, "" + picture, Toast.LENGTH_SHORT).show();
            }
        });
>>>>>>> d6c1b696d214179798eac2ae5177cbfa7b55f979

        //회원가입
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //회원가입 취소
        btnJoinCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}