package com.example.myproject.Atask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import com.example.myproject.Dto.MemberDTO;
import com.nhn.android.naverlogin.OAuthLogin;

import org.json.JSONException;
import org.json.JSONObject;
import static com.example.myproject.Common.Common.socialDTO;

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
                Log.d("naverJSON", "" + response);    //JSON 구조 확인용

                String id = response.getString("id");
                String email = response.getString("email");
                String nickname = response.getString("nickname");
                String name = response.getString("name");
                String gender = response.getString("gender").equals("M") ? "남자" : "여자";
                String birthyear = response.getString("birthyear"); //1995
                String birthday = response.getString("birthday");   //01-05
                String[] birthday_split = birthday.split("-");  //["01","05"]
                String profile_image = response.getString("profile_image");

                socialDTO = new MemberDTO();
                socialDTO.setId(id);
                socialDTO.setEmail(email);
                socialDTO.setNickname(nickname);
                socialDTO.setName(name);
                socialDTO.setGender(gender);
                socialDTO.setBirth(birthyear + "." + birthday_split[0] + "." + birthday_split[1]);
                socialDTO.setdbImgPath(profile_image);


                //Toast.makeText(mContext, "id : " + socialDTO.getId(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(mContext, "email : " + socialDTO.getEmail(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(mContext, "nickname : " + socialDTO.getNickname(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(mContext, "name : " + socialDTO.getName(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(mContext, "gender : " + socialDTO.getGender(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(mContext, "birth : " + socialDTO.getBirth(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(mContext, "profile_image : " + socialDTO.getdbImgPath(), Toast.LENGTH_SHORT).show();

                //Toast.makeText(mContext, ""+birthyear + "." + birthday_split[0] + "." + birthday_split[1], Toast.LENGTH_SHORT).show();
                //Toast.makeText(mContext, "id : " + id, Toast.LENGTH_SHORT).show();
                //Toast.makeText(mContext, "email : " + email, Toast.LENGTH_SHORT).show();
                //Toast.makeText(mContext, "nickname : " + nickname, Toast.LENGTH_SHORT).show();
                //Toast.makeText(mContext, "name : " + name, Toast.LENGTH_SHORT).show();
                //Toast.makeText(mContext, "gender : " + gender, Toast.LENGTH_SHORT).show();
                //Toast.makeText(mContext, "birthday : " + birthday, Toast.LENGTH_SHORT).show();
                //Toast.makeText(mContext, "birthyear : " + birthyear, Toast.LENGTH_SHORT).show();
                //Toast.makeText(mContext, "profile_image : " + profile_image, Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        super.onPostExecute(content);
    }
}
