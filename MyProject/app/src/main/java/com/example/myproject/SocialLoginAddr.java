package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import static com.example.myproject.Common.Common.socialDTO;

public class SocialLoginAddr extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_login_addr);

        Toast.makeText(this, "" + socialDTO.getId(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "" + socialDTO.getId(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "" + socialDTO.getEmail(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "" + socialDTO.getNickname(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "" + socialDTO.getName(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "" + socialDTO.getGender(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "" + socialDTO.getBirth(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "" + socialDTO.getdbImgPath(), Toast.LENGTH_SHORT).show();
        /*socialDTO.getId();
        socialDTO.getEmail();
        socialDTO.getNickname();
        socialDTO.getName();
        socialDTO.getGender();
        socialDTO.getBirth();
        socialDTO.getdbImgPath();*/
    }
}