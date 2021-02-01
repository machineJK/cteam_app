package com.example.myproject;

import androidx.annotation.Nullable;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
//import com.example.myproject.Atask.ListModify;
import com.example.myproject.Common.Common;

import static com.example.myproject.Common.Common.loginDTO;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.myproject.Common.Common.ipConfig;
import static com.example.myproject.Common.Common.isNetworkConnected;



public class ModifyMyInfo extends AppCompatActivity {

    private static final String TAG = "Main:ModifyMyInfo";

    public String imagePath;
    public String pImgDbPathU;
    public String imageRealPathU = "", imageDbPathU = "";
    //public String imageRealPathU, imageDbPathU;
    java.text.SimpleDateFormat tmpDateFormat;

    final int CAMERA_REQUEST = 1010;
    final int LOAD_IMAGE = 1011;

    File file = null;
    long fileSize = 0;

    Button photoBtn, photoLoad;
    ImageView imageView8;
    String id, pw, nickname, email;
    EditText etUId, etUPw, etUNickname, etUEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_my_info);

        etUId = findViewById(R.id.etUId);
        etUPw = findViewById(R.id.etUPw);
        etUNickname = findViewById(R.id.etUNick);
        etUEmail = findViewById(R.id.etUEmail);

        imageView8 = findViewById(R.id.imageView8);
        imageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        /*modify = findViewById(R.id.modify_modify);
        cancel = findViewById(R.id.modify_cancel);

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModifyMyInfo.this, MyInfo.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

        // 업데이트시 아이디 변경불가
        etUId.setEnabled(false);

        // 보내온 값 파싱
        Intent intent = getIntent();
        //MyModify selItem = (MyModify) intent.getSerializableExtra("selItem");
        //MemberDTO loginDTO = (MemberDTO) intent.getSerializableExtra("loginDTO");

        id = loginDTO.getId();
        //pw = loginDTO.getPw();
        nickname = loginDTO.getNickname();
        email = loginDTO.getEmail();

        Log.d(TAG, "onCreate: " + email);

        // 가져온 값 써 넣기
        etUId.setText(id);
        //etUPw.setText(pw);
        etUNickname.setText(nickname);
        etUEmail.setText(email);

        imagePath = loginDTO.getdbImgPath();
        pImgDbPathU = imagePath;
        imageDbPathU = imagePath;

        imageView8.setVisibility(View.VISIBLE);
        // 선택된 이미지 보여주기
        Glide.with(ModifyMyInfo.this).load(imagePath).into(imageView8);

        //카메라, 앨범
        photoBtn = findViewById(R.id.btnPhoto);
        photoLoad = findViewById(R.id.btnLoad);
        tmpDateFormat = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");


        //사진찍기
        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    try{
                        file = createFile();
                        Log.d("ModifyMyInfo:FilePath ", file.getAbsolutePath());
                    }catch(Exception e){
                        Log.d("ModifyMyInfo:error1", "Something Wrong", e);
                    }

                    imageView8.setVisibility(View.VISIBLE);

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

                }catch(Exception e){
                    Log.d("ModifyMyInfo:error2", "Something Wrong", e);
                }

            }
        });

        //앨범로드
        photoLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView8.setVisibility(View.VISIBLE);

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), LOAD_IMAGE);
            }
        });
        /*//사진 찍기
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
                imageView8.setVisibility(View.VISIBLE);

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), LOAD_IMAGE);
            }
        });*/


    }

    //사진을 저장할 파일 생성
    private File createFile() throws  IOException {
/*
        String imageFileName = "My" + tmpDateFormat.format(new Date()) + ".jpg";
        File storageDir = Environment.getExternalStorageDirectory();
        File curFile = new File(storageDir, imageFileName);

        return curFile;
    }*/

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String imageFileName="My" + tmpDateFormat.format(new Date()) + ".jpg";

        File photo = new File(Environment.getExternalStorageDirectory(),  imageFileName);

        return photo;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            try {
                // 이미지 돌리기 및 리사이즈
                Bitmap newBitmap = Common.imageRotateAndResize(file.getAbsolutePath());
                if(newBitmap != null){
                    imageView8.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }

                imageRealPathU = file.getAbsolutePath();
                String uploadFileName = imageRealPathU.split("/")[imageRealPathU.split("/").length - 1];
                imageDbPathU = ipConfig + "/app/resources/" + uploadFileName;

                ImageView imageView8 = (ImageView) findViewById(R.id.imageView8);
                imageView8.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));

                Log.d("ModifyMyInfo:picPath", file.getAbsolutePath());

            } catch (Exception e){
                e.printStackTrace();
            }
        }else if (requestCode == LOAD_IMAGE && resultCode == RESULT_OK) {

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
                    imageView8.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }

                imageRealPathU = path;
                String uploadFileName = imageRealPathU.split("/")[imageRealPathU.split("/").length - 1];
                imageDbPathU = ipConfig + "/app/resources/" + uploadFileName;

            } catch (Exception e){
                e.printStackTrace();
            }
        }

    }
    /*    if (requestCode == CAMERA_REQUEST && data != null) {
            Toast.makeText(this, "카메라에서 이미지 넘어옴", Toast.LENGTH_SHORT).show();
            try {
                // 이미지 돌리기 및 리사이즈
                Bitmap newBitmap = Common.imageRotateAndResize(file.getAbsolutePath());
                if(newBitmap != null){
                    imageView8.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }
                //imageRealPathU, imageDbPathU;
                imageRealPathU = file.getAbsolutePath();
                String uploadFileName = imageRealPathU.split("/")[imageRealPathU.split("/").length - 1];
                ///참고!!!!!!///////////////////////////////////////////////////
                imageDbPathU = ipConfig + "/app/resources/" + uploadFileName;
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
                    imageView8.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }

                imageRealPathU = path;
                Log.d("Sub1Add", "imageFilePathA Path : " + imageRealPathU);
                String uploadFileName = imageRealPathU.split("/")[imageRealPathU.split("/").length - 1];
                imageDbPathU = ipConfig + "/app/resources/" + uploadFileName;

            } catch (Exception e){
                e.printStackTrace();
            }
        }else if(requestCode == CAMERA_REQUEST && resultCode != RESULT_OK){
            Toast.makeText(this, "사진 못넘어옴", Toast.LENGTH_SHORT).show();
        }

    }*/
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
    //수정버튼
    public void btnUpdateClicked(View view){
        if(isNetworkConnected(this) == true){
            if(fileSize <= 300000000) {  // 파일크기가 300메가 보다 작아야 업로드 할수 있음
                id = etUId.getText().toString();
                pw = etUPw.getText().toString();
                nickname = etUNickname.getText().toString();
                email = etUEmail.getText().toString();


                com.example.myproject.ATask.ListModify listModify = new com.example.myproject.ATask.ListModify(id, pw, nickname, email, pImgDbPathU, imageDbPathU, imageRealPathU);
                listModify.execute();


                //Toast.makeText(getApplicationContext(), "수정성공", Toast.LENGTH_LONG).show();

                Intent showIntent = new Intent(getApplicationContext(), MyInfo.class);
                showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |   // 이 엑티비티 플래그를 사용하여 엑티비티를 호출하게 되면 새로운 태스크를 생성하여 그 태스크안에 엑티비티를 추가하게 됩니다. 단, 기존에 존재하는 태스크들중에 생성하려는 엑티비티와 동일한 affinity(관계, 유사)를 가지고 있는 태스크가 있다면 그곳으로 새 엑티비티가 들어가게됩니다.
                        Intent.FLAG_ACTIVITY_SINGLE_TOP | // 엑티비티를 호출할 경우 호출된 엑티비티가 현재 태스크의 최상단에 존재하고 있었다면 새로운 인스턴스를 생성하지 않습니다. 예를 들어 ABC가 엑티비티 스택에 존재하는 상태에서 C를 호출하였다면 여전히 ABC가 존재하게 됩니다.
                        Intent.FLAG_ACTIVITY_CLEAR_TOP); // 만약에 엑티비티스택에 호출하려는 엑티비티의 인스턴스가 이미 존재하고 있을 경우에 새로운 인스턴스를 생성하는 것 대신에 존재하고 있는 엑티비티를 포그라운드로 가져옵니다. 그리고 엑티비티스택의 최상단 엑티비티부터 포그라운드로 가져올 엑티비티까지의 모든 엑티비티를 삭제합니다.
                startActivity(showIntent);

                finish();
            }else{
                // 알림창 띄움
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
            Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }

    }
    //취소버튼
    public void btnCancelClicked(View view){
        finish();
    }



}
