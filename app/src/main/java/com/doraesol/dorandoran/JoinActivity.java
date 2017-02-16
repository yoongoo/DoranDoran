package com.doraesol.dorandoran;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JoinActivity extends AppCompatActivity {

    @BindView(R.id.bt_sign) ImageButton bt_sign;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.bt_sign)
    public void sign()
    {
        Intent intent = new Intent(
                getApplicationContext(), // 현재 화면의 제어권자
                LoginActivity.class); // 다음 넘어갈 클래스 지정
        startActivity(intent); // 다음 화면으로 넘어간다
    }
}
