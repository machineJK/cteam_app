package com.example.myproject.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.Dto.ChatListDTO;
import com.example.myproject.R;

import java.util.ArrayList;

public class ChatListAdapter extends BaseAdapter {
    private static final String TAG = "Chat";
    //Main에서 받아오는 것들
    Context context;
    ArrayList<ChatListDTO> dtos;

    //Adapter에서 생성할 것
    LayoutInflater inflater;

    public ChatListAdapter(Context context, ArrayList<ChatListDTO> dtos) {
        this.context = context;
        this.dtos = dtos;
        this.inflater =
                (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    //필요한 메소드
    public void addDTO(ChatListDTO dto){
        dtos.add(dto);
    }

    @Override
    public int getCount() { return dtos.size(); }

    @Override
    public Object getItem(int position) { return dtos.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView: position : " + position);
        ChatListViewHolder viewHolder;
        if (convertView == null){
            //화면을 구성하는 공간이 남아 있는데 덜만들어져 있으면, 보이는 화면을 더 만들어라.
            convertView =
                    inflater.inflate(R.layout.list_chat, parent, false);

            viewHolder = new ChatListViewHolder();
            viewHolder.textName = convertView.findViewById(R.id.textName);
            viewHolder.textId = convertView.findViewById(R.id.textId);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ChatListViewHolder) convertView.getTag();
        }//화면이 올라가는 것처럼 보이지만, 실제로는 데이터만 바뀐다.

        ChatListDTO dto = dtos.get(position);
        String nickname = dto.getNickname();
        String id = dto.getId();

        viewHolder.textName.setText(nickname);
        viewHolder.textId.setText(id);

        return convertView;

    }

    public class ChatListViewHolder{
        public TextView textName, textId;
    }//inner클래스를 만들고 getView에서 가져다 쓴다.
}
