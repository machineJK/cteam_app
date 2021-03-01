package com.example.myproject.Atask;

import android.app.ProgressDialog;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.myproject.Adapter.MyRecyclerviewAdapter;
import com.example.myproject.Adapter.WantMatching_RV_Adapter;
import com.example.myproject.Dto.MatchingDTO;

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
import java.util.ArrayList;

import static com.example.myproject.Common.Common.ipConfig;
import static com.example.myproject.Common.Common.loginDTO;

public class WantMatchingListSelect extends AsyncTask<Void,Void,Void> {
    // 생성자
    ArrayList<MatchingDTO> myItemArrayList;
    WantMatching_RV_Adapter adapter;

    public WantMatchingListSelect(ArrayList<MatchingDTO> myItemArrayList, WantMatching_RV_Adapter adapter) {
        this.myItemArrayList = myItemArrayList;
        this.adapter = adapter;
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
        myItemArrayList.clear();
        String result = "";
        String postURL = ipConfig + "/app/anWantMatchingList";

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            builder.addTextBody("id", loginDTO.getId(), ContentType.create("Multipart/related", "UTF-8"));

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
            */
            inputStream.close();

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

        adapter.notifyDataSetChanged();
    }

    //받은 arraylist를 파싱하는 방법
    public void readJsonStream(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                myItemArrayList.add(readMessage(reader));
            }
            reader.endArray();
        } finally {
            reader.close();
        }
    }

    public MatchingDTO readMessage(JsonReader reader) throws IOException {
        String teacher_id = "",student_id = "", teacher_value ="", student_value="", admin_value="",
                teacher_nickname="",student_nickname="";

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("teacher_id")) {
                teacher_id = reader.nextString();
            } else if (readStr.equals("student_id")) {
                student_id = reader.nextString();
            } else if (readStr.equals("teacher_value")) {
                teacher_value = reader.nextString();
            } else if (readStr.equals("student_value")) {
                student_value = reader.nextString();
            } else if (readStr.equals("admin_value")) {
                admin_value = reader.nextString();
            } else if (readStr.equals("teacher_nickname")) {
                teacher_nickname = reader.nextString();
            } else if (readStr.equals("student_nickname")) {
                student_nickname = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new MatchingDTO(teacher_id,student_id,teacher_value,student_value,admin_value,teacher_nickname,student_nickname);

    }

}
