package com.doraesol.dorandoran;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.doraesol.dorandoran.config.ResultCode;
import com.doraesol.dorandoran.config.Server;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class JoinActivity extends AppCompatActivity {

    private final String LOG_TAG = JoinActivity.class.getSimpleName();

    @BindView(R.id.et_join_id)
    EditText et_join_id;
    @BindView(R.id.et_join_pw)
    EditText et_join_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_join_ok, R.id.btn_join_cancel})
    public void OnButtonClicked(View view){

        String id = et_join_id.getText().toString();
        String pw = et_join_pw.getText().toString();

        switch(view.getId()){
            case R.id.btn_join_ok:
                JoinTask joinTask = new JoinTask();
                joinTask.execute(id, pw);
                break;
            case R.id.btn_join_cancel:
                break;
        }
    }

    class JoinTask extends AsyncTask<String,Void,Integer>{

        @Override
        protected Integer doInBackground(String... params) {
            String id = params[0];
            String pw = params[1];

            OkHttpClient client = new OkHttpClient();

            RequestBody body = new FormBody.Builder()
                    .add("id", id)
                    .add("pw", pw)
                    .build();

            Request request = new Request.Builder()
                    .url(Server.JOIN)
                    .post(body)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String returnValue = response.body().string();
                int resultCode = Integer.parseInt(returnValue);

                return resultCode;


            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }

            return ResultCode.ACK_RESULT_FAIL;
        }

        @Override
        protected void onPostExecute(Integer resultCode) {
            super.onPostExecute(resultCode);

            if(resultCode == ResultCode.ACK_RESULT_SUCCESS){
                Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_LONG).show();
            }
            else if(resultCode == ResultCode.ACK_RESULT_FAIL){
                Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_LONG).show();
            }
        }
    }

}
