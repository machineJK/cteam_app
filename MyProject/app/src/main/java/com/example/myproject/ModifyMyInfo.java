package com.example.myproject;

import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

<<<<<<< HEAD

import com.example.myproject.util.ImageResizeUtils;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.soundcloud.android.crop.Crop;
=======
import com.bumptech.glide.Glide;
import com.example.myproject.Common.Common;
import com.example.myproject.Dto.Myupdate;
>>>>>>> fbacea1dbe17220e4180323db0052cfc44ae9f2e

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.myproject.Common.Common.ipConfig;
import static com.example.myproject.Common.Common.isNetworkConnected;


public class ModifyMyInfo extends AppCompatActivity {

    Button modify,cancel;
    Button btnLoad, btnPhoto;
    ImageView imageView8;

    private static final String TAG = "blackjin";

    private Boolean isPermission = true;

    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;

    private Boolean isCamera = false;
    private File tempFile;
    /*String pw, nickname, email;
    EditText etUPw, etUNickname, etUEmail;*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_my_info);

        tedPermission();

        imageView8 = findViewById(R.id.imageView8);
        imageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        modify = findViewById(R.id.modify_modify);
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
        });

<<<<<<< HEAD
        /*etUPw = findViewById(R.id.etUPw);
=======
/*        etUPw = findViewById(R.id.etUPw);
>>>>>>> fbacea1dbe17220e4180323db0052cfc44ae9f2e
        etUNickname = findViewById(R.id.etUNick);
        etUEmail = findViewById(R.id.etUEmail);*/

        findViewById(R.id.btnLoad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 권한 허용에 동의하지 않았을 경우 토스트를 띄웁니다.
                if(isPermission) goToAlbum();
                else Toast.makeText(view.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
            }
        });

<<<<<<< HEAD
        findViewById(R.id.btnPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 권한 허용에 동의하지 않았을 경우 토스트를 띄웁니다.
                if(isPermission)  takePhoto();
                else Toast.makeText(view.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
            }
        });
=======
        photoLoad = findViewById(R.id.btnLoad);
        photoBtn = findViewById(R.id.btnPhoto);

        // 보내온 값 파싱
        Intent intent = getIntent();
        Myupdate selItem = (Myupdate) intent.getSerializableExtra("selItem");

        pw = selItem.getPw();
        nickname = selItem.getNickname();
        email = selItem.getEmail();

        etUPw.setText(pw);
        etUNickname.setText(nickname);
        etUEmail.setText(email);

        imagePath = selItem.getImage_path();
        pImgDbPathU = imagePath;
        imageDbPathU = imagePath;

        imageView8.setVisibility(View.VISIBLE);
        // 선택된 이미지 보여주기
        Glide.with(this).load(imagePath).into(imageView8);*/

/*        photoBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                try{
                    file = createFile();
                    Log.d("Sub1Update:FilePath ", file.getAbsolutePath());
                }catch(Exception e){
                    Log.d("Sub1Update:error1", "Something Wrong", e);
                }
>>>>>>> fbacea1dbe17220e4180323db0052cfc44ae9f2e


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            if (tempFile != null) {
                if (tempFile.exists()) {

                    if (tempFile.delete()) {
                        Log.e(TAG, tempFile.getAbsolutePath() + " 삭제 성공");
                        tempFile = null;
                    }
                }
            }
<<<<<<< HEAD
=======
        });*/
/*        photoLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView8.setVisibility(View.VISIBLE);
>>>>>>> fbacea1dbe17220e4180323db0052cfc44ae9f2e

            return;
        }

        switch (requestCode) {
            case PICK_FROM_ALBUM: {

                Uri photoUri = data.getData();
                Log.d(TAG, "PICK_FROM_ALBUM photoUri : " + photoUri);

                cropImage(photoUri);

                break;
            }
<<<<<<< HEAD
            case PICK_FROM_CAMERA: {

                Uri photoUri = Uri.fromFile(tempFile);
                Log.d(TAG, "takePhoto photoUri : " + photoUri);
=======
        });*/
