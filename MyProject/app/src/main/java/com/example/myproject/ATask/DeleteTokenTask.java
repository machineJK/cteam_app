package com.example.myproject.Atask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.nhn.android.naverlogin.OAuthLogin;

public class DeleteTokenTask extends AsyncTask<Void,Void,Boolean> {
    Context mContext;
    OAuthLogin mOAuthLoginModule;

    public DeleteTokenTask(Context mContext, OAuthLogin mOAuthLoginModule) {
        this.mContext = mContext;
        this.mOAuthLoginModule = mOAuthLoginModule;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        boolean isSuccessDeleteToken = mOAuthLoginModule.logoutAndDeleteToken(mContext);

        if (!isSuccessDeleteToken) {
            // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
            // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
            Log.d("deleteTokenTask", "errorCode:" + mOAuthLoginModule.getLastErrorCode(mContext));
            Log.d("deleteTokenTask", "errorDesc:" + mOAuthLoginModule.getLastErrorDesc(mContext));
        }

        return isSuccessDeleteToken;
    }

    protected void onPostExecute(boolean isSuccessDeleteToken) {
    }
}
