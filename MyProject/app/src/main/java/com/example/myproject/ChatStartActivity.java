package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.myproject.Common.Common.loginDTO;
import static com.example.myproject.Common.Common.selItem;

import com.example.myproject.Atask.FirebaseNotification;
import com.example.myproject.Adapter.ChatAdpter;
import com.example.myproject.Atask.SetMatch;
import com.example.myproject.Dto.ChatDTO;
import com.example.myproject.Dto.TeacherDTO;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class ChatStartActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ChatDTO> chatDTOList;

    private EditText edt_chat;
    private Button btn_send,btn_require;
    private String teacher;

    private DatabaseReference myRef;
    private DatabaseReference toRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_start);

        Intent intent = getIntent();
        teacher = intent.getStringExtra("chatSelect");

        btn_send = findViewById(R.id.btn_send);
        edt_chat = findViewById(R.id.edt_chat);
        btn_require = findViewById(R.id.btn_require);
        btn_require.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatDTO dto = new ChatDTO();
                dto.setNickname(loginDTO.getNickname());
                dto.setMsg(loginDTO.getNickname() + "님이 매칭을 요청하였습니다.\nMy Info 화면에서 수락 또는 거절을 눌러주세요!");

                long now = System.currentTimeMillis();
                Date mDate = new Date(now);
                SimpleDateFormat simpleDate = new SimpleDateFormat("hh:mm:aa");
                String getTime = simpleDate.format(mDate);
                dto.setDate(getTime);
                myRef.push().setValue(dto);
                toRef.push().setValue(dto);
                edt_chat.setText("");

                SetMatch setMatch = new SetMatch(selItem.getTeacher_id(),selItem.getTeacher_nickname(),loginDTO.getId(),loginDTO.getNickname());
                try {
                    setMatch.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                FirebaseNotification firebaseNotification = new FirebaseNotification(selItem.getTeacher_id(), dto);
                firebaseNotification.execute();
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = edt_chat.getText().toString();

                if(msg != null){
                    ChatDTO dto = new ChatDTO();
                    dto.setNickname(loginDTO.getName());
                    dto.setMsg(msg);
                    long now = System.currentTimeMillis();
                    Date mDate = new Date(now);
                    SimpleDateFormat simpleDate = new SimpleDateFormat("hh:mm:aa");
                    String getTime = simpleDate.format(mDate);
                    dto.setDate(getTime);
                    myRef.push().setValue(dto);
                    toRef.push().setValue(dto);
                    edt_chat.setText("");

                    FirebaseNotification firebaseNotification = new FirebaseNotification(selItem.getTeacher_id(), dto);
                    firebaseNotification.execute();
                }

            }
        });

        mRecyclerView = findViewById(R.id.rcview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        chatDTOList = new ArrayList<>();
        mAdapter = new ChatAdpter(chatDTOList , ChatStartActivity.this , loginDTO.getName());
        mRecyclerView.setAdapter(mAdapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();


        if(teacher == null || teacher.length() < 1 ){
            myRef = database.getReference(loginDTO.getId() + "2").child(selItem.getTeacher_id() + "1");
            toRef = database.getReference(selItem.getTeacher_id() + "1").child(loginDTO.getId() + "2");
        }else {
            myRef = database.getReference(loginDTO.getId() + "2").child(teacher);
            toRef = database.getReference(teacher).child(loginDTO.getId() + "2");
        }



        /*  ChatDTO dto = new ChatDTO();
        dto.setNickname(nick);
        dto.setMsg("hi");
        myRef.setValue(dto);*/

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChatDTO dto = snapshot.getValue(ChatDTO.class);
                ((ChatAdpter) mAdapter).addChat(dto);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}