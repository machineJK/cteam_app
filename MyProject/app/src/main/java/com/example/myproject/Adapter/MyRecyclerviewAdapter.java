package com.example.myproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myproject.Dto.TeacherDTO;
import com.example.myproject.Matching;
import com.example.myproject.R;
import com.example.myproject.TeacherDetail;

import java.util.ArrayList;

import static com.example.myproject.Common.Common.loginDTO;
import static com.example.myproject.Common.Common.selItem;


public class MyRecyclerviewAdapter extends RecyclerView.Adapter<MyRecyclerviewAdapter.ItemViewHolder>{
    private static final String TAG = "MyRecyclerviewAdapter";

    Context mContext;
    ArrayList<TeacherDTO> arrayList;

    public MyRecyclerviewAdapter(Context mContext, ArrayList<TeacherDTO> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.teacherlist_view, parent, false);

        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        Log.d("main:adapter", "" + position);

        TeacherDTO item = arrayList.get(position);
        holder.setItem(item);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: " + position);

                selItem = arrayList.get(position);

                //Toast.makeText(mContext, "teacher_id : " + arrayList.get(position).getTeacher_id(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, TeacherDetail.class);
                mContext.startActivity(intent);

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
    public TeacherDTO getItem(int position) {
        return arrayList.get(position);
    }

    // 특정 인덱스 항목 셋팅하기
    public void setItem(int position, TeacherDTO item){
        arrayList.set(position, item);
    }

    // arrayList 통째로 셋팅하기
    public void setItems(ArrayList<TeacherDTO> arrayList){
        this.arrayList = arrayList;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout parentLayout;
        public TextView teacher_addr;
        public TextView teacher_subject;
        public TextView teacher_worktime_pay;
        public ImageView teacher_picture;
        public ProgressBar progressBar;

        public ItemViewHolder(@NonNull final View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parentLayout);
            teacher_addr = itemView.findViewById(R.id.tv_addr);
            teacher_subject = itemView.findViewById(R.id.tv_subject);
            teacher_worktime_pay = itemView.findViewById(R.id.tv_worktime_pay);
            teacher_picture = itemView.findViewById(R.id.tv_img);
            progressBar = itemView.findViewById(R.id.progressBar);

        }

        public void setItem(TeacherDTO dto){

            teacher_addr.setText(dto.getTeacher_nickname() + ' ' + dto.getTeacher_addr());
            teacher_subject.setText(dto.getTeacher_subject());
            teacher_worktime_pay.setText(dto.getTeacher_worktime() + " " + dto.getTeacher_pay());

            Glide.with(itemView).load(dto.getTeacher_image_path()).into(teacher_picture);
        }
    }

}



