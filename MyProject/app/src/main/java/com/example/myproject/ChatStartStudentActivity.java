package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myproject.Atask.FirebaseNotification;
import com.example.myproject.Adapter.ChatAdpter;
import com.example.myproject.Atask.SetMatch;
import com.example.myproject.Dto.ChatDTO;
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

import static com.example.myproject.Common.Common.loginDTO;
import static com.example.myproject.Common.Common.selItem2;
import static com.example.myproject.Common.Common.myDetail;

public class ChatStartStudentActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ChatDTO> chatDTOList;

    private EditText edt_chat;
    private Button btn_send;
    private String student;

    private DatabaseReference myRef;
    private DatabaseReference toRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_start_student);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.cchat);

        String msgRequire = myDetail.getTeacher_pay();

        Intent intent = getIntent();
        student = intent.getStringExtra("chatSelect");

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

                    FirebaseNotification firebaseNotification = new FirebaseNotification(selItem2.getStudent_id(), dto);
                    firebaseNotification.execute();
                }

            }
        });

        mRecyclerView = findViewById(R.id.rcview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        chatDTOList = new ArrayList<>();
        mAdapter = new ChatAdpter(chatDTOList , ChatStartStudentActivity.this , loginDTO.getName());
        mRecyclerView.setAdapter(mAdapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        if(student == null || student.length() < 1){
            myRef = database.getReference(loginDTO.getId() + "1").child(selItem2.getStudent_id() + "2");
            toRef = database.getReference(selItem2.getStudent_id() + "2").child(loginDTO.getId() + "1");
        }else {
            myRef = database.getReference(loginDTO.getId() + "1").child(student);
            toRef = database.getReference(student).child(loginDTO.getId() + "1");
        }


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