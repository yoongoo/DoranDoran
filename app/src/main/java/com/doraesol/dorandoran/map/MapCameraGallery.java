package com.doraesol.dorandoran.map;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.doraesol.dorandoran.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MapCameraGallery extends AppCompatActivity
{
    final int REQUEST_PICTURE = 1000;
    private Uri uri;
    Bundle extraBundle;
    @BindView(R.id.iv_map_picture) ImageView iv_map_picture;
    @BindView(R.id.iv_map_gallery) ImageView iv_map_gallery;
    @BindView(R.id.iv_map_photo) ImageView iv_map_photo;
    @BindView(R.id.bt_map_ok) Button bt_map_ok;
    @BindView(R.id.bt_map_cancel) Button bt_map_cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_cameragallery);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.iv_map_picture, R.id.iv_map_gallery, R.id.bt_map_ok, R.id.bt_map_cancel})
    public void OnButtonClicked(View paramView)
    {


        switch (paramView.getId())
        {
            case R.id.iv_map_picture:
                Toast.makeText(getApplicationContext(), "사진 촬영", Toast.LENGTH_SHORT).show();
                startCameraActivity();
                break;

            case R.id.iv_map_gallery:
                Toast.makeText(getApplicationContext(), "갤러리", Toast.LENGTH_SHORT).show();
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

            case R.id.bt_map_ok:
                Toast.makeText(getApplicationContext(), "확인되었습니다.", Toast.LENGTH_SHORT).show();

                finish();
                extraBundle = new Bundle();
                extraBundle.putString("uri", uri.toString());
                Intent intent1 = new Intent();
                intent1.putExtras(extraBundle);
                this.setResult(RESULT_OK,intent1);

                break;

            case R.id.bt_map_cancel:
                Toast.makeText(getApplicationContext(), "취소되었습니다.", Toast.LENGTH_SHORT).show();

                Intent intent2 = new Intent(
                        getApplicationContext(), // 현재 화면의 제어권자
                        MapMyRouteActivity.class); // 다음 넘어갈 클래스 지정
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2); // 다음 화면으로 넘어간다
                break;

            default:
                break;

        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICTURE && resultCode == Activity.RESULT_OK)
        {
            Log.d("onActivityResult", "Camera SUCCESS");
            uri = data.getData();

            Glide.with(this)
                    .load(uri)
                    .centerCrop()
                    .crossFade()
                    .into(iv_map_photo);
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
}
