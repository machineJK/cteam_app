package com.example.myproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.myproject.Adapter.AdminMatching_RV_Adapter;
import com.example.myproject.Atask.AdminMatchingListSelect;
import com.example.myproject.Dto.MatchingDTO;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.myproject.Common.Common.isNetworkConnected;
import static com.example.myproject.Common.Common.loginDTO;

public class AdminMyInfo extends AppCompatActivity {

    ImageButton matching, talk, board, my;
    Button btnLogout;
    ImageView imageView6;

    RecyclerView recyclerView;
    ArrayList<MatchingDTO> myItemArrayList;
    AdminMatching_RV_Adapter adapter;
    AdminMatchingListSelect listSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_my_info);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.cadmin);


        // 매칭희망 리사이클러 뷰 시작
        myItemArrayList = new ArrayList();
        adapter = new AdminMatching_RV_Adapter(this, myItemArrayList);
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        if(isNetworkConnected(this) == true){
            listSelect = new AdminMatchingListSelect(myItemArrayList, adapter);
            listSelect.execute();
        }else {
            Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }

        imageView6 = findViewById(R.id.imageView6);
        if( !loginDTO.getdbImgPath().contains("http") ){
            loginDTO.setdbImgPath("http://112.164.58.217:8080/tutors/" + loginDTO.getdbImgPath());
        }
        Glide.with(this).load(loginDTO.getdbImgPath()).into(imageView6);

        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //일반 로그아웃
                if(loginDTO.getKakao_login().equals("0") && loginDTO.getNaver_login().equals("0")){
                    SharedPreferences normalLogin = getSharedPreferences("normalLogin", MODE_PRIVATE);
                    SharedPreferences.Editor editor = normalLogin.edit();
                    editor.clear();
                    editor.commit();

                    loginDTO = null;
                    Intent intent = new Intent(AdminMyInfo.this, LoginActivity.class);
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
                Intent intent = new Intent(AdminMyInfo.this, Matching.class);
                startActivity(intent);
                finish();
            }
        });

        talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMyInfo.this, ChatListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMyInfo.this, Board.class);
                startActivity(intent);
                finish();
            }
        });
    }
}