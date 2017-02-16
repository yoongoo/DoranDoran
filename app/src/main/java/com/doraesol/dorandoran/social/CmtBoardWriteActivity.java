package com.doraesol.dorandoran.social;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.doraesol.dorandoran.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CmtBoardWriteActivity extends AppCompatActivity {

    final int REQUEST_PICTURE = 1000;
    @BindView(R.id.fam_cmt_write)           FloatingActionMenu fam_cmt_write;
    @BindView(R.id.fab_cmt_insert_picture)  FloatingActionButton fab_cmt_insert_picture;
    @BindView(R.id.fab_cmt_insert_gallery)  FloatingActionButton fab_cmt_insert_gallery;
    @BindView(R.id.iv_cmt_write_picture)    ImageView iv_cmt_write_picture;
    @BindView(R.id.iv_cmt_insert_confirm)   ImageView iv_cmt_insert_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmt_board_write);
        ButterKnife.bind(this);
        fam_cmt_write.setClosedOnTouchOutside(true);
    }

    @OnClick({R.id.fab_cmt_insert_picture, R.id.fab_cmt_insert_gallery})
    public void OnFabCmtClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_cmt_insert_gallery:
                int permissionCheck = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE);

                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    startGallery();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            2000);
                }
                break;

            case R.id.fab_cmt_insert_picture:
                startCameraActivity();
                break;

            default:
                break;
        }
    }

    @OnClick({R.id.iv_cmt_insert_confirm, R.id.iv_cmt_insert_back})
    public void OnImageButtonClicked(View view){
        switch (view.getId()) {
            case R.id.iv_cmt_insert_back:
                printToast("작성완료");
                break;

            case R.id.iv_cmt_insert_confirm:
                printToast("뒤로가기");
                break;

            default:
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICTURE && resultCode == Activity.RESULT_OK) {
            Log.d("onActivityResult", "Camera SUCCESS");
            Uri uri = data.getData();

            Glide.with(this)
                    .load(uri)
                    .centerCrop()
                    .crossFade()
                    .into(iv_cmt_write_picture);
        }
    }

    public void startGallery() {
        Intent cameraIntent = new Intent(Intent.ACTION_GET_CONTENT);
        cameraIntent.setType("image/*");
        if (cameraIntent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_PICTURE);
        }
    }

    public void startCameraActivity() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_PICTURE);
        }
    }

    private void printToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
