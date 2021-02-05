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

public class BoardInsert extends AsyncTask<Void, Void, String> {
    String board_id, board_title, board_content;
    String brdImageDbPathA, brdImageRealPathA, id_image_path;
    int board_notice, qna_ref_num;
    //board_num, board_write_date, board_readcount

    public BoardInsert(String board_id, String board_title, String board_content,
                       String brdImageDbPathA, String brdImageRealPathA,
                       int board_notice, int qna_ref_num, String id_image_path) {
        this.board_id = board_id;
        this.board_title = board_title;
        this.board_content = board_content;
        this.brdImageDbPathA = brdImageDbPathA;
        this.brdImageRealPathA = brdImageRealPathA;
        this.board_notice = board_notice;
        this.qna_ref_num = qna_ref_num;
        this.id_image_path = id_image_path;
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
            builder.addTextBody("board_id", board_id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("board_title", board_title, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("board_content", board_content, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("board_notice", board_notice + "", ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("qna_ref_num", qna_ref_num + "", ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("brdDbImgPath", brdImageDbPathA, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("id_image_path", id_image_path, ContentType.create("Multipart/related", "UTF-8"));
            builder.addPart("brdImage", new FileBody(new File(brdImageRealPathA)));
            String postURL = ipConfig + "/app/anBoard";
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
}
