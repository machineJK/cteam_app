package com.example.myproject.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.Dto.MatchingDTO;
import com.example.myproject.R;

import java.util.ArrayList;

import static com.example.myproject.Common.Common.selItem5;

public class Matched_RV_Adapter extends RecyclerView.Adapter<Matched_RV_Adapter.ItemViewHolder>{

    Context mContext;
    ArrayList<MatchingDTO> arrayList;

    public Matched_RV_Adapter(Context mContext, ArrayList<MatchingDTO> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Matched_RV_Adapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.matched, parent, false);

        return new Matched_RV_Adapter.ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        Log.d("main:adapter", "" + position);

        MatchingDTO item = arrayList.get(position);
        holder.setItem(item);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selItem5 = arrayList.get(position);
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
    public MatchingDTO getItem(int position) {
        return arrayList.get(position);
    }

    // 특정 인덱스 항목 셋팅하기
    public void setItem(int position, MatchingDTO item){
        arrayList.set(position, item);
    }

    // arrayList 통째로 셋팅하기
    public void setItems(ArrayList<MatchingDTO> arrayList){
        this.arrayList = arrayList;
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout parentLayout;
        public TextView student_nickname,teacher_nickname;

        public ItemViewHolder(@NonNull final View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parentLayout);
            teacher_nickname = itemView.findViewById(R.id.tv_teacher_nickname);
            student_nickname = itemView.findViewById(R.id.tv_student_nickname);
        }

        public void setItem(MatchingDTO dto){
            teacher_nickname.setText(dto.getTeacher_nickname());
            student_nickname.setText(dto.getStudent_nickname());
        }
    }



}
