package com.doraesol.dorandoran;

import android.app.Application;
import com.tsengvn.typekit.Typekit;

/**
 * Created by YOONGOO on 2017-04-16.
 */

public class CustomFont extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this,"fonts/cherry_blossom_B.ttf"));
    }


}
