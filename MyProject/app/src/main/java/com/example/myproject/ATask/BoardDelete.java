package com.example.myproject.Atask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import static com.example.myproject.Common.Common.ipConfig;

public class BoardDelete extends AsyncTask<Void, Void, String> {

    int qna_ref_num;
    String isComment;
    String board_id;

    public BoardDelete(int qna_ref_num) {
        this.qna_ref_num = qna_ref_num;
    }

    public BoardDelete(int qna_ref_num, String isComment, String board_id) {
        this.qna_ref_num = qna_ref_num;
        this.isComment = isComment;
        this.board_id = board_id;
    }

    // 데이터베이스에 삽입결과 0보다크면 삽입성공, 같거나 작으면 실패
    String state = "";

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected String doInBackground(Void... voids) {

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            // 문자열 및 데이터 추가
            if(isComment.equals("y")){
                builder.addTextBody("qna_ref_num", String.valueOf(qna_ref_num), ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("isComment", isComment, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("board_id", board_id, ContentType.create("Multipart/related", "UTF-8"));
            }else{
                builder.addTextBody("qna_ref_num", String.valueOf(qna_ref_num), ContentType.create("Multipart/related", "UTF-8"));
            }

            String postURL = ipConfig + "/app/anBoardDelete";
            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            // 응답
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            state = stringBuilder.toString();

            inputStream.close();

        }  catch (Exception e) {
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

        return state;
    }

    @Override
    protected void onPostExecute(String state) {

    }
}
