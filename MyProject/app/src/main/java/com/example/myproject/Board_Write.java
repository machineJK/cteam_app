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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.myproject.Atask.BoardInsert;
import com.example.myproject.Atask.BoardInsert2;
import com.example.myproject.Common.Common;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static com.example.myproject.Common.Common.ipConfig;
import static com.example.myproject.Common.Common.loginDTO;

public class Board_Write extends AppCompatActivity {

    private static final String TAG = "main:Board_WriteActivity";
    final int CAMERA_REQUEST = 1000;
    final int LOAD_IMAGE = 1001;

    String state ;

    Button btnBrdJoin, btnBrdJoinCancel;

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
        setContentView(R.layout.activity_board__write);

        etBrdContent = findViewById(R.id.etBrdContent);
        btnBrdJoin = findViewById(R.id.btnBrdJoin);
        btnBrdJoinCancel = findViewById(R.id.btnBrdJoinCancel);

        //사진 및 앨범
        photoBtn = findViewById(R.id.btnPhoto);
        photoLoad = findViewById(R.id.btnLoad);

        brdImageView = findViewById(R.id.brdImageView);

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
                brdImageView.setVisibility(View.VISIBLE);

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), LOAD_IMAGE);
            }
        });


        btnBrdJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //반드시 정보를 입력하게 하기
                if(etBrdContent.getText().toString().length() == 0){
                    Toast.makeText(Board_Write.this, "내용을 작성하세요!!!", Toast.LENGTH_SHORT).show();
                    etBrdContent.requestFocus();
                    return;
                }

//                if(loginDTO == null){
//                    Toast.makeText(Board_Write.this, "로그인을 해주세요!!!", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(Board_Write.this, LoginActivity.class);
//                    startActivity(intent);
//                } else {

                    if(fileSize <= 30000000){  // 파일크기가 30메가 보다 작아야 업로드 할수 있음
//
                        String board_id = loginDTO.getId();
                        String board_nickname = loginDTO.getNickname();
                        String board_content = etBrdContent.getText().toString();
                        String id_image_path = loginDTO.getdbImgPath();
                        int board_notice = 0;
                        int qna_ref_num = 0;

                        if(loginDTO.getId().equals("admin")){
                            board_notice = 1;
                        }


                        Log.d("main:Boardwrite", "데이터 추출 !!!" + brdimageDbPathA);

                        if(brdimageDbPathA == null) {
                            BoardInsert2 brdInsert2 = new BoardInsert2(board_id,board_nickname,board_content,
                                    id_image_path,board_notice, qna_ref_num);
                            try {
                                state = brdInsert2.execute().get().trim();
                                Log.d("main:Boardwrite", "입력실행 !!!" + state);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            if(state.equals("1")){
                                Toast.makeText(Board_Write.this, "삽입성공 !!!", Toast.LENGTH_SHORT).show();
                                Log.d("main:Boardwrite", "삽입성공 !!!");
                                Board board = new Board();
                                board.selectBoard();
                                finish();
                            } else{
                                Toast.makeText(Board_Write.this, "삽입실패 !!!", Toast.LENGTH_SHORT).show();
                                Log.d("main:Boardwrite", "삽입실패 !!!");
                                finish();
                            }

                        } else {
                            BoardInsert brdInsert = new BoardInsert(board_id, board_nickname,board_content,
                                    brdimageDbPathA, brdimageRealPathA, board_notice, qna_ref_num, id_image_path);
                            try {
                                state = brdInsert.execute().get().trim();
                                Log.d("main:Boardwrite", "입력실행 !!!" + state);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            if(state.equals("1")){
                                Toast.makeText(Board_Write.this, "삽입성공 !!!", Toast.LENGTH_SHORT).show();
                                Log.d("main:Boardwrite", "삽입성공 !!!");
                                Board board = new Board();
                                board.selectBoard();
                                finish();
                            } else{
                                Toast.makeText(Board_Write.this, "삽입실패 !!!", Toast.LENGTH_SHORT).show();
                                Log.d("main:Boardwrite", "삽입실패 !!!");
                                finish();
                            }
                        }

                    } else {
                        // 알림창 띄움
                        final AlertDialog.Builder builder = new AlertDialog.Builder(Board_Write.this);
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

    //            }

            }
        });

        //문의작성 취소 버튼
        btnBrdJoinCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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