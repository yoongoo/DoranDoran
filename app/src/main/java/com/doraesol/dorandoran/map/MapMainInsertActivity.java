package com.doraesol.dorandoran.map;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.doraesol.dorandoran.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapMainInsertActivity extends AppCompatActivity {

    @BindView(R.id.btn_ok)
    Button btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_main_insert);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_ok)
    public void OnClick(View view) {
        if(view.getId() == R.id.btn_ok) {
            Intent intent = new Intent();
            intent.putExtra("name", "결과가 넘어간다 얍!");
            setResult(RESULT_OK, intent);
            finish();
        }
    }

}
