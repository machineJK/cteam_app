package com.example.myproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myproject.BoardDetailForm;
import com.example.myproject.Dto.BoardDTO;
import com.example.myproject.R;

import java.util.ArrayList;

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
        View itemView = inflater.inflate(R.layout.boardlist_view, parent, false);

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
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    // 어댑터에 매소드 만들기

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

        public LinearLayout parentLayout;
        public TextView brd_nickname;
        public TextView brd_content;
        public TextView brd_date;
        public ImageView board_picture;

        public ItemViewHolder(@NonNull final View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.brd_parentLayout);
            brd_nickname = itemView.findViewById(R.id.brd_nickname);
            brd_content = itemView.findViewById(R.id.brd_content);
            brd_date = itemView.findViewById(R.id.brd_date);
            board_picture = itemView.findViewById(R.id.brd_detail_id_img);
        }

        public void setItem(BoardDTO dto){

            brd_nickname.setText(dto.getBoard_nickname());
            brd_content.setText(dto.getBoard_content());
            brd_date.setText(dto.getBoard_write_date());

            Glide.with(itemView).load(dto.getId_image_path()).circleCrop().into(board_picture);
        }
    }

}



