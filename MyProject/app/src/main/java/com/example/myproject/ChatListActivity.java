package com.example.myproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import static com.example.myproject.Common.Common.loginDTO;
import static com.example.myproject.Common.Common.selItem;

import com.example.myproject.Adapter.ChatListAdapter;
import com.example.myproject.Adapter.MyRecyclerviewAdapter;
import com.example.myproject.Atask.TeacherListSelect;
import com.example.myproject.Dto.ChatListDTO;
import com.example.myproject.Dto.TeacherDTO;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ChatListActivity extends AppCompatActivity {
    ImageButton matching,talk,board,my;
    Button btn_studentList;

    private ListView chat_teacherList;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    TeacherListSelect listSelect_t;
    ArrayList<TeacherDTO> myItemArrayList_t;
    MyRecyclerviewAdapter adapter_t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.cchat);

        adapter_t =    new MyRecyclerviewAdapter(this, myItemArrayList_t);
        myItemArrayList_t = new ArrayList<>();
        listSelect_t = new TeacherListSelect(myItemArrayList_t,adapter_t);
        try {
            listSelect_t.execute().get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        databaseReference = firebaseDatabase.getReference(loginDTO.getId() + "2");
        chat_teacherList = findViewById(R.id.chat_teacherList);
        dtos = new ArrayList<>();
        showChatList();

        btn_studentList = findViewById(R.id.btn_studentList);
        matching = findViewById(R.id.talk_matching);
        talk = findViewById(R.id.talk_talk);
        board = findViewById(R.id.talk_board);
        my = findViewById(R.id.talk_my);

        btn_studentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatListActivity.this, ChatListStudentActivity.class);
                startActivity(intent);
                finish();
            }
        });

        matching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatListActivity.this, Matching.class);
                startActivity(intent);
                finish();
            }
        });

        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatListActivity.this, Board.class);
                startActivity(intent);
                finish();
            }
        });

        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginDTO.getId().equals("admin")){
                    Intent intent = new Intent(ChatListActivity.this, AdminMyInfo.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(ChatListActivity.this, MyInfo.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }


    ChatListAdapter adapter;
    ArrayList<ChatListDTO> dtos;
    ChatListDTO dto;
    private void showChatList() {

        adapter = new ChatListAdapter(ChatListActivity.this, dtos);

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.addChildEventListener(new ChildEventListener() {

            String id = "";
            String nickname = "";
            String myName = "";
            String myId = "";

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                id = dataSnapshot.getKey();
                myName = loginDTO.getNickname();
                myId = loginDTO.getId();
                for (DataSnapshot snapshot : dataSnapshot.getChildren() ) {
                    nickname = snapshot.child("nickname").getValue().toString();
                    if(!nickname.equals(myName)){
                        Log.d("chat", "onChildAdded: " + nickname);
                        Log.d("chat", "onChildAdded: " + id);

                        dto = new ChatListDTO(id, nickname);
                        adapter.addDTO(dto);
                        Log.d("chat", "ondto size: " + dtos.size());
                        break;
                    }
                }

                chat_teacherList.setAdapter(adapter);
                chat_teacherList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ChatListDTO dto = (ChatListDTO) adapter.getItem(position);
                        String teacherId = dto.getId();
                       /* selItem.setTeacher_nickname(dto.getNickname());
                        selItem.setTeacher_id(dto.getId().substring(0, lengthId));
                        Log.d("chat", "onItemClick: " + selItem);*/
                        for (int i=0 ; i<myItemArrayList_t.size(); i++){
                            if (teacherId.equals(myItemArrayList_t.get(i).getTeacher_id() + "1")){
                                selItem = myItemArrayList_t.get(i);
                            }
                        }
                        Intent intent = new Intent(ChatListActivity.this , ChatStartActivity.class);
                        startActivity(intent);

                    }
                });
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