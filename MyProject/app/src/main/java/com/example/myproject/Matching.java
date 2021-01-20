package com.example.myproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.Adapter.MyRecyclerviewAdapter;
import com.example.myproject.Atask.ListSelect;
import com.example.myproject.Dto.TeacherDTO;

import java.util.ArrayList;
import static com.example.myproject.Common.Common.isNetworkConnected;

import static com.example.myproject.Common.Common.loginDTO;

//과외 매칭
public class Matching extends AppCompatActivity {

    Button teacher, student, add, matching, talk, board, my;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    ArrayList<TeacherDTO> myItemArrayList;
    MyRecyclerviewAdapter adapter;
    ListSelect listSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);

        // 리사이클러 뷰 시작
        myItemArrayList = new ArrayList();
        adapter = new MyRecyclerviewAdapter(this, myItemArrayList);
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        if(isNetworkConnected(this) == true){
            listSelect = new ListSelect(myItemArrayList, adapter, progressDialog);
            listSelect.execute();
        }else {
            Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }



















        teacher = findViewById(R.id.teacher);
        student = findViewById(R.id.student);
        add = findViewById(R.id.add2);

        //과외 등록 했을 때 액티비티 넘어가는 것!!!!
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Matching.this, TeacherForm.class);
                startActivity(intent);
            }
        });


        //학생 등록 화면 이동
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Matching.this, MatchingStudent.class);
                startActivity(intent);
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
                Intent intent = new Intent(Matching.this, Chat.class);
                startActivity(intent);
            }
        });

        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Matching.this, Board.class);
                startActivity(intent);
            }
        });

        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Matching.this, MyInfo.class);
                startActivity(intent);
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
            listSelect = new ListSelect(myItemArrayList, adapter, progressDialog);
            listSelect.execute();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }






}