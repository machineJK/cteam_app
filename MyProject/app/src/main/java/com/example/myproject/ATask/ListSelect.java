package com.example.myproject.Atask;

import android.app.ProgressDialog;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.myproject.Adapter.MyRecyclerviewAdapter;
import com.example.myproject.Dto.TeacherDTO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;
import static com.example.myproject.Common.Common.ipConfig;

public class ListSelect extends AsyncTask<Void,Void,Void> {
    // 생성자
    ArrayList<TeacherDTO> myItemArrayList;
    MyRecyclerviewAdapter adapter;
    ProgressDialog progressDialog;

    public ListSelect(ArrayList<TeacherDTO> myItemArrayList, MyRecyclerviewAdapter adapter, ProgressDialog progressDialog) {
        this.myItemArrayList = myItemArrayList;
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
        myItemArrayList.clear();
        String result = "";
        String postURL = ipConfig + "/app/anSelectMulti";

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

        Log.d("Matching", "List Select Complete!!!");

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

    public TeacherDTO readMessage(JsonReader reader) throws IOException {
        String teacher_id="",teacher_univ="",teacher_major="",teacher_univNum="",
                teacher_subject="", teacher_worktime="",teacher_pay="",
                teacher_intro="",teacher_image_path="";
        int teacher_matching=-1;
        Date teacher_date = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("teacher_id")) {
                teacher_id = reader.nextString();
            } else if (readStr.equals("teacher_univ")) {
                teacher_univ = reader.nextString();
            } else if (readStr.equals("teacher_date")) {
               teacher_date = Date.valueOf(reader.nextString());
            } else if (readStr.equals("teacher_major")) {
                teacher_major = reader.nextString();
            } else if (readStr.equals("teacher_univNum")) {
                teacher_univNum = reader.nextString();
            } else if (readStr.equals("teacher_subject")) {
                teacher_subject = reader.nextString();
            } else if (readStr.equals("teacher_worktime")) {
                teacher_worktime = reader.nextString();
            } else if (readStr.equals("teacher_pay")) {
                teacher_pay = reader.nextString();
            } else if (readStr.equals("teacher_intro")) {
                teacher_intro = reader.nextString();
            } else if (readStr.equals("teacher_matching")) {
                teacher_matching = reader.nextInt();
            }else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new TeacherDTO(teacher_id, teacher_univ, teacher_major,
                teacher_univNum, teacher_subject, teacher_worktime,
                teacher_pay, teacher_intro, teacher_matching,
                teacher_date, teacher_image_path);

    }
}



