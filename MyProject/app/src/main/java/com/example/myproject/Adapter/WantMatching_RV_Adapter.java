package com.example.myproject.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.Atask.JoinInsert;
import com.example.myproject.Atask.StudentAccept;
import com.example.myproject.Dto.MatchingDTO;
import com.example.myproject.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.myproject.Common.Common.loginDTO;
import static com.example.myproject.Common.Common.selItem4;

public class WantMatching_RV_Adapter extends RecyclerView.Adapter<WantMatching_RV_Adapter.ItemViewHolder>{

    Context mContext;
    ArrayList<MatchingDTO> arrayList;

    public WantMatching_RV_Adapter(Context mContext, ArrayList<MatchingDTO> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public WantMatching_RV_Adapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.want_matching, parent, false);

        return new WantMatching_RV_Adapter.ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        Log.d("main:adapter", "" + position);

        MatchingDTO item = arrayList.get(position);
        holder.setItem(item);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selItem4 = arrayList.get(position);
            }
        });

        //승인
        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selItem4 = arrayList.get(position);
                StudentAccept studentAccept = new StudentAccept(loginDTO.getId(),selItem4.getStudent_id(),"y");

                try{
                    String state = studentAccept.execute().get().trim();
                    Toast.makeText(mContext, "승인 완료!", Toast.LENGTH_SHORT).show();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                arrayList.remove(selItem4);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, arrayList.size());

            }
        });

        //거절
        holder.btnRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selItem4 = arrayList.get(position);
                StudentAccept studentAccept = new StudentAccept(loginDTO.getId(),selItem4.getStudent_id(),"n");

                try{
                    String state = studentAccept.execute().get().trim();
                    Toast.makeText(mContext, "거절 완료!", Toast.LENGTH_SHORT).show();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                arrayList.remove(selItem4);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, arrayList.size());
                
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
        public TextView student_nickname;
        public ImageButton btnAccept,btnRefuse;

        public ItemViewHolder(@NonNull final View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parentLayout);
            student_nickname = itemView.findViewById(R.id.tv_student_nickname);
            btnAccept = itemView.findViewById(R.id.btnAccept);
            btnRefuse = itemView.findViewById(R.id.btnRefuse);

        }

        public void setItem(MatchingDTO dto){
            student_nickname.setText(dto.getStudent_nickname());

        }
    }



}
