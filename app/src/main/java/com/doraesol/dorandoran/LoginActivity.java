package com.doraesol.dorandoran;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImageView iv_login = (ImageView) findViewById(R.id.iv_login) ;
        iv_login.setImageResource(R.drawable.img_log_sprout) ;
    }
}
