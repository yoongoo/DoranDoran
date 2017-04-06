package com.doraesol.dorandoran;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JJY on 2017-04-01.
 */

public class MenuActivity extends AppCompatActivity
{
    @BindView(R.id.familytree_bt) ImageView familytree_bt;
    @BindView(R.id.map_bt) ImageView map_bt;
    @BindView(R.id.calendar_bt) ImageView calendar_bt;
    @BindView(R.id.setting_bt) ImageView setting_bt;
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        backPressCloseHandler = new BackPressCloseHandler(this);
        ButterKnife.bind(this);
    }

    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }


    @OnClick(R.id.familytree_bt)
    public void OnFamilyButtonClicked()
    {
        Intent intent = new Intent(
                getApplicationContext(), // 현재 화면의 제어권자
                MainActivity.class);// 다음 넘어갈 클래스 지정
        intent.putExtra("page", 1);
        startActivity(intent); // 다음 화면으로 넘어간다
    }
    @OnClick(R.id.map_bt)
    public void OnMapButtonClicked()
    {
        Intent intent = new Intent(
                getApplicationContext(), // 현재 화면의 제어권자
                MainActivity.class); // 다음 넘어갈 클래스 지정
        intent.putExtra("page", 2);
        startActivity(intent); // 다음 화면으로 넘어간다
    }
    @OnClick(R.id.calendar_bt)
    public void OnCalendarButtonClicked()
    {
        Intent intent = new Intent(
                getApplicationContext(), // 현재 화면의 제어권자
                MainActivity.class); // 다음 넘어갈 클래스 지정
        intent.putExtra("page", 3);
        startActivity(intent); // 다음 화면으로 넘어간다
    }
    @OnClick(R.id.setting_bt)
    public void OnSettingButtonClicked()
    {
        Intent intent = new Intent(
                getApplicationContext(), // 현재 화면의 제어권자
                MainActivity.class); // 다음 넘어갈 클래스 지정
        intent.putExtra("page", 4);
        startActivity(intent); // 다음 화면으로 넘어간다
    }
}
