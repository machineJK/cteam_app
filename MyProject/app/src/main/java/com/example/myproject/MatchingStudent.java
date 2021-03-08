package com.example.myproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myproject.Adapter.MyRecyclerviewAdapter2;
import com.example.myproject.Atask.StudentListSelect;
import com.example.myproject.Dto.StudentDTO;

import java.util.ArrayList;

import static com.example.myproject.Common.Common.isNetworkConnected;
import static com.example.myproject.Common.Common.loginDTO;

public class MatchingStudent extends AppCompatActivity implements TextWatcher {
    Button add2, teacher2;
    ImageButton matching, talk, board, my;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    ArrayList<StudentDTO> myItemArrayList;
    MyRecyclerviewAdapter2 adapter;
    StudentListSelect listSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_student);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.cmatching);


        // 리사이클러 뷰 시작
        myItemArrayList = new ArrayList();
        adapter = new MyRecyclerviewAdapter2(this, myItemArrayList);
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        if(isNetworkConnected(this) == true){
            listSelect = new StudentListSelect(myItemArrayList, adapter, progressDialog);
            listSelect.execute();
        }else {
            Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }










        //학생 등록하기
        add2 = findViewById(R.id.add2);
        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatchingStudent.this, StudentForm.class);
                startActivity(intent);
                finish();
            }
        });
        
        //선생화면 이동
        teacher2 = findViewById(R.id.teacher);
        teacher2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatchingStudent.this, Matching.class);
                startActivity(intent);
                finish();
            }
        });

        
        //화면 이동
        matching = findViewById(R.id.matchingst_matching);
        talk = findViewById(R.id.matchingst_talk);
        board = findViewById(R.id.matchingst_board);
        my = findViewById(R.id.matchingst_my);

        talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatchingStudent.this, ChatListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatchingStudent.this, Board.class);
                startActivity(intent);
                finish();
            }
        });

        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginDTO.getId().equals("admin")){
                    Intent intent = new Intent(MatchingStudent.this, AdminMyInfo.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(MatchingStudent.this, MyInfo.class);
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
            listSelect = new StudentListSelect(myItemArrayList, adapter, progressDialog);
            listSelect.execute();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        adapter.getFilter().filter(charSequence);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}