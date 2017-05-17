package com.doraesol.dorandoran;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.doraesol.dorandoran.map.MapMainFragment;
import com.doraesol.dorandoran.setting.SettingFragment;
import com.doraesol.dorandoran.social.CmtBoardFragment;
import com.tsengvn.typekit.TypekitContextWrapper;
import java.io.IOException;
import java.io.InputStream;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
}
