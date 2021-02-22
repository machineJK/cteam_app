package com.example.myproject;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.Adapter.MyRecyclerviewAdapter;
import com.example.myproject.Atask.MyTeacherDetail;
import com.example.myproject.Atask.SetToken;
import com.example.myproject.Atask.TeacherListSelect;
import com.example.myproject.Dto.TeacherDTO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


import static com.example.myproject.Common.Common.isNetworkConnected;
import static com.example.myproject.Common.Common.loginDTO;
import static com.example.myproject.Common.Common.myDetail;

//과외 매칭
public class Matching extends AppCompatActivity implements TextWatcher {

    private String TAG = "Matching";

    Button teacher, student, add;
    ImageButton matching, talk, board, my;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    ArrayList<TeacherDTO> myItemArrayList;
    MyRecyclerviewAdapter adapter;
    TeacherListSelect listSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);

        MyTeacherDetail myTeacherDetail = new MyTeacherDetail(loginDTO.getId());
        try {
            myTeacherDetail.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //token 얻어오기
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                    return;
                }

                // Get new FCM registration token
                String token = task.getResult();

                // Log and toast
                String msg = getString(R.string.msg_token_fmt, token);
                //Log.d(TAG, msg);
                //Toast.makeText(Matching.this, msg, Toast.LENGTH_SHORT).show();

                SetToken setToken = new SetToken(loginDTO.getId(), token);
                try {
                    String state = setToken.execute().get();
                    Log.d(TAG, state);

                } catch (Exception e){
                    Log.d(TAG, e.getMessage());
                }

            }
        });

        //데이터 체크용
        Log.d("kakanav", "id : " + loginDTO.getId());
        Log.d("kakanav", "pw : " + loginDTO.getPw());
        Log.d("kakanav", "nickname : " + loginDTO.getNickname());
        Log.d("kakanav", "name : " + loginDTO.getName());
        Log.d("kakanav", "gender : " + loginDTO.getGender());
        Log.d("kakanav", "birth : " + loginDTO.getBirth());
        Log.d("kakanav", "email : " + loginDTO.getEmail());
        Log.d("kakanav", "addr1 : " + loginDTO.getAddr1());
        Log.d("kakanav", "addr2 : " + loginDTO.getAddr2());
        Log.d("kakanav", "kakao : " + loginDTO.getKakao_login());
        Log.d("kakanav", "naver : " + loginDTO.getNaver_login());
        Log.d("kakanav", "img : " + loginDTO.getdbImgPath());

        // 리사이클러 뷰 시작
        myItemArrayList = new ArrayList();
        adapter = new MyRecyclerviewAdapter(this, myItemArrayList);
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        if(isNetworkConnected(this) == true){
            listSelect = new TeacherListSelect(myItemArrayList, adapter, progressDialog);
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
                finish();
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
                Intent intent = new Intent(Matching.this, ChatListActivity.class);
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
        Toast.makeText(this, "onNewIntent다시부름", Toast.LENGTH_SHORT).show();
        super.onNewIntent(intent);

    }

    private void processIntent(Intent intent){
        if(intent != null){
            listSelect = new TeacherListSelect(myItemArrayList, adapter, progressDialog);
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