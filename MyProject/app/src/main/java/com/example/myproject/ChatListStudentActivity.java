package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.myproject.Common.Common.loginDTO;
import static com.example.myproject.Common.Common.selItem;
import static com.example.myproject.Common.Common.selItem2;

public class ChatListStudentActivity extends AppCompatActivity {

    ImageButton matching,talk,board,my;
    Button btn_teacherList;

    private ListView chat_studentList;
    private String teacher ;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list_student);

        databaseReference = firebaseDatabase.getReference(loginDTO.getId() + "1");
        chat_studentList = findViewById(R.id.chat_studentList);

        showChatList();

        btn_teacherList = findViewById(R.id.btn_teacherList);
        matching = findViewById(R.id.talk_matching);
        talk = findViewById(R.id.talk_talk);
        board = findViewById(R.id.talk_board);
        my = findViewById(R.id.talk_my);

        btn_teacherList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatListStudentActivity.this, ChatListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        matching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatListStudentActivity.this, Matching.class);
                startActivity(intent);
                finish();
            }
        });

        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatListStudentActivity.this, Board.class);
                startActivity(intent);
                finish();
            }
        });

        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginDTO.getId().equals("admin")){
                    Intent intent = new Intent(ChatListStudentActivity.this, AdminMyInfo.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(ChatListStudentActivity.this, MyInfo.class);
                    startActivity(intent);
                    finish();
                }


            }
        });
    }

    private void showChatList() {
        // 리스트 어댑터 생성 및 세팅
        final ArrayAdapter<String> adapter
                = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        chat_studentList.setAdapter(adapter);

        chat_studentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChatListStudentActivity.this , ChatStartStudentActivity.class);
                intent.putExtra("chatSelect", adapter.getItem(position).toString());
                startActivity(intent);

            }
        });

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                adapter.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}