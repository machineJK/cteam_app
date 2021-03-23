package com.example.myproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myproject.Atask.BoardDelete;
import com.example.myproject.Board;
import com.example.myproject.BoardDetailForm;
import com.example.myproject.Dto.BoardDTO;
import com.example.myproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.myproject.Common.Common.loginDTO;
import static com.example.myproject.Common.Common.selItem3;
import static com.example.myproject.Common.Common.selItem7;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ItemViewHolder>{

    Context mContext;
    ArrayList<BoardDTO> arrayList;

    public CommentAdapter(Context mContext, ArrayList<BoardDTO> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.comment_view, parent, false);

        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        Log.d("main:Boardadapter", "" + position);

        BoardDTO item = arrayList.get(position);
        holder.setItem(item);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selItem7 = arrayList.get(position);
            }

        });

        holder.btnCommentDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selItem7 = arrayList.get(position);
                BoardDelete boardDelete = new BoardDelete(selItem7.getQna_ref_num(), "y",loginDTO.getId());
                try{
                    boardDelete.execute().get().trim();
                    Toast.makeText(mContext, "삭제 성공", Toast.LENGTH_SHORT).show();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    // 업데이트
    public void updateList (BoardDTO bdto) {
        arrayList.add(0,bdto);
        notifyDataSetChanged();
    }


    // 리사이클러뷰 내용 모두 지우기
    public void removeAllItem(){
        arrayList.clear();
    }

    // 특정 인덱스 항목 가져오기
    public BoardDTO getItem(int position) {
        return arrayList.get(position);
    }

    // 특정 인덱스 항목 셋팅하기
    public void setItem(int position, BoardDTO item){
        arrayList.set(position, item);
    }

    // arrayList 통째로 셋팅하기
    public void setItems(ArrayList<BoardDTO> arrayList){
        this.arrayList = arrayList;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout parentLayout,commentDeleteLayout;
        public TextView brd_nickname;
        public TextView brd_content;
        public TextView brd_date;
        public ImageView board_picture;
        public Button btnCommentDelete;


        public ItemViewHolder(@NonNull final View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.brd_parentLayout);
            commentDeleteLayout = itemView.findViewById(R.id.commentDeleteLayout);
            brd_nickname = itemView.findViewById(R.id.brd_nickname);
            brd_content = itemView.findViewById(R.id.brd_content);
            brd_date = itemView.findViewById(R.id.brd_date);
            board_picture = itemView.findViewById(R.id.brd_detail_id_img);
            btnCommentDelete = itemView.findViewById(R.id.btnCommentDelete);
        }

        public void setItem(BoardDTO dto){

            brd_nickname.setText(dto.getBoard_nickname());
            brd_content.setText(dto.getBoard_content());
            brd_date.setText(dto.getBoard_write_date());
            if(! dto.getId_image_path().contains("http") ){
                dto.setId_image_path("http://112.164.58.217:8080/tutors/" + dto.getId_image_path());
            }
            /*if(loginDTO.getId().equals(dto.getBoard_id()) || loginDTO.getId().equals("admin")){
                commentDeleteLayout.setVisibility(View.VISIBLE);
            }*/
            Glide.with(itemView).load(dto.getId_image_path()).circleCrop().into(board_picture);
        }
    }

}



