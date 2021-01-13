package com.example.myproject;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MyInfo extends AppCompatActivity {

    Button button13;
    ImageView imageView6;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        button13 = findViewById(R.id.button13);
        imageView6 = findViewById(R.id.imageView6);
        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.myproject.MyInfo.this, ModifyMyInfo.class);
                startActivity(intent);
            }
        });
    }
}