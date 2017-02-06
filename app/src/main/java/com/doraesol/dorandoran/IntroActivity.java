package com.doraesol.dorandoran;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ImageView iv_title = (ImageView) findViewById(R.id.iv_title) ;
        iv_title.setImageResource(R.drawable.img_intro) ;

    }
}
