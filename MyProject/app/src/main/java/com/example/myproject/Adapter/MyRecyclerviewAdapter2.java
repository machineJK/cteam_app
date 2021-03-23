package com.example.myproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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
import com.example.myproject.StudentDetail;

import java.util.ArrayList;

import static com.example.myproject.Common.Common.loginDTO;
import static com.example.myproject.Common.Common.selItem2;


public class MyRecyclerviewAdapter2 extends RecyclerView.Adapter<MyRecyclerviewAdapter2.ItemViewHolder> implements Filterable {
    private static final String TAG = "MyRecyclerviewAdapter2";

    Context mContext;
    ArrayList<StudentDTO> arrayList;
    ArrayList<StudentDTO> arrayList_filter;

    public MyRecyclerviewAdapter2(Context mContext, ArrayList<StudentDTO> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.arrayList_filter = arrayList;
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

        StudentDTO item = arrayList_filter.get(position);
        holder.setItem(item);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: " + position);

                selItem2 = arrayList_filter.get(position);
                Intent intent = new Intent(mContext, StudentDetail.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList_filter.size();
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

    ////Filter
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    arrayList_filter = arrayList;
                } else {
                    ArrayList<StudentDTO> filteringList = new ArrayList<>();
                    for (StudentDTO dto : arrayList) {
                        //필터조건
                        if (dto.getStudent_subject().toLowerCase().contains(charString.toLowerCase())
                                || dto.getStudent_grade().contains(charString.toLowerCase())
                                || dto.getStudent_addr().toLowerCase().contains(charString.toLowerCase())) {
                            filteringList.add(dto);
                        }
                    }

                    arrayList_filter = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = arrayList_filter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                arrayList_filter = (ArrayList<StudentDTO>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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
            student_address.setText(dto.getStudent_addr());
            student_subject.setText(dto.getStudent_subject());

            if( ! dto.getStudent_image_path().contains("http") ){
                dto.setStudent_image_path("http://112.164.58.217:8080/tutors/" + dto.getStudent_image_path());
            }
            Glide.with(itemView).load(dto.getStudent_image_path()).into(student_picture);
        }
    }

}



