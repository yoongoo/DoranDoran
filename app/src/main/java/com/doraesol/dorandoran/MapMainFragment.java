package com.doraesol.dorandoran;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapMainFragment extends Fragment implements OnMapReadyCallback{

    private GoogleMap m_googleMap;
    private MapView m_mapView;


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

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap map) {

    }
}
