package com.example.myproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.myproject.Adapter.ChatListAdapter;
import com.example.myproject.Adapter.MyRecyclerviewAdapter;
import com.example.myproject.Adapter.MyRecyclerviewAdapter2;
import com.example.myproject.Atask.StudentListSelect;
import com.example.myproject.Atask.TeacherListSelect;
import com.example.myproject.Dto.ChatListDTO;
import com.example.myproject.Dto.StudentDTO;
import com.example.myproject.Dto.TeacherDTO;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.myproject.Common.Common.loginDTO;
import static com.example.myproject.Common.Common.selItem;
import static com.example.myproject.Common.Common.selItem2;

public class ChatListStudentActivity extends AppCompatActivity {

    ImageButton matching,talk,board,my;
    Button btn_teacherList;

    private ListView chat_studentList;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    StudentListSelect listSelect_s;
    ArrayList<StudentDTO> myItemArrayList_s;
    MyRecyclerviewAdapter2 adapter_s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list_student);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.cchat);

        adapter_s = new MyRecyclerviewAdapter2(this, myItemArrayList_s);
        myItemArrayList_s = new ArrayList<>();
        listSelect_s = new StudentListSelect(myItemArrayList_s,adapter_s);
        try {
            listSelect_s.execute().get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        databaseReference = firebaseDatabase.getReference(loginDTO.getId() + "1");
        chat_studentList = findViewById(R.id.chat_studentList);
        dtos = new ArrayList<>();
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
    ChatListAdapter adapter;
    ArrayList<ChatListDTO> dtos;
    ChatListDTO dto;
    private void showChatList() {

        adapter = new ChatListAdapter(ChatListStudentActivity.this, dtos);

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

                chat_studentList.setAdapter(adapter);
                chat_studentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ChatListDTO dto = (ChatListDTO) adapter.getItem(position);
                        String teacherId = dto.getId();
                       /* selItem.setTeacher_nickname(dto.getNickname());
                        selItem.setTeacher_id(dto.getId().substring(0, lengthId));
                        Log.d("chat", "onItemClick: " + selItem);*/
                        for (int i=0 ; i<myItemArrayList_s.size(); i++){
                            if (teacherId.equals(myItemArrayList_s.get(i).getStudent_id() + "2")){
                                selItem2 = myItemArrayList_s.get(i);
                            }
                        }
                        Intent intent = new Intent(ChatListStudentActivity.this , ChatStartStudentActivity.class);
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