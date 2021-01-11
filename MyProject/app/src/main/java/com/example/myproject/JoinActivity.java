package com.example.myproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.InputStream;

public class JoinActivity extends AppCompatActivity {
    private static final String TAG = "main:JoinActivity";
    
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    static String SAMPLEIMG = "camera_image";


    Button btnJoin,btnJoinCancel;
    Spinner spinnerYear,spinnerMonth,spinnerDay,spinnerAddr1,spinnerAddr2;
    EditText eT_member_id,eT_member_pw,eT_member_nickname,eT_member_name,eT_member_email;
    String member_gender;
    RadioGroup rg_gender;
    RadioButton rb_male,rb_female;
    ImageView picture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //권한 위임
        checkDangerousPermissions();

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

        //주소
        spinnerAddr1 = findViewById(R.id.spinnerAddr1);
        spinnerAddr2 = findViewById(R.id.spinnerAddr2);

        ArrayAdapter addr1Adapter = ArrayAdapter.createFromResource(this,R.array.addr1,
                R.layout.spinner_design);
        addr1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAddr1.setAdapter(addr1Adapter);

        spinnerAddr1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String addr1_text = spinnerAddr1.getSelectedItem().toString();
                if(addr1_text.equals("서울")){
                    ArrayAdapter addr2Adapter = ArrayAdapter.createFromResource(JoinActivity.this,
                            R.array.seoul,R.layout.spinner_design);
                    addr2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerAddr2.setAdapter(addr2Adapter);
                }else if(addr1_text.equals("광주")){
                    ArrayAdapter addr2Adapter = ArrayAdapter.createFromResource(JoinActivity.this,
                            R.array.gwangju,R.layout.spinner_design);
                    addr2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerAddr2.setAdapter(addr2Adapter);
                }else if(addr1_text.equals("인천")){
                    ArrayAdapter addr2Adapter = ArrayAdapter.createFromResource(JoinActivity.this,
                            R.array.incheon,R.layout.spinner_design);
                    addr2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerAddr2.setAdapter(addr2Adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //이미지 클릭해서 앨범 불러오기
        picture = findViewById(R.id.member_picture);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, PICK_FROM_ALBUM);
                //PICK_FROM_ALBUM = 1
            }
        });

        //카메라 불러오기 버튼
        ImageButton btnCamera = findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,PICK_FROM_CAMERA);
                //PICK_FROM_CAMERA == 0
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

                String member_addr1 = spinnerAddr1.getSelectedItem().toString();
                String member_adddr2 = spinnerAddr2.getSelectedItem().toString();



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

    //앨범 및 카메라
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PICK_FROM_ALBUM){ //앨범
            if(resultCode == RESULT_OK){
                try {
                    //선택한 이미지에서 비트맵 생성

                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);

                    in.close();

                    //이미지뷰에 세팅
                    picture.setImageBitmap(img);

                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(this, "앨범 불러오기 오류", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if(requestCode == PICK_FROM_CAMERA){//카메라
            if(resultCode == RESULT_OK){
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                if(bitmap != null){
                    picture.setImageBitmap(bitmap);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //권한 위임
    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                Log.d(TAG, "권한 설정 완료");
            }else{
                Log.d(TAG, " 권한 설정 요청");
                ActivityCompat.requestPermissions(JoinActivity.this,
                        new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        }

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}