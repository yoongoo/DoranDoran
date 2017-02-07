package com.doraesol.dorandoran;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.onClick;

public class LoginActivity extends AppCompatActivity
{
    @BindView(R.id.iv_login)   ImageView iv_login;
    //메인화면으로
    //@BindView(R.id.bt_login)    ImageButton bt_login;
    @BindView(R.id.bt_signup) ImageButton bt_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        iv_login.setImageResource(R.drawable.img_log_sprout) ;
    }

    //메인화면으로
    /*@OnClick(R.id.bt_login)
    public void login()
    {
        Intent intent = new Intent(
                getApplicationContext(), // 현재 화면의 제어권자
                MainActivity.class); // 다음 넘어갈 클래스 지정
        startActivity(intent); // 다음 화면으로 넘어간다
    }*/

    @OnClick(R.id.bt_signup)
    public void signup()
    {
        Intent intent = new Intent(
                getApplicationContext(), // 현재 화면의 제어권자
                JoinActivity.class); // 다음 넘어갈 클래스 지정
        startActivity(intent); // 다음 화면으로 넘어간다
    }
}
