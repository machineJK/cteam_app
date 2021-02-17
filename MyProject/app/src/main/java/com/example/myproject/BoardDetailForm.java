package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static com.example.myproject.Common.Common.selItem;
import static com.example.myproject.Common.Common.selItem3;

public class BoardDetailForm extends AppCompatActivity {
    ImageView brd_detail_id_img,brd_detail_image;
    TextView brd_detail_nickname,brd_detail_date,brd_content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail_form);

        brd_detail_id_img = findViewById(R.id.brd_detail_id_img);
        brd_detail_nickname = findViewById(R.id.brd_detail_nickname);
        brd_detail_date = findViewById(R.id.brd_detail_date);
        brd_content = findViewById(R.id.brd_content);
        brd_detail_image = findViewById(R.id.brd_detail_image);
        Glide.with(this).load(selItem3.getId_image_path()).circleCrop().into(brd_detail_id_img);

        brd_detail_nickname.setText(selItem3.getBoard_nickname());
        brd_detail_date.setText(selItem3.getBoard_write_date());
        brd_content.setText(selItem3.getBoard_content());

        if(selItem3.getBoard_image_path() != null){
            Glide.with(this).load(selItem3.getBoard_image_path()).into(brd_detail_image);
        }

    }
}