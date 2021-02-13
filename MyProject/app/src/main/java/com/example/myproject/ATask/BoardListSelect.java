package com.example.myproject.Atask;

import android.app.ProgressDialog;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.myproject.Adapter.BoardAdapter;
import com.example.myproject.Dto.BoardDTO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.example.myproject.Common.Common.ipConfig;

public class BoardListSelect extends AsyncTask<Void,Void,Void> {
    // 생성자
    ArrayList<BoardDTO> brdArrayList;
    BoardAdapter adapter;
    ProgressDialog progressDialog;

    public BoardListSelect(ArrayList<BoardDTO> brdArrayList, BoardAdapter adapter, ProgressDialog progressDialog) {
        this.brdArrayList = brdArrayList;
        this.adapter = adapter;
        this.progressDialog = progressDialog;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        brdArrayList.clear();
        String result = "";
        String postURL = ipConfig + "/app/anSelectBoard";

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            readJsonStream(inputStream);

            /*BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            String jsonStr = stringBuilder.toString();

            inputStream.close();*/

        } catch (Exception e) {
            Log.d("Matching", e.getMessage());
            e.printStackTrace();
        }finally {
            if(httpEntity != null){
                httpEntity = null;
            }
            if(httpResponse != null){
                httpResponse = null;
            }
            if(httpPost != null){
                httpPost = null;
            }
            if(httpClient != null){
                httpClient = null;
            }

        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if(progressDialog != null){
            progressDialog.dismiss();
        }

        Log.d("Board", "List Select Complete!!!");

        adapter.notifyDataSetChanged();
    }

    //받은 arraylist를 파싱하는 방법
    public void readJsonStream(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                brdArrayList.add(readMessage(reader));
            }
            reader.endArray();
        } finally {
            reader.close();
        }
    }

    public BoardDTO readMessage(JsonReader reader) throws IOException {
        int board_readcount = 0, board_notice = 1, qna_ref_num = 0;
        String board_id = "", board_nickname = "", board_content = "";
        String board_write_date = "", board_image_path = "", id_image_path = "";
        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("board_id")) {
                board_id = reader.nextString();
            } else if (readStr.equals("board_write_date")) {
                String[] temp = reader.nextString().replace("월", "-").replace(",", "-")
                        .replace(" ", "").split("-");
                board_write_date = temp[2] + "-" + temp[0] + "-" + temp[1];
            } else if (readStr.equals("board_notice")) {
                board_notice = reader.nextInt();
            } else if (readStr.equals("qna_ref_num")) {
                qna_ref_num = reader.nextInt();
            } else if (readStr.equals("board_readcount")) {
                board_readcount = reader.nextInt();
            } else if (readStr.equals("board_nickname")) {
                board_nickname = reader.nextString();
            } else if (readStr.equals("board_content")) {
                board_content = reader.nextString();
            } else if (readStr.equals("board_image_path")) {
                board_image_path = reader.nextString();
            } else if (readStr.equals("id_image_path")) {
                id_image_path = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        BoardDTO boardDTO = new BoardDTO(board_id, board_nickname, board_content, board_write_date,
                board_readcount, board_image_path, board_notice, qna_ref_num, id_image_path);
        return boardDTO;

    }
}



