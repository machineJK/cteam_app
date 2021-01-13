package com.example.myproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myproject.Atask.JoinInsert;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;

public class JoinActivity extends AppCompatActivity {
    private static final String TAG = "main:JoinActivity";
    
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;

    String state;
    //경로변수
    String mCurrentPhotoPath;

    Button btnJoin,btnJoinCancel;
    Spinner spinnerYear,spinnerMonth,spinnerDay,spinnerAddr1,spinnerAddr2;
    EditText et_id,et_pw,et_nickname,et_name,et_email;
    String gender, picture;
    RadioGroup rg_gender;
    RadioButton rb_male,rb_female;
    //ImageView picture;


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
/*
        picture = findViewById(R.id.picture);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_FROM_ALBUM);
                //PICK_FROM_ALBUM = 1
            }
        });
*/

        //카메라 불러오기 버튼
/*        ImageButton btnCamera = findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,PICK_FROM_CAMERA);
                //PICK_FROM_CAMERA == 0
                *//*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager()) != null){
                    File photoFile = null;

                    try {
                        photoFile = createImageFile();
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                    if(photoFile != null){
                        Uri photoURI = FileProvider.getUriForFile(JoinActivity.this,
                                "com.example.myproject.fileprovider",photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(intent,PICK_FROM_CAMERA);
                    }

                }*//*
            }
        });*/




        
        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);
        et_nickname = findViewById(R.id.et_nickname);
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        rg_gender = findViewById(R.id.rg_gender);
        rb_male = findViewById(R.id.rb_male);
        rb_female = findViewById(R.id.rb_female);

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

<<<<<<< HEAD
        //
        btnJoin = findViewById(R.id.btnUpdate);
        btnJoinCancel = findViewById(R.id.btnUpdateCancel);
=======
        btnJoin = findViewById(R.id.btnJoin);
        btnJoinCancel = findViewById(R.id.btnJoinCancel);
>>>>>>> 42fafac5ca53ab5bfe7e7d25b9b9b1746fa329ac

        //체크용
        findViewById(R.id.btnCheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();
                String birth = spinnerYear.getSelectedItem().toString() + "." + spinnerMonth.getSelectedItem().toString()
                        + "." + spinnerDay.getSelectedItem().toString();

                Toast.makeText(JoinActivity.this, "" + email, Toast.LENGTH_SHORT).show();
                Toast.makeText(JoinActivity.this, "" + birth, Toast.LENGTH_SHORT).show();
            }
        });


        //회원가입 버튼
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //반드시 개인정보를 입력하게 하기
                if(et_id.getText().toString().length() == 0){
                    Toast.makeText(JoinActivity.this, "아이디를 작성하세요!!!", Toast.LENGTH_SHORT).show();
                    et_id.requestFocus();
                    return;
                }
                if(et_pw.getText().toString().length() == 0){
                    Toast.makeText(JoinActivity.this, "비밀번호를 작성하세요!!!", Toast.LENGTH_SHORT).show();
                    et_pw.requestFocus();
                    return;
                }
                if(et_nickname.getText().toString().length() == 0){
                    Toast.makeText(JoinActivity.this, "닉네임을 작성하세요!!!", Toast.LENGTH_SHORT).show();
                    et_nickname.requestFocus();
                    return;
                }
                if(et_name.getText().toString().length() == 0){
                    Toast.makeText(JoinActivity.this, "이름은 작성하세요!!!", Toast.LENGTH_SHORT).show();
                    et_name.requestFocus();
                    return;
                }
                if(et_email.getText().toString().length() == 0){
                    Toast.makeText(JoinActivity.this, "이메일을 작성하세요!!!", Toast.LENGTH_SHORT).show();
                    et_email.requestFocus();
                    return;
                }
                
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

                //Toast.makeText(JoinActivity.this, "" + id, Toast.LENGTH_SHORT).show();
                //Toast.makeText(JoinActivity.this, "" + pw, Toast.LENGTH_SHORT).show();
                //Toast.makeText(JoinActivity.this, "" + nickname, Toast.LENGTH_SHORT).show();
                //Toast.makeText(JoinActivity.this, "" + name, Toast.LENGTH_SHORT).show();
                //Toast.makeText(JoinActivity.this, "" + gender, Toast.LENGTH_SHORT).show();
                //Toast.makeText(JoinActivity.this, "" + email, Toast.LENGTH_SHORT).show();
                //Toast.makeText(JoinActivity.this, "" + birth, Toast.LENGTH_SHORT).show();
                //Toast.makeText(JoinActivity.this, "" + addr1, Toast.LENGTH_SHORT).show();
                //Toast.makeText(JoinActivity.this, "" + addr2, Toast.LENGTH_SHORT).show();
                //Toast.makeText(JoinActivity.this, "" + picture, Toast.LENGTH_SHORT).show();


                //서버와의 연결을 위한 JoinInsert(AsyncTest 상속받음)
                JoinInsert joinInsert = new JoinInsert(id,pw,nickname, name,gender,
                                         birth,email,addr1, addr2, picture);
                try{
                    state = joinInsert.execute().get().trim();
                    Log.d("main:joinact0 : ", state);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(state.equals("1")){
                    Toast.makeText(JoinActivity.this, "삽입성공 !!!", Toast.LENGTH_SHORT).show();
                    Log.d("main:joinact", "삽입성공 !!!");
                    finish();
                }else{
                    Toast.makeText(JoinActivity.this, "삽입실패 !!!", Toast.LENGTH_SHORT).show();
                    Log.d("main:joinact", "삽입실패 !!!");
                    finish();
                }

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

/*    //촬영한 사진을 이미지 파일로 저장
    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmsss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg",storageDir);

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }*/

    /*//앨범 및 카메라
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
                    picture.setImageBitmap(bitmap);}
                *//*File file = new File(mCurrentPhotoPath);
                Bitmap bitmap;
                if(Build.VERSION.SDK_INT >= 29){
                    ImageDecoder.Source source =
                            ImageDecoder.createSource(getContentResolver(),Uri.fromFile(file));
                    try {
                        bitmap = ImageDecoder.decodeBitmap(source);
                        if(bitmap != null){picture.setImageBitmap(bitmap);}
                    }catch(IOException e){
                            e.printStackTrace();
                        }
                }else{
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                        if (bitmap != null){picture.setImageBitmap(bitmap);}
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }*//*
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/






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

        //카메라
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