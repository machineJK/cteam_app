package com.example.myproject.Atask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.nhn.android.naverlogin.OAuthLogin;

import org.json.JSONException;
import org.json.JSONObject;

public class NaverRequestApiTask extends AsyncTask<Void,Void,String> {
    Context mContext;
    OAuthLogin mOAuthLoginModule;

    public NaverRequestApiTask(Context mContext, OAuthLogin mOAuthLoginModule) {
        this.mContext = mContext;
        this.mOAuthLoginModule = mOAuthLoginModule;
    }

    @Override
    protected String doInBackground(Void... params) {
        String url = "https://openapi.naver.com/v1/nid/me";
        String at = mOAuthLoginModule.getAccessToken(mContext);
        return mOAuthLoginModule.requestApi(mContext, at, url);
    }

    @Override
    protected void onPostExecute(String content) {
        try {
            JSONObject loginResult = new JSONObject(content);
            if (loginResult.getString("resultcode").equals("00")){
                JSONObject response = loginResult.getJSONObject("response");
                Log.d("naverJSON", "" + response);
                //String id = response.getString("id");
                //String email = response.getString("email");
                //String nickname = response.getString("nickname") == null ? "없음" : "있음";
                //String name = response.getString("name");
                //String gender = response.getString("gender").equals("M") ? "남자" : "여자";
                //String birthday = response.getString("birthday");
                //String profile_image = response.getString("profile_image");

                //Toast.makeText(mContext, "email : " + email, Toast.LENGTH_SHORT).show();
                //Toast.makeText(mContext, "nickname : " + nickname, Toast.LENGTH_SHORT).show();
                //Toast.makeText(mContext, "name : " + name, Toast.LENGTH_SHORT).show();
                //Toast.makeText(mContext, "gender : " + gender, Toast.LENGTH_SHORT).show();
                //Toast.makeText(mContext, "birthday : " + birthday, Toast.LENGTH_SHORT).show();
                //Toast.makeText(mContext, "profile_image : " + profile_image, Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        super.onPostExecute(content);
    }
}
