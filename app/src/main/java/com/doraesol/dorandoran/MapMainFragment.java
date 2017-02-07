package com.doraesol.dorandoran;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.support.design.widget.TabLayout;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapMainFragment extends Fragment implements OnMapReadyCallback, TabLayout.OnTabSelectedListener{

    private GoogleMap m_googleMap;
    private MapView m_mapView;
    @BindView(R.id.iv_map_search) ImageView iv_map_search;
    @BindView(R.id.tl_map_tabs) TabLayout tl_map_tabs;



    static final LatLng LOCATION_SEOUL = new LatLng(37.56, 126.97);


    public MapMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map_main, container, false);
        m_mapView = (MapView) rootView.findViewById(R.id.google_map);
        m_mapView.onCreate(savedInstanceState);
        m_mapView.onResume();
        m_mapView.getMapAsync(this);
        ButterKnife.bind(this, rootView);

        tl_map_tabs.addTab(tl_map_tabs.newTab().setText("신규등록").setIcon(R.drawable.ic_map_target));
        tl_map_tabs.addTab(tl_map_tabs.newTab().setText("경로목록").setIcon(R.drawable.ic_map_list));
        tl_map_tabs.addTab(tl_map_tabs.newTab().setText("즐겨찾기").setIcon(R.drawable.ic_map_star));
        tl_map_tabs.setSelectedTabIndicatorHeight(0);
        tl_map_tabs.setOnTabSelectedListener(this);

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap map) {
    }

    @OnClick(R.id.iv_map_search)
    public void OnSearchClick() {
        Toast.makeText(getActivity(), "검색 버튼 클릭", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        final int position = tab.getPosition();
        Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
