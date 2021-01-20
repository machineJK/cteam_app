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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import static com.example.myproject.Common.Common.ipConfig;

public class StudentInsert extends AsyncTask<Void, Void, String> {
    String student_id,student_subject,student_grade,student_intro,student_image_path;

    public StudentInsert(String student_id, String student_subject,
                         String student_grade, String student_intro,
                         String student_image_path) {
        this.student_id = student_id;
        this.student_subject = student_subject;
        this.student_grade = student_grade;
        this.student_intro = student_intro;
        this.student_image_path = student_image_path;
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
            builder.addTextBody("student_id", student_id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("student_subject", student_subject, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("student_grade", student_grade, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("student_intro", student_intro, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("student_image_path", student_image_path, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/anStudent";
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
