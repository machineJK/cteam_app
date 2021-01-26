package com.example.myproject.Adapter;

import android.content.Context;
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
import com.example.myproject.Dto.StudentDTO;
import com.example.myproject.R;

import java.util.ArrayList;

import static com.example.myproject.Common.Common.loginDTO;
import static com.example.myproject.Common.Common.selItem2;


public class MyRecyclerviewAdapter2 extends RecyclerView.Adapter<MyRecyclerviewAdapter2.ItemViewHolder>{
    private static final String TAG = "MyRecyclerviewAdapter2";

    Context mContext;
    ArrayList<StudentDTO> arrayList;

    public MyRecyclerviewAdapter2(Context mContext, ArrayList<StudentDTO> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.studentlist_view, parent, false);

        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        Log.d("main:adapter", "" + position);

        StudentDTO item = arrayList.get(position);
        holder.setItem(item);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: " + position);

                selItem2 = arrayList.get(position);

                Toast.makeText(mContext, "img_path : " + arrayList.get(position).getStudent_image_path(), Toast.LENGTH_SHORT).show();
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
    public StudentDTO getItem(int position) {
        return arrayList.get(position);
    }

    // 특정 인덱스 항목 셋팅하기
    public void setItem(int position, StudentDTO item){
        arrayList.set(position, item);
    }

    // arrayList 통째로 셋팅하기
    public void setItems(ArrayList<StudentDTO> arrayList){
        this.arrayList = arrayList;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout parentLayout;
        public TextView student_grade;
        public TextView student_address;
        public TextView student_subject;
        public ImageView student_picture;
        public ProgressBar progressBar;

        public ItemViewHolder(@NonNull final View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parentLayout);
            student_grade = itemView.findViewById(R.id.tv_grade);
            student_address = itemView.findViewById(R.id.tv_address);
            student_subject = itemView.findViewById(R.id.tv_subject2);
            student_picture = itemView.findViewById(R.id.tv_image);
            progressBar = itemView.findViewById(R.id.progressBar);

        }

        public void setItem(StudentDTO dto){

            student_grade.setText(dto.getStudent_grade());
            student_address.setText(loginDTO.getAddr1() + " " + loginDTO.getAddr2());
            student_subject.setText(dto.getStudent_subject());

            Glide.with(itemView).load(dto.getStudent_image_path()).into(student_picture);
        }
    }

}


