package com.example.myproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myproject.Adapter.BoardAdapter;
import com.example.myproject.Adapter.CommentAdapter;
import com.example.myproject.Atask.BoardDelete;
import com.example.myproject.Atask.BoardInsert2;
import com.example.myproject.Atask.BoardListSelect;
import com.example.myproject.Atask.CommentListSelect;
import com.example.myproject.Atask.JoinInsert;
import com.example.myproject.Atask.ReadCount;
import com.example.myproject.Dto.BoardDTO;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.myproject.Common.Common.isNetworkConnected;
import static com.example.myproject.Common.Common.loginDTO;
import static com.example.myproject.Common.Common.selItem3;

public class BoardDetailForm extends AppCompatActivity {
    ImageView brd_detail_id_img,brd_detail_image;
    TextView brd_detail_nickname,brd_detail_date,brd_content,brd_read_count;
    EditText et_comment;
    Button btnComment,btnModify,btnDelete,btnBoardList;
    LinearLayout modifyDeleteLayout;

    RecyclerView recyclerView;
    ArrayList<BoardDTO> brdArrayList;
    CommentAdapter adapter;
    CommentListSelect listSelect;
    String state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail_form);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.cboard);

        brd_detail_id_img = findViewById(R.id.brd_detail_id_img);
        brd_detail_nickname = findViewById(R.id.brd_detail_nickname);
        brd_detail_date = findViewById(R.id.brd_detail_date);
        brd_content = findViewById(R.id.brd_content);
        brd_detail_image = findViewById(R.id.brd_detail_image);
        brd_read_count = findViewById(R.id.brd_read_count);
        Glide.with(this).load(selItem3.getId_image_path()).circleCrop().into(brd_detail_id_img);
        //Glide.with(this).load(loginDTO.getdbImgPath()).circleCrop().into(brd_detail_id_img);

        //조회수 증가
        ReadCount readCount = new ReadCount(selItem3.getQna_ref_num());

        try{
            state = readCount.execute().get().trim();
            Log.d("main:joinact0 : ", state);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ////////////

        brd_detail_nickname.setText(selItem3.getBoard_nickname());
        brd_detail_date.setText(selItem3.getBoard_write_date());
        brd_content.setText(selItem3.getBoard_content());
        brd_read_count.setText("" + selItem3.getBoard_readcount());

        if(selItem3.getBoard_image_path() != null){
            if(! selItem3.getBoard_image_path().contains("http") ){
                selItem3.setBoard_image_path("http://112.164.58.217:8080/tutors/" + selItem3.getBoard_image_path());
            }
            Glide.with(this).load(selItem3.getBoard_image_path()).into(brd_detail_image);
        }

        //게시글과 같은 아이디이거나 아이디가 admin일때 수정삭제 가능
        modifyDeleteLayout = findViewById(R.id.modifyDeleteLayout);
        btnModify = findViewById(R.id.btnModify);
        btnDelete = findViewById(R.id.btnDelete);
        
        if(loginDTO.getId().equals(selItem3.getBoard_id()) || loginDTO.getId().equals("admin")){
            //수정 삭제 버튼 보이게 하기
            modifyDeleteLayout.setVisibility(View.VISIBLE);
            
            //게시글 삭제
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BoardDelete boardDelete = new BoardDelete(selItem3.getQna_ref_num());

                    try{
                        boardDelete.execute().get().trim();
                        Intent intent = new Intent(BoardDetailForm.this,Board.class);
                        startActivity(intent);
                        finish();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
            
            //게시글 수정
            btnModify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(BoardDetailForm.this, BoardModify.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        //목록
        btnBoardList = findViewById(R.id.btnBoardList);
        btnBoardList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BoardDetailForm.this, Board.class);
                startActivity(intent);
                finish();
            }
        });


        //답글 리사이클러 뷰
        brdArrayList = new ArrayList<>();
        adapter = new CommentAdapter(this, brdArrayList); //수정
        recyclerView = findViewById(R.id.recyclerView_comment);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        //원본 글의 번호
        int postOriginal = selItem3.getQna_ref_num();
        
        if(isNetworkConnected(this) == true){
            listSelect = new CommentListSelect( brdArrayList, adapter,postOriginal);   //수정
            listSelect.execute();   //수정
        }else {
            Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }

        //답글 내용
        et_comment = findViewById(R.id.et_comment);

        //답글 등록 버튼
        btnComment = findViewById(R.id.btnComment);
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String board_id = loginDTO.getId();
                String board_content = et_comment.getText().toString();
                String id_image_path = loginDTO.getdbImgPath();
                String board_nickname = loginDTO.getNickname();
                String isComment = "y";

                BoardDTO dto = new BoardDTO();
                dto.setBoard_id(board_id);
                dto.setBoard_content(board_content);
                dto.setId_image_path(id_image_path);
                dto.setBoard_nickname(board_nickname);

                int board_notice = 0;
                if(loginDTO.getId().equals("admin")) {
                    board_notice = 1;
                }

                BoardInsert2 brdInsert2 = new BoardInsert2(board_id,board_nickname,board_content,
                        id_image_path,board_notice,isComment,postOriginal);
                try {
                    String state;
                    state = brdInsert2.execute().get().trim();

                    adapter.updateList(dto);
                    et_comment.setText("");
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}