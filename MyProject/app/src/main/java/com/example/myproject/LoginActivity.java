package com.example.myproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myproject.Atask.LoginSelect;
import com.example.myproject.Atask.NaverRequestApiTask;
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
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import java.util.concurrent.ExecutionException;

import static com.example.myproject.Common.Common.loginDTO;

public class LoginActivity extends AppCompatActivity {

    //구글 로그인
    private FirebaseAuth mAuth = null;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private SignInButton signInButton;

    Button btnLogin, btnSignUp,logout;
    EditText et_id, et_pw;
    String id,pw;

    //네이버 로그인
    private static OAuthLogin mOAuthLoginModule;
    private static Context nContext;
    private OAuthLoginButton naver_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //구글 로그인 버튼
        signInButton = findViewById(R.id.signInButton);
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
        });

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

        et_id.setText("admin");
        et_pw.setText("0000");

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
                    Intent intent = new Intent(LoginActivity.this, Matching.class);
                    startActivity(intent);
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
            }
        });

        //네이버 로그인
        nContext = this;
        initData();

    }

    //네이버 로그인 처리
    private void initData(){
        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(nContext,"M2lOgOAPfpTZBvZEv6r1",
                "5RHMuUjq1t","안드로이드_과외");
        naver_login = (OAuthLoginButton) findViewById(R.id.naver_login);
        naver_login.setOAuthLoginHandler(mOAuthLoginHandler);
    }

    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            String accessToken = mOAuthLoginModule.getAccessToken(nContext);
            String refreshToken = mOAuthLoginModule.getRefreshToken(nContext);
            long expiresAt = mOAuthLoginModule.getExpiresAt(nContext);
            String tokenType = mOAuthLoginModule.getTokenType(nContext);

            new NaverRequestApiTask(nContext, mOAuthLoginModule).execute();
/*            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(LoginActivity.this, SocialLoginAddr.class);
                    startActivity(intent);
                }
            },2000);*/
        }
    };

    //구글 로그인 처리
    @Override
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
    }




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