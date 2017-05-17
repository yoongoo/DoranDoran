package com.doraesol.dorandoran.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.doraesol.dorandoran.R;

/**
 * Created by YOONGOO on 2017-04-16.
 */

public class IconSpinnerAdapter extends BaseAdapter{
    ImageView iv_icon;
    int[] imageResource;
    Context context;
    LayoutInflater inflater;

    public IconSpinnerAdapter(Context context, int[] imageResource) {
        this.context = context;
        this.inflater = (LayoutInflater.from(context));
        this.imageResource = imageResource;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.dlg_calendar_insert_spinner_item, null);
        ImageView iv = (ImageView)convertView.findViewById(R.id.iv_spinner_item);
        iv.setImageResource(imageResource[position]);

        return convertView;
    }
}
