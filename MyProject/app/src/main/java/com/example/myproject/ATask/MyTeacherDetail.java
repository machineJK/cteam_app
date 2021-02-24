package com.example.myproject.Atask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.myproject.Dto.TeacherDTO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.example.myproject.Common.Common.ipConfig;
import static com.example.myproject.Common.Common.myDetail;

public class MyTeacherDetail extends AsyncTask<Void, Void, Void> {

    private String TAG = "myTeacherDetail";
    private String id;

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    public MyTeacherDetail(String id){
        this.id = id;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        String postURL = ipConfig + "/app/myTeacherDetail";

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            builder.addTextBody("id", id, ContentType.create("Multipart/related", "UTF-8"));
            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            myDetail = readJsonStream(inputStream);
            Log.d(TAG, "doInBackground: " + myDetail.getTeacher_id());
            Log.d(TAG, "doInBackground: " + myDetail.getTeacher_pay());
            Log.d(TAG, "doInBackground: " + myDetail.getTeacher_intro());
            inputStream.close();

        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
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

    public TeacherDTO readJsonStream(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

        String teacher_id = "", teacher_univ = "", teacher_major = "", teacher_univNum = "",
                teacher_subject = "", teacher_worktime = "", teacher_pay = "",
                teacher_intro = "", teacher_image_path = "", teacher_date = "", teacher_nickname = "",
                teacher_addr = "";
        int teacher_matching = -1;

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("teacher_id")) {
                teacher_id = reader.nextString();
            } else if (readStr.equals("teacher_pay")) {
                teacher_pay = reader.nextString();
            } else if (readStr.equals("teacher_intro")) {
                teacher_intro = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d(TAG, teacher_id + "," + teacher_intro);
        return new TeacherDTO(teacher_id, teacher_univ, teacher_major,
                teacher_univNum, teacher_subject, teacher_worktime,
                teacher_pay, teacher_intro, teacher_image_path, teacher_matching,
                teacher_date, teacher_nickname, teacher_addr);
    }
}
