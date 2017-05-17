package com.doraesol.dorandoran;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.doraesol.dorandoran.config.ResultCode;
import com.doraesol.dorandoran.config.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FamilyTreeSearchActivity extends AppCompatActivity {

    @BindView(R.id.et_familytree_search) EditText et_familytree_search;
    @BindView(R.id.lv_searched_users)
    ListView lv_searched_users;
    List<String> searchedList;
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_tree_search);
        ButterKnife.bind(this);
        searchedList = new ArrayList<>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, searchedList);
        lv_searched_users.setAdapter(adapter);

        lv_searched_users.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String from_user_id = prefs.getString("user_id", null);
                String to_user_id = searchedList.get(position);

                SendMessageToUserTask sendMessageToUserTask = new SendMessageToUserTask();
                sendMessageToUserTask.execute(from_user_id, to_user_id);

                Toast.makeText(getApplicationContext(), from_user_id + " , " + to_user_id, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btn_familytree_search)
    public void OnSearchButtonClicked(){
        String target_user = et_familytree_search.getText().toString();

        SearchUserTask searchUserTask = new SearchUserTask();
        searchUserTask.execute(target_user);
    }

    class SendMessageToUserTask extends AsyncTask<String, Void, Integer>{

        @Override
        protected Integer doInBackground(String... params) {
            String from_user = params[0];
            String to_user = params[1];

            OkHttpClient client = new OkHttpClient();

            RequestBody body = new FormBody.Builder()
                    .add("from_user", from_user)
                    .add("to_user", to_user)
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

            Toast.makeText(getApplicationContext(), "status : " + resultCode, Toast.LENGTH_LONG).show();

            /*
            if(resultCode == ResultCode.ACK_RESULT_SUCCESS){
                Toast.makeText(getApplicationContext(), "전송 성공", Toast.LENGTH_LONG).show();
            }
            else if(resultCode == ResultCode.ACK_RESULT_FAIL){
                Toast.makeText(getApplicationContext(), "전송 실패", Toast.LENGTH_LONG).show();
            }
            */
        }
    }

    // 서버에서 사용자 검색
    class SearchUserTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String target_user = params[0];

            OkHttpClient client = new OkHttpClient();

            RequestBody body = new FormBody.Builder()
                    .add("target_user", target_user)
                    .build();

            Request request = new Request.Builder()
                    .url(Server.GET_USER)
                    .post(body)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String returnValue = response.body().string();

                return returnValue;
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String returnValue) {
            super.onPostExecute(returnValue);

            if(returnValue != null){
                searchedList.clear();

                searchedList.add(returnValue);
                /*
                for(String user : searchedList) {
                    searchedList.add(user);
                }
                */

                adapter.notifyDataSetChanged();
            }
        }
    }
}
