package com.example.myproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myproject.Atask.JoinInsert;
import com.example.myproject.Common.Common;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static com.example.myproject.Common.Common.ipConfig;
import static com.example.myproject.Common.Common.isNetworkConnected;
import static com.example.myproject.Common.Common.loginDTO;
import static com.example.myproject.Common.Common.checkDTO;


public class NaverExtraInfo extends AppCompatActivity {
    Spinner naverAddr1;
    Spinner naverAddr2;

    Button btnJoin;

    String state;

    final int CAMERA_REQUEST = 1000;
    final int LOAD_IMAGE = 1001;

    ImageButton photoBtn,photoLoad;
    public String imageRealPathA, imageDbPathA;
    String date = "";
    File file = null;
    long fileSize = 0;
    ImageView imageView;
    java.text.SimpleDateFormat tmpDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naver_extra_info);

        /*Toast.makeText(this, "" + socialDTO.getId(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "" + socialDTO.getEmail(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "" + socialDTO.getNickname(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "" + socialDTO.getName(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "" + socialDTO.getGender(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "" + socialDTO.getBirth(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "" + socialDTO.getdbImgPath(), Toast.LENGTH_SHORT).show();*/

        naverAddr1 = findViewById(R.id.naverAddr1);
        naverAddr2 = findViewById(R.id.naverAddr2);

        ArrayAdapter addr1Adapter = ArrayAdapter.createFromResource(this,R.array.addr1,
                R.layout.spinner_design);
        addr1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        naverAddr1.setAdapter(addr1Adapter);

        naverAddr1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String addr1_text = naverAddr1.getSelectedItem().toString();
                if(addr1_text.equals("서울")){
                    ArrayAdapter addr2Adapter = ArrayAdapter.createFromResource(NaverExtraInfo.this,
                            R.array.seoul,R.layout.spinner_design);
                    addr2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    naverAddr2.setAdapter(addr2Adapter);
                }else if(addr1_text.equals("광주")){
                    ArrayAdapter addr2Adapter = ArrayAdapter.createFromResource(NaverExtraInfo.this,
                            R.array.gwangju,R.layout.spinner_design);
                    addr2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    naverAddr2.setAdapter(addr2Adapter);
                }else if(addr1_text.equals("인천")){
                    ArrayAdapter addr2Adapter = ArrayAdapter.createFromResource(NaverExtraInfo.this,
                            R.array.incheon,R.layout.spinner_design);
                    addr2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    naverAddr2.setAdapter(addr2Adapter);
                }else if(addr1_text.equals("대전")){
                    ArrayAdapter addr2Adapter = ArrayAdapter.createFromResource(NaverExtraInfo.this,
                            R.array.daejeon,R.layout.spinner_design);
                    addr2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    naverAddr2.setAdapter(addr2Adapter);
                }else if(addr1_text.equals("대구")){
                    ArrayAdapter addr2Adapter = ArrayAdapter.createFromResource(NaverExtraInfo.this,
                            R.array.daegu,R.layout.spinner_design);
                    addr2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    naverAddr2.setAdapter(addr2Adapter);
                }else if(addr1_text.equals("부산")){
                    ArrayAdapter addr2Adapter = ArrayAdapter.createFromResource(NaverExtraInfo.this,
                            R.array.busan,R.layout.spinner_design);
                    addr2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    naverAddr2.setAdapter(addr2Adapter);
                }else if(addr1_text.equals("울산")){
                    ArrayAdapter addr2Adapter = ArrayAdapter.createFromResource(NaverExtraInfo.this,
                            R.array.ulsan,R.layout.spinner_design);
                    addr2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    naverAddr2.setAdapter(addr2Adapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //사진 및 앨범
        photoBtn = findViewById(R.id.n_btnPhoto);
        photoLoad = findViewById(R.id.n_btnLoad);

        imageView = findViewById(R.id.n_imageView);

        tmpDateFormat = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");

        //사진 찍기
        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    file = createFile();
                    Log.d("FilePath ", file.getAbsolutePath());

                }catch(Exception e){
                    Log.d("Sub1Add:filepath", "Something Wrong", e);
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // API24 이상 부터
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,
                            FileProvider.getUriForFile(getApplicationContext(),
                                    getApplicationContext().getPackageName() + ".fileprovider", file));
                    Log.d("sub1:appId", getApplicationContext().getPackageName());
                }else {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                }

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, CAMERA_REQUEST);
                }
            }
        });

        //앨범에서 로드
        photoLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setVisibility(View.VISIBLE);

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), LOAD_IMAGE);
            }
        });

    
        //추가정보 입력 후 db에 저장
        btnJoin = findViewById(R.id.btnExtraJoin);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkConnected(NaverExtraInfo.this) == true){

                    if(fileSize <= 30000000){  // 파일크기가 30메가 보다 작아야 업로드 할수 있음
                        String id = loginDTO.getId();
                        String pw = "social";
                        String nickname = loginDTO.getNickname();
                        String name = loginDTO.getName();
                        String gender = loginDTO.getGender();
                        String email = loginDTO.getEmail();
                        String birth = loginDTO.getBirth();
                        String addr1 = naverAddr1.getSelectedItem().toString();
                        String addr2 = naverAddr2.getSelectedItem().toString();
                        String kakao_login = "0";
                        String naver_login = "1";

                        loginDTO.setAddr1(addr1);
                        loginDTO.setAddr2(addr2);
                        loginDTO.setdbImgPath(imageDbPathA);
                        loginDTO.setKakao_login(kakao_login);
                        loginDTO.setNaver_login(naver_login);

                        //서버와의 연결을 위한 JoinInsert(AsyncTest 상속받음)
                        JoinInsert joinInsert = new JoinInsert(id,pw,nickname, name,gender,
                                birth,email,addr1, addr2, imageDbPathA, imageRealPathA,kakao_login,naver_login);

                        try{
                            state = joinInsert.execute().get().trim();
                            Log.d("main:joinact0 : ", state);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if(state.equals("1")){
                            Toast.makeText(NaverExtraInfo.this, "삽입성공 !!!", Toast.LENGTH_SHORT).show();
                            Log.d("main:joinact", "삽입성공 !!!");
                            finish();
                        }else{
                            Toast.makeText(NaverExtraInfo.this, "삽입실패 !!!", Toast.LENGTH_SHORT).show();
                            Log.d("main:joinact", "삽입실패 !!!");
                            finish();
                        }


                        Intent showIntent = new Intent(NaverExtraInfo.this, Matching.class);
                        showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |   // 이 엑티비티 플래그를 사용하여 엑티비티를 호출하게 되면 새로운 태스크를 생성하여 그 태스크안에 엑티비티를 추가하게 됩니다. 단, 기존에 존재하는 태스크들중에 생성하려는 엑티비티와 동일한 affinity(관계, 유사)를 가지고 있는 태스크가 있다면 그곳으로 새 엑티비티가 들어가게됩니다.
                                Intent.FLAG_ACTIVITY_SINGLE_TOP | // 엑티비티를 호출할 경우 호출된 엑티비티가 현재 태스크의 최상단에 존재하고 있었다면 새로운 인스턴스를 생성하지 않습니다. 예를 들어 ABC가 엑티비티 스택에 존재하는 상태에서 C를 호출하였다면 여전히 ABC가 존재하게 됩니다.
                                Intent.FLAG_ACTIVITY_CLEAR_TOP); // 만약에 엑티비티스택에 호출하려는 엑티비티의 인스턴스가 이미 존재하고 있을 경우에 새로운 인스턴스를 생성하는 것 대신에 존재하고 있는 엑티비티를 포그라운드로 가져옵니다. 그리고 엑티비티스택의 최상단 엑티비티부터 포그라운드로 가져올 엑티비티까지의 모든 엑티비티를 삭제합니다.
                        startActivity(showIntent);

                        finish();
                    }else{
                        // 알림창 띄움
                        final AlertDialog.Builder builder = new AlertDialog.Builder(NaverExtraInfo.this);
                        builder.setTitle("알림");
                        builder.setMessage("파일 크기가 30MB초과하는 파일은 업로드가 제한되어 있습니다.\n30MB이하 파일로 선택해 주십시요!!!");
                        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                    }

                }else {
                    Toast.makeText(NaverExtraInfo.this,
                            "인터넷이 연결되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NaverExtraInfo.this, LoginActivity.class);
                    startActivity(intent);
                }


            }
        });



    }

    //사진을 저장할 파일 생성
    private File createFile() throws IOException {

        String imageFileName = "My" + tmpDateFormat.format(new Date()) + ".jpg";
        File storageDir = Environment.getExternalStorageDirectory();
        File curFile = new File(storageDir, imageFileName);

        return curFile;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && data != null) {
            Toast.makeText(this, "카메라에서 이미지 넘어옴", Toast.LENGTH_SHORT).show();
            try {
                // 이미지 돌리기 및 리사이즈
                Bitmap newBitmap = Common.imageRotateAndResize(file.getAbsolutePath());
                if(newBitmap != null){
                    imageView.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }

                imageRealPathA = file.getAbsolutePath();
                String uploadFileName = imageRealPathA.split("/")[imageRealPathA.split("/").length - 1];
                ///참고!!!!!!///////////////////////////////////////////////////
                imageDbPathA = ipConfig + "/app/resources/" + uploadFileName;
                ///참고!!!!!!///////////////////////////////////////////////////
            } catch (Exception e){
                e.printStackTrace();
            }
        }else if (requestCode == LOAD_IMAGE && data != null) {
            try {
                String path = "";
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    // Get the path from the Uri
                    path = getPathFromURI(selectedImageUri);
                }
                // 이미지 돌리기 및 리사이즈
                Bitmap newBitmap = Common.imageRotateAndResize(path);
                if(newBitmap != null){
                    imageView.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }

                imageRealPathA = path;
                Log.d("Sub1Add", "imageFilePathA Path : " + imageRealPathA);
                String uploadFileName = imageRealPathA.split("/")[imageRealPathA.split("/").length - 1];
                imageDbPathA = ipConfig + "/app/resources/" + uploadFileName;

            } catch (Exception e){
                e.printStackTrace();
            }
        }else if(requestCode == CAMERA_REQUEST && resultCode != RESULT_OK){
            Toast.makeText(this, "사진 못넘어옴", Toast.LENGTH_SHORT).show();
        }

    }

    // Get the real path from the URI
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

}