package com.example.myproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myproject.Adapter.BoardAdapter;
import com.example.myproject.Atask.BoardListSelect;
import com.example.myproject.Dto.BoardDTO;
import com.example.myproject.Dto.MemberDTO;

import java.util.ArrayList;

import static com.example.myproject.Common.Common.isNetworkConnected;
import static com.example.myproject.Common.Common.loginDTO;

public class Board extends AppCompatActivity {

    ImageButton btnQnA;
    ImageButton matching, talk, board, my;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    ArrayList<BoardDTO> brdArrayList;
    BoardAdapter adapter;
    BoardListSelect listSelect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.cboard);

        // 리사이클러 뷰 시작
        brdArrayList = new ArrayList<>();
        adapter = new BoardAdapter(this, brdArrayList);
        recyclerView = findViewById(R.id.recyclerView_brd);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
        if(isNetworkConnected(this) == true){
            listSelect = new BoardListSelect( brdArrayList, adapter, progressDialog);
            listSelect.execute();
        }else {
            Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }

        
        //글쓰기 화면 이동
        findViewById(R.id.btn_brd_QnA2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Board.this, Board_Write.class);
                startActivity(intent);
                finish();
            }
        });

        //하단 화면이동
        matching = findViewById(R.id.matchingst_matching);
        talk = findViewById(R.id.matchingst_talk);
        board = findViewById(R.id.matchingst_board);
        my = findViewById(R.id.matchingst_my);

        talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Board.this, ChatListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        matching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Board.this, Matching.class);
                startActivity(intent);
                finish();
            }
        });

        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginDTO.getId().equals("admin")){
                    Intent intent = new Intent(Board.this, AdminMyInfo.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(Board.this, MyInfo.class);
                    startActivity(intent);
                    finish();
                }


            }
        });
    }

    // 이미 화면이 있을때 받는곳
    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("Sub1", "onNewIntent() 호출됨");

        // 새로고침하면서 이미지가 겹치는 현상 없애기 위해...
        adapter.removeAllItem();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("데이터 업로딩");
        progressDialog.setMessage("데이터 업로딩 중입니다\n" + "잠시만 기다려주세요 ...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        processIntent(intent);

        super.onNewIntent(intent);

    }

    private void processIntent(Intent intent){
        if(intent != null){
            listSelect = new BoardListSelect(brdArrayList, adapter, progressDialog);
            listSelect.execute();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


}