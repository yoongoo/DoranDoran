package com.doraesol.dorandoran;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.doraesol.dorandoran.config.Server;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.Status;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener
{
    final String LOG_TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.et_login_id)     EditText et_login_id;
    @BindView(R.id.et_login_pw)     EditText et_login_pw;
    @BindView(R.id.btn_login_google) SignInButton btn_login_google;
    GoogleApiClient mGoogleApiClient;
    private static final int RESOLVE_CONNECTION_REQUEST_CODE = 1;

    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        InitalizemGoogleApiClient();
    }

    //메인화면으로
    @OnClick(R.id.bt_login)
    public void OnLoginButtonClicked()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("user_id", et_login_id.getText().toString());
        editor.commit();

        String id = et_login_id.getText().toString();
        String fcmToken = FirebaseInstanceId.getInstance().getToken();
        RegisterFcmTokenTask registerFcmTokenTask = new RegisterFcmTokenTask();
        registerFcmTokenTask.execute(id, fcmToken);

        Log.d(LOG_TAG, "token : " + fcmToken);

        Intent intent = new Intent(
                getApplicationContext(), // 현재 화면의 제어권자
                MenuActivity.class); // 다음 넘어갈 클래스 지정
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent); // 다음 화면으로 넘어간다
        finish();
    }

    @OnClick(R.id.bt_signup)
    public void OnSignupButtonClicked()
    {
        Intent intent = new Intent(
                getApplicationContext(), // 현재 화면의 제어권자
                JoinActivity.class); // 다음 넘어갈 클래스 지정
        startActivity(intent); // 다음 화면으로 넘어간다
    }

    @OnClick(R.id.btn_login_google)
    public void onGoogleLoginButtonClicked()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent( mGoogleApiClient );
        startActivityForResult( signInIntent, RESOLVE_CONNECTION_REQUEST_CODE );
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch ( requestCode ) {

            case RESOLVE_CONNECTION_REQUEST_CODE:
                Log.d(LOG_TAG, "Data String : " + data.getDataString());
                Log.d(LOG_TAG, "Data toString : " + data.toString());
                Log.d(LOG_TAG, "Data toString : " + data.getExtras().toString());
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                Status stat = result.getStatus();
                Log.d(LOG_TAG, "result state : " + result.isSuccess());

                if ( result.isSuccess()) {
                    GoogleSignInAccount account = result.getSignInAccount( );
                    // 계정 정보 얻어오기
                    Log.d(LOG_TAG, account.getDisplayName());
                    Log.d(LOG_TAG, account.getEmail());
                    Log.d(LOG_TAG, account.getDisplayName());

                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show();
                }

                break;

            default:
                super.onActivityResult( requestCode, resultCode, data );
                break;
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(LOG_TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount account = result.getSignInAccount( );
            Log.d(LOG_TAG, account.getDisplayName());
            Log.d(LOG_TAG, account.getEmail());
            Log.d(LOG_TAG, account.getDisplayName());
        } else {
            // Signed out, show unauthenticated UI.
        }
    }


    private void InitalizemGoogleApiClient(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder( GoogleSignInOptions.DEFAULT_SIGN_IN )
                // 필요한 항목이 있으면 아래에 추가
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                // 필요한 api가 있으면 아래에 추가
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void InitalizePermission(){

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(LOG_TAG, "login failed : " + connectionResult.getErrorMessage());
    }




    class RegisterFcmTokenTask extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... params) {
            String id = params[0];
            String token = params[1];

            OkHttpClient client = new OkHttpClient();

            RequestBody body = new FormBody.Builder()
                    .add("id", id)
                    .add("fcm_token", token)
                    .build();

            Request request = new Request.Builder()
                    .url(Server.REGISTER_FCM_TOKEN)
                    .post(body)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String returnValue = response.body().string();
                int resultCode = Integer.parseInt(returnValue);
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }

            return null;
        }
    }
}
