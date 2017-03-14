package com.doraesol.dorandoran.map;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.doraesol.dorandoran.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MapSearchActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final String LOG_TAG = MapSearchActivity.class.getSimpleName();
    private GoogleMap mMap;
    private Marker marker;

    private String origin;
    private String dest;
    private String GOOGLE_DIRECTION_API_KEY;
    @BindView(R.id.btn_map_search)
    Button btn_map_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_search);
        ButterKnife.bind(this);

        GOOGLE_DIRECTION_API_KEY = getResources().getString(R.string.direction_api_key);
        // Load map
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Load suggestion box
        PlaceAutocompleteFragment autocompleteFragment1 = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.fg_starting);
        PlaceAutocompleteFragment autocompleteFragment2 = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.fg_ending);


        //autocompleteFragment1.setHint("출발지");
        //autocompleteFragment2.setHint("도착지");

        //((EditText)autocompleteFragment1.getView().findViewById(R.id.fg_starting)).setTextSize(10.0f);
        //((EditText)autocompleteFragment2.getView().findViewById(R.id.fg_ending)).setTextSize(10.0f);


        autocompleteFragment1.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place1) {
                // TODO: Get info about the selected place.
                LatLng latLng = place1.getLatLng();
                origin = "place_id:" + place1.getId();
                //origin = place1.getAddress().toString();
                CameraUpdate center = CameraUpdateFactory.newLatLng(latLng);
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);
                mMap.moveCamera(center);
                mMap.animateCamera(zoom);
                mMap.clear();

                marker = mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
                //Log.d(LOG_TAG, "origin name : " + place1.getName().toString());
                //Log.d(LOG_TAG, "origin address : " + place1.getAddress().toString());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(LOG_TAG, "An error occurred: " + status);
            }
        });

        autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place2) {
                // TODO: Get info about the selected place.
                LatLng latLng = place2.getLatLng();
                dest = "place_id:" + place2.getId();
                //dest = place2.getAddress().toString();
                //dest_Lat = latLng.latitude;
                //dest_Lng = latLng.longitude;
                CameraUpdate center = CameraUpdateFactory.newLatLng(latLng);
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);
                mMap.moveCamera(center);
                mMap.animateCamera(zoom);
                mMap.clear();

                marker = mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(LOG_TAG, "An error occurred: " + status);
            }
        });
    }

    @OnClick(R.id.btn_map_search)
    public void onMapSearchButtonClicked(){
        FindPathTask findPathTask = new FindPathTask();
        findPathTask.execute(origin,dest);
        Log.d(LOG_TAG, "origin : " + origin + " " + "dest : " + dest);
        Log.d(LOG_TAG, "FindPathTask executed. .");
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        LatLng cbnu = new LatLng(36.625123, 127.457177);
        mMap.addMarker(new MarkerOptions().position(cbnu).title("학연산"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cbnu, 18));
    }

    class FindPathTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            final String START_POINT   = params[0];
            final String END_POINT     = params[1];
            String request_url         =
                    "https://maps.googleapis.com/maps/api/directions/json?" +
                            "origin=" + START_POINT + "&" +
                            "destination=" + END_POINT + "&" +
                            "key=" + GOOGLE_DIRECTION_API_KEY;

            Log.d(LOG_TAG, "REQUEST URL : " + request_url);
            OkHttpClient client = new OkHttpClient();


            Request request = new Request.Builder()
                    .url(request_url)
                    .build();


            try {
                Response response = client.newCall(request).execute();

                return response.body().string();

            } catch (IOException e) {
                e.printStackTrace();

                return null;
            }
        }


        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);

            Log.d(LOG_TAG, str);
        }
    }
}
