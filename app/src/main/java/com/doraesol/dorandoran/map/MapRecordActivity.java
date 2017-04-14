package com.doraesol.dorandoran.map;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.doraesol.dorandoran.R;

/**
 * Created by JJY on 2017-04-11.
 */

public class MapRecordActivity extends AppCompatActivity implements GeolocationPermissions.Callback
{
    final int REQUEST_PICTURE = 1000;
    WebView mapview;
    String url="file:///android_asset/www/index.html";
    Uri uri = null;
    private final Handler handler = new Handler();

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_record);

        mapview=(WebView) findViewById(R.id.mapview);	//casting webview
        mapview.getSettings().setJavaScriptEnabled(true);						//webview options
        mapview.getSettings().setGeolocationEnabled(true);
        mapview.getSettings().setDatabasePath("/data/data/"+this.getPackageName()+"/databases/");
        mapview.getSettings().setDomStorageEnabled(true);
        mapview.getSettings().setDatabaseEnabled(true);
        mapview.getSettings().setAppCacheEnabled(true);

        Geoclient geoclient=new Geoclient();	//casting class

        mapview.setWebChromeClient(geoclient);	//set webchromeclient for permission
        String origin="";
        geoclient.onGeolocationPermissionsShowPrompt(origin,this);	//for permission
        mapview.addJavascriptInterface(new JJJavaScriptInterface(), "call");
        mapview.loadUrl(url);

    }

    @Override
    public void invoke(String origin, boolean allow, boolean retain) {}

    class Geoclient extends WebChromeClient
    {    //for display mylocation
        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {

            super.onGeolocationPermissionsShowPrompt(origin, callback);
            callback.invoke(origin, true, false);
        }

		/*@Override
		public void onExceededDatabaseQuota(String url,String databaseIdentifier, long quota,//for database
				long estimatedDatabaseSize, long totalQuota,WebStorage.QuotaUpdater quotaUpdater) {

			super.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize, totalQuota, quotaUpdater);

			quotaUpdater.updateQuota(estimatedDatabaseSize*2);
		}*/

    }

    public  class JJJavaScriptInterface
    {
        public JJJavaScriptInterface(){}

        @JavascriptInterface
        public void callAndroid()
        {
            handler.post(new Runnable() {
                @Override
                public void run()
                {
                    startCameraActivity();
                    Toast.makeText(MapRecordActivity.this, "찰칵~ 찰칵~", Toast.LENGTH_SHORT).show();
                }
            });
        }
        @JavascriptInterface
        public void callCamera()
        {

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

        }
    }

    public void startCameraActivity() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_PICTURE);
        }
    }


}
