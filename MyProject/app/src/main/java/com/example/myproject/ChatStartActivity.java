package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.myproject.Common.Common.loginDTO;
import static com.example.myproject.Common.Common.selItem;

import com.example.myproject.Adapter.ChatAdpter;
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


public class ChatStartActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ChatDTO> chatDTOList;

    private EditText edt_chat;
    private Button btn_send;

    private DatabaseReference myRef;
    private DatabaseReference toRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_start);

        btn_send = findViewById(R.id.btn_send);
        edt_chat = findViewById(R.id.edt_chat);

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
        myRef = database.getReference(loginDTO.getId()).child(selItem.getTeacher_id());
        toRef = database.getReference(selItem.getTeacher_id()).child(loginDTO.getId());

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