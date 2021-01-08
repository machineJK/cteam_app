package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

//로그인 화면 전 인트로화면 (3초 떠있다가 넘어감)
public class Intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(com.example.cteam_app.Intro.this, com.example.cteam_app.LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}