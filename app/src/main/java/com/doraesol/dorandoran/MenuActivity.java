package com.doraesol.dorandoran;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.doraesol.dorandoran.config.DataConfig;
import com.doraesol.dorandoran.config.ResultCode;
import com.doraesol.dorandoran.config.Server;
import com.doraesol.dorandoran.map.MapMainFragment;
import com.doraesol.dorandoran.setting.SettingFragment;
import com.doraesol.dorandoran.social.CmtBoardFragment;
import com.tsengvn.typekit.TypekitContextWrapper;
import java.io.IOException;
import java.io.InputStream;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by JJY on 2017-04-01.
 */

public class MenuActivity extends AppCompatActivity
{

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        backPressCloseHandler = new BackPressCloseHandler(this);
        ButterKnife.bind(this);

        Intent paramIntent = getIntent();


        try{
            String requestCode = paramIntent.getExtras().getString("REQUEST_CODE");

            if(requestCode.equals(Server.REQUEST_USER_FAMILYTREE)){
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String from_user_id = prefs.getString("user_id", null);
                String to_user_id = paramIntent.getExtras().getString("FROM_USER");
                String json_data = loadCurrentFamilyTreeInfo();

                showSendDialog(from_user_id, to_user_id, json_data);
            }
        }
        catch(NullPointerException ex){
            Toast.makeText(getApplicationContext(), "요청코드가 없습니다.", Toast.LENGTH_LONG);
        }


    }

    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    @OnClick({R.id.ll_menu_family_tree, R.id.ll_menu_schedule, R.id.ll_menu_search, R.id.ll_menu_settings})
    public void onMenuLayoutClicked(View paramView){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        int pageNumber = 0;

        switch (paramView.getId()){
            case R.id.ll_family_tree:   pageNumber = 0; break;
            case R.id.ll_menu_schedule: pageNumber = 1; break;
            case R.id.ll_menu_search:   pageNumber = 2; break;
            case R.id.ll_menu_settings: pageNumber = 3; break;
        }
        intent.putExtra("page", pageNumber);
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(TypekitContextWrapper.wrap(base));
    }
    

    private void showSendDialog(final String from_user, final String to_user, final String json_data) {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);

        alertdialog.setPositiveButton("승인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SendMessageToUserTask sendMessageToUserTask = new SendMessageToUserTask();
                sendMessageToUserTask.execute(from_user, to_user, json_data);

                Toast.makeText(getApplicationContext(), "승인 완료", Toast.LENGTH_SHORT).show();
            }
        });

        alertdialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alert = alertdialog.create();
        alert.setIcon(R.drawable.ic_list_genogram);
        alert.setTitle("가계도 요청");
        alert.show();
    }


    // 사용자에게 메세지를 보내는 클래스
    class SendMessageToUserTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            String from_user = params[0];
            String to_user = params[1];
            String json_data = params[2];

            OkHttpClient client = new OkHttpClient();

            RequestBody body = new FormBody.Builder()
                    .add("from_user", from_user)
                    .add("to_user", to_user)
                    .add("json_data", json_data)
                    .add("request_code", Server.RESPONSE_USER_FAMILYTREE)
                    .build();

            Request request = new Request.Builder()
                    .url(Server.SEND_MESSAGE_TO_USER)
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
        }
    }

    private String loadCurrentFamilyTreeInfo(){
        String retJsonData;
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        retJsonData = mPref.getString(DataConfig.FAMILY_TREE_INFO, DataConfig.EMPTY_DATA);

        if(retJsonData.equals(DataConfig.EMPTY_DATA)){
            Log.d("JSONDATA", "invalid Json Data .. ");
        }
        else {
            Log.d("JSONDATA", "valid json data .. ");
        }

        return retJsonData;
    }
}
