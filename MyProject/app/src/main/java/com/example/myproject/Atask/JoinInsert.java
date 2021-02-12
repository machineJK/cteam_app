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

public class JoinInsert extends AsyncTask<Void, Void, String> {

    String id,pw,nickname,name,gender,
            birth, email, addr1, addr2, imageDbPathA,
            imageRealPathA,kakao_login, naver_login;

    public JoinInsert(String id, String pw, String nickname, String name, String gender,
                      String birth, String email, String addr1, String addr2,
                      String imageDbPathA, String imageRealPathA, String kakao_login, String naver_login) {
        this.id = id;
        this.pw = pw;
        this.nickname = nickname;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.email = email;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.imageDbPathA = imageDbPathA;
        this.imageRealPathA = imageRealPathA;
        this.kakao_login = kakao_login;
        this.naver_login = naver_login;
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
            builder.addTextBody("id", id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("pw", pw, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("nickname", nickname, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("name", name, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("gender", gender, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("birth", birth, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("email", email, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("addr1", addr1, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("addr2", addr2, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("dbImgPath", imageDbPathA, ContentType.create("Multipart/related", "UTF-8"));
            builder.addPart("image", new FileBody(new File(imageRealPathA)));
            builder.addTextBody("kakao_login", kakao_login, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("naver_login", naver_login, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/anJoin";
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
