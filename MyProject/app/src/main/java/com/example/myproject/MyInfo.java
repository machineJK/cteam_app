package com.example.myproject;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.nhn.android.naverlogin.OAuthLogin;

import static com.example.myproject.Common.Common.loginDTO;
import static com.example.myproject.LoginActivity.mOAuthLoginModule;

public class MyInfo extends AppCompatActivity {

    Button modify, btnLogout;
    ImageButton matching, talk, board, my;
    ImageView imageView6;
    ImageButton imageButton;
    TextView my_nickname;
    String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        my_nickname = findViewById(R.id.my_nickname);
        nickname = loginDTO.getNickname();
        my_nickname.setText(nickname);


        imageView6 = findViewById(R.id.imageView6);
        Glide.with(this).load(loginDTO.getdbImgPath()).circleCrop().into(imageView6);
        modify = findViewById(R.id.my_modify);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInfo.this, ModifyMyInfo.class);
                startActivity(intent);
            }
        });
        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInfo.this, TeacherDetail.class);
                startActivity(intent);
            }
        });
        
        //로그아웃
        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //카카오 로그아웃
                if (loginDTO.getKakao_login().equals("1") && loginDTO.getNaver_login().equals("0")){
                    UserManagement.getInstance().requestLogout(new LogoutResponseCallback() { //로그아웃 실행
                        @Override
                        public void onCompleteLogout() {
                            //로그아웃 성공 시 로그인 화면(LoginActivity)로 이동
                            SharedPreferences kakaoLogin = getSharedPreferences("kakaoLogin", MODE_PRIVATE);
                            SharedPreferences.Editor editor = kakaoLogin.edit();
                            editor.clear();
                            editor.commit();

                            loginDTO = null;
                            Intent intent = new Intent(MyInfo.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    });
                }

                //일반 로그아웃
                if(loginDTO.getKakao_login().equals("0") && loginDTO.getNaver_login().equals("0")){
                    SharedPreferences normalLogin = getSharedPreferences("normalLogin", MODE_PRIVATE);
                    SharedPreferences.Editor editor = normalLogin.edit();
                    editor.clear();
                    editor.commit();

                    loginDTO = null;
                    Intent intent = new Intent(MyInfo.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

                if(loginDTO.getKakao_login().equals("0") && loginDTO.getNaver_login().equals("1")){
                    SharedPreferences naverLogin = getSharedPreferences("naverLogin", MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = naverLogin.edit();
                    editor1.clear();
                    editor1.commit();

                    SharedPreferences naverDTO = getSharedPreferences("naverDTO", MODE_PRIVATE);
                    SharedPreferences.Editor editor2 = naverDTO.edit();
                    editor2.clear();
                    editor2.commit();

                    //mOAuthLoginModule.logout(MyInfo.this);
                    loginDTO = null;
                    Intent intent = new Intent(MyInfo.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });



        
        //화면이동
        matching = findViewById(R.id.my_matching);
        talk = findViewById(R.id.my_talk);
        board = findViewById(R.id.my_board);
        my = findViewById(R.id.my_my);

        matching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInfo.this, Matching.class);
                startActivity(intent);
            }
        });

        talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInfo.this, ChatListActivity.class);
                startActivity(intent);
            }
        });

        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInfo.this, Board.class);
                startActivity(intent);
            }
        });


    }
}