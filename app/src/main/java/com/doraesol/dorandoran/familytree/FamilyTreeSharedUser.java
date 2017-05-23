package com.doraesol.dorandoran.familytree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doraesol.dorandoran.R;

/**
 * Created by YOONGOO on 2017-05-24.
 */

public class FamilyTreeSharedUser extends LinearLayout{
    ImageView iv_shared_user;
    TextView tv_shared_user;

    public FamilyTreeSharedUser(Context paramContext){
        super(paramContext);
        initializeView();
    }

    private void initializeView(){
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View view = li.inflate(R.layout.shared_user, this, false);
        addView(view);
        iv_shared_user = (ImageView) findViewById(R.id.iv_shared_user);
        tv_shared_user = (TextView) findViewById(R.id.tv_shared_user);
    }

    public void setImage(int resId) {
        this.iv_shared_user.setImageResource(resId);
    }
    public void setText(String name)  {
        this.tv_shared_user.setText(name);
    }
    public String getText() { return this.tv_shared_user.getText().toString(); }
}
