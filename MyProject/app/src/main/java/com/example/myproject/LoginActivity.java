package com.example.myproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myproject.Atask.LoginSelect;
import com.example.myproject.Atask.IdCheck;
import com.example.myproject.Atask.NaverRequestApiTask;
import com.example.myproject.Dto.MemberDTO;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;
import java.util.concurrent.ExecutionException;

import static com.example.myproject.Common.Common.checkDTO;
import static com.example.myproject.Common.Common.loginDTO;

public class LoginActivity extends AppCompatActivity {

    //구글 로그인
    private FirebaseAuth mAuth = null;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private SignInButton signInButton;

    Button btnLogin, btnSignUp;
    EditText et_id, et_pw;
    String id,pw;

    CheckBox chkAuto;

    //네이버 로그인
    public static OAuthLogin mOAuthLoginModule;
    private Context lContext = LoginActivity.this;
    private OAuthLoginButton naver_login;

    private ISessionCallback sessionCallback;

    SharedPreferences autoLogin,normalLogin,kakaoLogin,naverLogin,naverDTO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //자동로그인 체크박스
        chkAuto = findViewById(R.id.chkAuto);

        //카카오 로그인
        sessionCallback = new SessionCallback();
        Session.getCurrentSession().addCallback(sessionCallback);
        //Session.getCurrentSession().checkAndImplicitOpen();   //카카오톡 자동로그인

        //자동로그인 체크박스 설정
        autoLogin = getSharedPreferences("autoLogin",MODE_PRIVATE);
        if(!autoLogin.getString("isChecked", "").equals("")){
            if(autoLogin.getString("isChecked","").equals("checked")){
                chkAuto.setChecked(true);
            }else if(autoLogin.getString("isChecked","").equals("notChecked")){
                chkAuto.setChecked(false);
            }
        }

        chkAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    SharedPreferences.Editor editor = autoLogin.edit();
                    editor.putString("isChecked", "checked");
                    editor.commit();
                }else{
                    SharedPreferences.Editor editor = autoLogin.edit();
                    editor.putString("isChecked", "notChecked");
                    editor.commit();
                }
            }
        });

        normalLogin = getSharedPreferences("normalLogin",MODE_PRIVATE);
        kakaoLogin = getSharedPreferences("kakaoLogin", MODE_PRIVATE);
        naverLogin = getSharedPreferences("naverLogin", MODE_PRIVATE);
        naverDTO = getSharedPreferences("naverDTO", MODE_PRIVATE);

        //자동로그인이 체크되어있을 경우 자동 로그인
        if(chkAuto.isChecked()){
            //일반 자동로그인
            if(!normalLogin.getString("id", "").equals("")){
                loginDTO = new MemberDTO();
                loginDTO.setId(normalLogin.getString("id",""));
                loginDTO.setPw(normalLogin.getString("pw",""));
                loginDTO.setNickname(normalLogin.getString("nickname",""));
                loginDTO.setName(normalLogin.getString("name",""));
                loginDTO.setGender(normalLogin.getString("gender",""));
                loginDTO.setBirth(normalLogin.getString("birth",""));
                loginDTO.setEmail(normalLogin.getString("email",""));
                loginDTO.setAddr1(normalLogin.getString("addr1",""));
                loginDTO.setAddr2(normalLogin.getString("addr2",""));
                loginDTO.setdbImgPath(normalLogin.getString("dbimgpath",""));
                loginDTO.setKakao_login(normalLogin.getString("kakao_login",""));
                loginDTO.setNaver_login(normalLogin.getString("naver_login",""));

                Intent intent = new Intent(LoginActivity.this, Matching.class);
                startActivity(intent);
                finish();
            }
            //카카오톡 자동로그인
            if(kakaoLogin.getString("kakaoLogin","").equals("kakaoLogin")){
                //Toast.makeText(this, kakaoLogin.getString("kakaoLogin","") ,Toast.LENGTH_SHORT).show();
                Session.getCurrentSession().checkAndImplicitOpen();
            }

            //네이버 자동로그인
            if(naverLogin.getString("naverLogin","").equals("naverLogin")){
                Toast.makeText(LoginActivity.this, "네이버 값 있음", Toast.LENGTH_SHORT).show();
                loginDTO = new MemberDTO();
                loginDTO.setId(naverDTO.getString("id",""));
                loginDTO.setPw(naverDTO.getString("pw",""));
                loginDTO.setNickname(naverDTO.getString("nickname",""));
                loginDTO.setName(naverDTO.getString("name",""));
                loginDTO.setGender(naverDTO.getString("gender",""));
                loginDTO.setBirth(naverDTO.getString("birth",""));
                loginDTO.setEmail(naverDTO.getString("email",""));
                loginDTO.setAddr1(naverDTO.getString("addr1",""));
                loginDTO.setAddr2(naverDTO.getString("addr2",""));
                loginDTO.setdbImgPath(naverDTO.getString("dbimgpath",""));
                loginDTO.setKakao_login(naverDTO.getString("kakao_login",""));
                loginDTO.setNaver_login(naverDTO.getString("naver_login",""));

                Intent intent = new Intent(LoginActivity.this, Matching.class);
                startActivity(intent);
                finish();
            }
        }




                //구글 로그인 버튼
        /*signInButton = findViewById(R.id.signInButton);
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });*/

        //다음 로그인 시 바로 매칭으로 넘어가게 하는 코드
        /*
        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(getApplication(), Matching.class);
            startActivity(intent);
            finish();
        }

         */

        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);


        //미리 로그인 아이디 써놓기(개발용)(나중에 지우기)////////////
        //et_id.setText("admin");
        //et_pw.setText("0000");

        //권한 주기
        checkDangerousPermissions();

        //로그인
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_id.getText().toString().length() != 0 && et_pw.getText().toString().length() != 0){
                    id = et_id.getText().toString();
                    pw = et_pw.getText().toString();

                    LoginSelect loginSelect = new LoginSelect(id, pw);
                    try {
                        loginSelect.execute().get();
                    } catch (ExecutionException e) {
                        e.getMessage();
                    } catch (InterruptedException e) {
                        e.getMessage();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "아이디와 암호를 모두 입력하세요", Toast.LENGTH_SHORT).show();
                    Log.d("main:login", "아이디와 암호를 모두 입력하세요 !!!");
                    return;
                }

                if(loginDTO != null){
                    Toast.makeText(LoginActivity.this, "로그인 되었습니다 !!!", Toast.LENGTH_SHORT).show();
                    Log.d("main:login", loginDTO.getId() + "님 로그인 되었습니다 !!!");

                    normalLogin = getSharedPreferences("normalLogin",MODE_PRIVATE);
                    SharedPreferences.Editor editor = normalLogin.edit();
                    editor.putString("id", loginDTO.getId());
                    editor.putString("pw", loginDTO.getPw());
                    editor.putString("nickname", loginDTO.getNickname());
                    editor.putString("name", loginDTO.getName());
                    editor.putString("gender", loginDTO.getGender());
                    editor.putString("birth", loginDTO.getBirth());
                    editor.putString("email", loginDTO.getEmail());
                    editor.putString("addr1", loginDTO.getAddr1());
                    editor.putString("addr2", loginDTO.getAddr2());
                    editor.putString("dbimgpath", loginDTO.getdbImgPath());
                    editor.putString("kakao_login", loginDTO.getKakao_login());
                    editor.putString("naver_login", loginDTO.getNaver_login());
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this, Matching.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "아이디나 비밀번호가 일치안함 !!!", Toast.LENGTH_SHORT).show();
                    Log.d("main:login", "아이디나 비밀번호가 일치안함 !!!");
                    et_id.setText(""); et_pw.setText("");
                    et_id.requestFocus();
                }


            }
        });

        //회원가입 화면 이동
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.myproject.LoginActivity.this, JoinActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //네이버 로그인
        //lContext = LoginActivity.this;
        initData();

    }

    //네이버 로그인 처리
    private void initData(){
        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(lContext,"M2lOgOAPfpTZBvZEv6r1",
                "5RHMuUjq1t","안드로이드_과외");
        naver_login = (OAuthLoginButton) findViewById(R.id.naver_login);
        naver_login.setOAuthLoginHandler(mOAuthLoginHandler);
    }

    @SuppressLint("HandlerLeak")
    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            String accessToken = mOAuthLoginModule.getAccessToken(lContext);
            String refreshToken = mOAuthLoginModule.getRefreshToken(lContext);
            long expiresAt = mOAuthLoginModule.getExpiresAt(lContext);
            String tokenType = mOAuthLoginModule.getTokenType(lContext);

            new NaverRequestApiTask(lContext, mOAuthLoginModule).execute();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //id 중복체크 이후
                    IdCheck naverIdCheck = new IdCheck(loginDTO.getId(), "m");
                    try {
                        naverIdCheck.execute().get();
                    } catch (ExecutionException e) {
                        e.getMessage();
                    } catch (InterruptedException e) {
                        e.getMessage();
                    }

                    //0이면 db에 없음, 1이면 db에 있음
                    if(checkDTO.getIdchk() == 0){
                        Intent intent = new Intent(LoginActivity.this, NaverExtraInfo.class);
                        startActivity(intent);
                        finish();
                    }else{
                        id = loginDTO.getId();
                        pw = "social";

                        LoginSelect loginSelect = new LoginSelect(id, pw);

                        try {
                            loginSelect.execute().get();

                            Intent intent = new Intent(LoginActivity.this, Matching.class);
                            startActivity(intent);
                            finish();

                        } catch (ExecutionException e) {
                            e.getMessage();
                        } catch (InterruptedException e) {
                            e.getMessage();
                        }

                    }

                }
            },3000);
        }
    };

    //카카오 로그인 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    private class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            UserManagement.getInstance().me(new MeV2ResponseCallback() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    int result = errorResult.getErrorCode();

                    if(result == ApiErrorCode.CLIENT_ERROR_CODE) {
                        Toast.makeText(getApplicationContext(), "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"로그인 도중 오류가 발생했습니다: "+errorResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    //Toast.makeText(getApplicationContext(),"세션이 닫혔습니다. 다시 시도해 주세요: "+errorResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(MeV2Response result) {
                    //id 중복체크 이후
                    IdCheck kakaoIdCheck = new IdCheck(String.valueOf(result.getId()),"m");
                    try {
                        kakaoIdCheck.execute().get();
                    } catch (ExecutionException e) {
                        e.getMessage();
                    } catch (InterruptedException e) {
                        e.getMessage();
                    }
                    //0이면 db에 없음, 1이면 db에 있음
                    if(checkDTO.getIdchk() == 0){
                        Intent intent = new Intent(getApplicationContext(), KakaoExtraInfo.class);
                        intent.putExtra("id", String.valueOf(result.getId()));
                        startActivity(intent);
                    }else{
                        id = String.valueOf(result.getId());
                        pw = "social";
                        LoginSelect loginSelect = new LoginSelect(id, pw);

                        SharedPreferences.Editor editor = kakaoLogin.edit();
                        editor.putString("kakaoLogin", "kakaoLogin");
                        editor.commit();

                        try {
                            loginSelect.execute().get();
                        } catch (ExecutionException e) {
                            e.getMessage();
                        } catch (InterruptedException e) {
                            e.getMessage();
                        }
                        Intent intent = new Intent(LoginActivity.this, Matching.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException e) {
            Toast.makeText(getApplicationContext(), "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요: "+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //구글 로그인 처리
/*    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.d("login:", e.getMessage());
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {//구글 로그인이 최종적으로 성공적인지 실패인지 물어보는 곳
                        if (task.isSuccessful()) { //로그인이 성공했으면
                            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            //account데이터를 가져왔으므로 써먹자.(loginDTO에 집어넣자)

                            Log.d("logd", account.getId());
                            Log.d("logd", account.getDisplayName());
                            Log.d("logd", account.getEmail());
                            Log.d("logd", account.getFamilyName());
                            Log.d("logd", account.getGivenName());
                            Log.d("logd", String.valueOf(account.getPhotoUrl()));


                            Intent intent = new Intent(LoginActivity.this, NaverExtraInfo.class);

                            startActivity(intent);



                        } else {
                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/




    //권한 위임
    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}