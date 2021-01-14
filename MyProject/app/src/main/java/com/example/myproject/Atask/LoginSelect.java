package com.example.myproject.Atask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.myproject.Dto.MemberDTO;

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
import static com.example.myproject.Common.Common.loginDTO;

public class LoginSelect extends AsyncTask<Void, Void, Void> {

    String id, pw;

    public LoginSelect(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    /*@Override  // 없어도 됨
    protected void onPreExecute() {

    }*/

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            // 문자열 및 데이터 추가
            builder.addTextBody("id", id, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("pw", pw, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/app/anLogin";
            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            loginDTO = readMessage(inputStream);


            /*StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            String jsonStr = stringBuilder.toString();*/

            inputStream.close();

        } catch (Exception e) {
            Log.d("main:loginselect", e.getMessage());
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

    }

    public MemberDTO readMessage(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

        String id = "", pw = "", nickname = "", name = "", gender = "",
                birth = "", email = "", addr1 = "", addr2 = "", picture = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("id")) {
                id = reader.nextString();
            } else if (readStr.equals("pw")) {
                pw = reader.nextString();
            } else if (readStr.equals("nickname")) {
                nickname = reader.nextString();
            } else if (readStr.equals("name")) {
                name = reader.nextString();
            }else if (readStr.equals("gender")) {
                gender = reader.nextString();
            }else if (readStr.equals("birth")) {
                birth = reader.nextString();
            }else if (readStr.equals("email")) {
                email = reader.nextString();
            }else if (readStr.equals("addr1")) {
                addr1 = reader.nextString();
            }else if (readStr.equals("addr2")) {
                addr2 = reader.nextString();
            }else if (readStr.equals("picture")) {
                picture = reader.nextString();
            }else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d("main:loginselect : ", id + "," + pw);
        return new MemberDTO(id, pw, nickname, name, gender, birth, email, addr1, addr2, picture);

    }
}
