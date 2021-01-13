package com.example.myproject.Atask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

import com.example.myproject.Common.Common;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class JoinInsert extends AsyncTask<Void, Void, String> {

    String member_id,member_pw, member_nickname, member_name,
            member_gender, member_birth, member_email, member_picture;

    public JoinInsert(String member_id, String member_pw, String member_nickname, String member_name,
                      String member_gender, String member_birth, String member_email, String member_picture) {
        this.member_id = member_id;
        this.member_pw = member_pw;
        this.member_nickname = member_nickname;
        this.member_name = member_name;
        this.member_gender = member_gender;
        this.member_birth = member_birth;
        this.member_email = member_email;
        this.member_picture = member_picture;
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
            builder.addTextBody("member_id", member_id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_pw", member_pw, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_nickname", member_nickname, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_name", member_name, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_gender", member_gender, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_birth", member_birth, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_email", member_email, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("member_picture", member_picture, ContentType.create("Multipart/related", "UTF-8"));


            String postURL = Common.ipConfig + "/app/anJoin";
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
