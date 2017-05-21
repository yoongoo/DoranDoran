package com.doraesol.dorandoran.setting;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.doraesol.dorandoran.LoginActivity;
import com.doraesol.dorandoran.R;
import com.doraesol.dorandoran.map.MapMainActivity;
import com.google.android.gms.auth.api.Auth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingMyPageActivity extends AppCompatActivity {

    @BindView(R.id.ll_setting_mypage_id)    LinearLayout ll_setting_mypage_id;
    @BindView(R.id.ll_setting_mypage_name)  LinearLayout ll_setting_mypage_name;
    @BindView(R.id.ll_setting_mypage_phone) LinearLayout ll_setting_mypage_phone;
    @BindView(R.id.ll_setting_mypage_birth) LinearLayout ll_setting_mypage_birth;
    @BindView(R.id.ll_setting_mypage_type)  LinearLayout ll_setting_mypage_type;
    @BindView(R.id.fab_setting_mypage)      FloatingActionButton fab_setting_mypage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_my_page);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_setting_mypage_id,
            R.id.ll_setting_mypage_name,
            R.id.ll_setting_mypage_phone,
            R.id.ll_setting_mypage_birth,
            R.id.ll_setting_mypage_type})
    public void OnLayoutClick(View view) {
        switch (view.getId()){
            case R.id.ll_setting_mypage_id:
                printToast("아이디 클릭");
                break;
            case R.id.ll_setting_mypage_name:
                printToast("이름 클릭");
                break;
            case R.id.ll_setting_mypage_phone:
                printToast("전화번호 클릭");
                break;
            case R.id.ll_setting_mypage_birth:
                printToast("생년월일 클릭");
                break;
            case R.id.ll_setting_mypage_type:
                printToast("혈액형 클릭");
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.fab_setting_mypage)
    public void onUpdateProfilePicture(){
        printToast("사진 변경 클릭");
    }



    private void printToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
