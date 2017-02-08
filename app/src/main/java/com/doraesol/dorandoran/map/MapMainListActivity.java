package com.doraesol.dorandoran.map;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.doraesol.dorandoran.R;

public class MapMainListActivity extends AppCompatActivity {

    GridView androidGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_main_list);

        final String[] gridViewString = {
                "Alram", "Android", "Mobile", "Website", "Profile", "WordPress",
                "Alram", "Android", "Mobile", "Website", "Profile", "WordPress",
                "Alram", "Android", "Mobile", "Website", "Profile", "WordPress",

        } ;

        final int[] gridViewImageId = {
                R.drawable.img_main_user, R.drawable.img_main_user, R.drawable.img_main_user, R.drawable.img_main_user, R.drawable.img_main_user, R.drawable.img_main_user,
                R.drawable.img_main_user, R.drawable.img_main_user, R.drawable.img_main_user, R.drawable.img_main_user, R.drawable.img_main_user, R.drawable.img_main_user,
                R.drawable.img_main_user, R.drawable.img_main_user, R.drawable.img_main_user, R.drawable.img_main_user, R.drawable.img_main_user, R.drawable.img_main_user,

        };

        MapListGridViewAdpater adapterViewAndroid = new MapListGridViewAdpater(MapMainListActivity.this, gridViewString, gridViewImageId);
        androidGridView = (GridView)findViewById(R.id.gv_map_list);
        androidGridView.setAdapter(adapterViewAndroid);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                Toast.makeText(MapMainListActivity.this, "GridView Item: " + gridViewString[+i], Toast.LENGTH_LONG).show();
            }
        });
    }

    public class MapListGridViewAdpater extends BaseAdapter {
        private Context context;
        private final String[] gridViewString;
        private final int[] gridViewImageId;

        public MapListGridViewAdpater(Context context, String[] gridViewString, int[] gridViewImageId) {
            this.context = context;
            this.gridViewString = gridViewString;
            this.gridViewImageId = gridViewImageId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View gridView;

            if (convertView == null) {

                gridView = new View(context);
                gridView = inflater.inflate(R.layout.map_main_listitem, null);
                TextView textView = (TextView) gridView.findViewById(R.id.tv_map_item);
                ImageView imageView = (ImageView) gridView.findViewById(R.id.iv_map_item);
                textView.setText(gridViewString[position]);
                imageView.setImageResource(gridViewImageId[position]);
            } else {
                gridView = (View) convertView;
            }
            return gridView;
        }

        @Override
        public int getCount() {
            return gridViewString.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

    }
}
