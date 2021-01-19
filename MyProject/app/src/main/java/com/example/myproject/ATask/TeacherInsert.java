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

public class TeacherInsert extends AsyncTask<Void, Void, String> {
    String teacher_id,teacher_univ,teacher_major,teacher_univNum,teacher_subject,
            teacher_worktime,teacher_pay,teacher_intro,teacher_image_path;

    public TeacherInsert(String teacher_id, String teacher_univ, String teacher_major,
                         String teacher_univNum, String teacher_subject,
                         String teacher_worktime, String teacher_pay, String teacher_intro,
                         String teacher_image_path) {
        this.teacher_id = teacher_id;
        this.teacher_univ = teacher_univ;
        this.teacher_major = teacher_major;
        this.teacher_univNum = teacher_univNum;
        this.teacher_subject = teacher_subject;
        this.teacher_worktime = teacher_worktime;
        this.teacher_pay = teacher_pay;
        this.teacher_intro = teacher_intro;
        this.teacher_image_path = teacher_image_path;
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
            builder.addTextBody("teacher_id", teacher_id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("teacher_univ", teacher_univ, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("teacher_major", teacher_major, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("teacher_univNum", teacher_univNum, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("teacher_subject", teacher_subject, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("teacher_worktime", teacher_worktime, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("teacher_pay", teacher_pay, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("teacher_intro", teacher_intro, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("teacher_image_path", teacher_image_path, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/anTeacher";
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