>>>>>>> fbacea1dbe17220e4180323db0052cfc44ae9f2e

                cropImage(photoUri);

                break;
            }
            case Crop.REQUEST_CROP: {
                //File cropFile = new File(Crop.getOutput(data).getPath());
                setImage();
            }
        }
    }



    /* 앨범에서 이미지 가져오기 */
    private void goToAlbum() {
        isCamera = false;

<<<<<<< HEAD
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }
=======
/*    private File createFile() throws IOException {
        java.text.SimpleDateFormat tmpDateFormat = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");
>>>>>>> fbacea1dbe17220e4180323db0052cfc44ae9f2e

    /* 카메라 사진 찍기 */
    private void takePhoto() {

<<<<<<< HEAD
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
=======
        return curFile;
    }*/

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
>>>>>>> fbacea1dbe17220e4180323db0052cfc44ae9f2e

        try {
            tempFile = createImageFile();
        } catch (IOException e) {
            Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }
        if (tempFile != null) {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

                Uri photoUri = FileProvider.getUriForFile(this,
                        "{package name}.provider", tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, PICK_FROM_CAMERA);

            } else {

                Uri photoUri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, PICK_FROM_CAMERA);

            }
        }
    }

    /* Crop 기능 */
    private void cropImage(Uri photoUri) {

        Log.d(TAG, "tempFile : " + tempFile);

        /**
         *  갤러리에서 선택한 경우에는 tempFile 이 없으므로 새로 생성해줍니다.
         */
        if(tempFile == null) {
            try {
                tempFile = createImageFile();
            } catch (IOException e) {
                Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                finish();
                e.printStackTrace();
            }
        }

<<<<<<< HEAD
        //크롭 후 저장할 Uri
        Uri savingUri = Uri.fromFile(tempFile);

        Crop.of(photoUri, savingUri).asSquare().start(this);
    }

    /* 폴더 및 파일 만들기 */
    private File createImageFile() throws IOException {

        // 이미지 파일 이름 ( blackJin_{시간}_ )
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "blackJin_" + timeStamp + "_";

        // 이미지가 저장될 폴더 이름 ( blackJin )
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/blackJin/");
        if (!storageDir.exists()) storageDir.mkdirs();

        // 파일 생성
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        Log.d(TAG, "createImageFile : " + image.getAbsolutePath());

        return image;
    }

    /* tempFile 을 bitmap 으로 변환 후 ImageView 에 설정한다. */
    private void setImage() {
=======
    }*/

/*    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }*/

/*    public void btnUpdateClicked(View view){
        if(isNetworkConnected(this) == true){
            if(fileSize <= 500000000) {  // 파일크기가 500메가 보다 작아야 업로드 할수 있음
                pw = etUPw.getText().toString();
                nickname = etUNickname.getText().toString();
                email = etUEmail.getText().toString();
>>>>>>> fbacea1dbe17220e4180323db0052cfc44ae9f2e

        ImageView imageView = findViewById(R.id.imageView8);

        ImageResizeUtils.resizeFile(tempFile, tempFile, 1280, isCamera);

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
        Log.d(TAG, "setImage : " + tempFile.getAbsolutePath());

        imageView.setImageBitmap(originalBm);

        /**
         *  tempFile 사용 후 null 처리를 해줘야 합니다.
         *  (resultCode != RESULT_OK) 일 때 tempFile 을 삭제하기 때문에
         *  기존에 데이터가 남아 있게 되면 원치 않은 삭제가 이뤄집니다.
         */


<<<<<<< HEAD
    }





    private void tedPermission() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공
                isPermission = true;

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
                isPermission = false;

            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();


    }

=======
    }*/
/*    public void btnCancelClicked(View view){
        finish();
    }*/
>>>>>>> fbacea1dbe17220e4180323db0052cfc44ae9f2e
}
