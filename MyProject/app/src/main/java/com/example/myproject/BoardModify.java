package com.example.myproject;

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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.example.myproject.Atask.BoardInsert;
import com.example.myproject.Atask.BoardInsert2;
import com.example.myproject.Atask.BoardUpdate;
import com.example.myproject.Common.Common;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static com.example.myproject.Common.Common.ipConfig;
import static com.example.myproject.Common.Common.loginDTO;
import static com.example.myproject.Common.Common.selItem3;

public class BoardModify extends AppCompatActivity {

    private static final String TAG = "main:Board_WriteActivity";
    final int CAMERA_REQUEST = 1000;
    final int LOAD_IMAGE = 1001;

    String state ;

    Button btnBrdModify, btnBrdModifyCancel;

    ImageButton photoBtn,photoLoad;
    public String brdimageRealPathA, brdimageDbPathA;
    File file = null;
    long fileSize = 0;
    ImageView brdImageView;
    java.text.SimpleDateFormat tmpDateFormat;

    EditText etBrdContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_modify);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.cboard);

        etBrdContent = findViewById(R.id.etBrdContent);
        btnBrdModify = findViewById(R.id.btnBrdModify);
        btnBrdModifyCancel = findViewById(R.id.btnBrdModifyCancel);

        //사진 및 앨범
        photoBtn = findViewById(R.id.btnPhoto);
        photoLoad = findViewById(R.id.btnLoad);

        brdImageView = findViewById(R.id.brdImageView);

        tmpDateFormat = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");
        //Toast.makeText(this, "1" + brdimageDbPathA, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "2" + brdimageRealPathA, Toast.LENGTH_SHORT).show();
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
                brdImageView.setVisibility(View.VISIBLE);

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), LOAD_IMAGE);
            }
        });

        //기존 내용 써놓기
        etBrdContent.setText(selItem3.getBoard_content());
        if(selItem3.getBoard_image_path() != null){
            if(! selItem3.getBoard_image_path().contains("http") ){
                selItem3.setBoard_image_path("http://112.164.58.217:8080/tutors/" + selItem3.getBoard_image_path());
            }
            Glide.with(this).load(selItem3.getBoard_image_path()).into(brdImageView);
        }

        //수정 버튼
        btnBrdModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //반드시 정보를 입력하게 하기
                if(etBrdContent.getText().toString().length() == 0){
                    Toast.makeText(BoardModify.this, "내용을 작성하세요!!!", Toast.LENGTH_SHORT).show();
                    etBrdContent.requestFocus();
                    return;
                }

                if(fileSize <= 30000000){  // 파일크기가 30메가 보다 작아야 업로드 할수 있음
//
                    int qna_ref_num = selItem3.getQna_ref_num();
                    String board_content = etBrdContent.getText().toString();

                    if(brdimageDbPathA != null && brdimageRealPathA != null){

                        //Toast.makeText(BoardModify.this, "넘어옴", Toast.LENGTH_SHORT).show();
                        //새로운 이미지 넣기
                        BoardUpdate boardUpdate = new BoardUpdate(qna_ref_num,board_content,brdimageDbPathA,brdimageRealPathA);
                        selItem3.setBoard_content(board_content);
                        selItem3.setBoard_image_path(brdimageDbPathA);

                        Intent intent = new Intent(BoardModify.this, BoardDetailForm.class);
                        startActivity(intent);
                        finish();
                        try {
                            boardUpdate.execute().get().trim();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        //기존 이미지 사용
                        BoardUpdate boardUpdate = new BoardUpdate(qna_ref_num,board_content);
                        selItem3.setBoard_content(board_content);
                        Intent intent = new Intent(BoardModify.this, BoardDetailForm.class);
                        startActivity(intent);
                        finish();
                        try {
                            boardUpdate.execute().get().trim();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }


                } else {
                    // 알림창 띄움
                    final AlertDialog.Builder builder = new AlertDialog.Builder(BoardModify.this);
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
            }
        });

        //수정 취소 버튼
        btnBrdModifyCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BoardModify.this, BoardDetailForm.class);
                startActivity(intent);
                finish();
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
                    brdImageView.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }

                brdimageRealPathA = file.getAbsolutePath();
                String uploadFileName = brdimageRealPathA.split("/")[brdimageRealPathA.split("/").length - 1];
                ///참고!!!!!!///////////////////////////////////////////////////
                brdimageDbPathA = ipConfig + "/app/resources/" + uploadFileName;
                //Toast.makeText(this, "1" + brdimageDbPathA, Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, "2" + brdimageRealPathA, Toast.LENGTH_SHORT).show();
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
                    brdImageView.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(this, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }

                brdimageRealPathA = path;
                Log.d("Sub1Add", "brdimageFilePathA Path : " + brdimageRealPathA);
                String uploadFileName = brdimageRealPathA.split("/")[brdimageRealPathA.split("/").length - 1];
                brdimageDbPathA = ipConfig + "/app/resources/" + uploadFileName;

            } catch (Exception e){
                e.printStackTrace();
            }
        } else if(requestCode == CAMERA_REQUEST && resultCode != RESULT_OK){
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