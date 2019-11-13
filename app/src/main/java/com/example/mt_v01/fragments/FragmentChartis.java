package com.example.mt_v01.fragments;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mt_v01.R;
import com.example.mt_v01.cardviewpager.CardViewPagerAdapter;
import com.example.mt_v01.cardviewpager.animators.AlphaTransformer;
import com.example.mt_v01.cardviewpager.animators.ScaleTransformer;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.List;

public class FragmentChartis extends Fragment {
    MapView map = null;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    Context ctx;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_chartis, container,false );
        ctx = view.getContext();

        //Create the challenges card view pager
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.cardView);
        viewPager.setPageMargin(20);
        viewPager.setOffscreenPageLimit(3);
        List<Integer> list = new ArrayList<>();
        list.add(1);//TODO: instead of a number, add the appropriate amount of challenge object/id.
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        CardViewPagerAdapter cardViewPagerAdapter = new CardViewPagerAdapter(ctx, list);
        viewPager.setAdapter(cardViewPagerAdapter);
        viewPager.setPageMargin((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                11, getResources().getDisplayMetrics()));
//      viewPager.setPageTransformer(false, new AlphaTransformer()); //enable only if we want opacity to change on card view pager
        viewPager.setPageTransformer(false, new ScaleTransformer(ctx));

        //make map
        map = view.findViewById(R.id.map_view);
        // Create a custom tile source

        map.setTileSource(TileSourceFactory.MAPNIK);

        //give start point to the map and add some functionality to it
        //handle permissions first, to show user location (shown in callback onRequestPermissionsResult)
        setUpMap(map);
        getLocationPermission();
        return view;
    }

    private void setUpMap(final MapView map) {

        // two-finger rotation
        RotationGestureOverlay mRotationGestureOverlay = new RotationGestureOverlay(ctx, map);
        mRotationGestureOverlay.setEnabled(true);
        map.getOverlays().add(mRotationGestureOverlay);

        //add compass
        CompassOverlay mCompassOverlay = new CompassOverlay(ctx,
                new InternalCompassOrientationProvider(ctx),
                map);
        mCompassOverlay.enableCompass();
        map.getOverlays().add(mCompassOverlay);


        //hide the bad-looking osm default zoom controls
        map.setBuiltInZoomControls(false);
        map.setMultiTouchControls(true);
        IMapController mapController = map.getController();
        mapController.setZoom(17);

        //Tinos, Pyrgos st as the starting point
        GeoPoint startPoint = new GeoPoint(37.639115, 25.041889);
        mapController.setCenter(startPoint);
        map.setMinZoomLevel(10.0);
        map.setMaxZoomLevel(21.0);

        //your items
        final ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
        items.add(new OverlayItem("1","1",new GeoPoint(37.638143, 25.039033)));
        items.add(new OverlayItem("2","2", new GeoPoint(37.637878, 25.038593)));
        items.add(new OverlayItem("3","3", new GeoPoint(37.637917, 25.038434)));
        items.add(new OverlayItem("4","4", new GeoPoint(37.637917, 25.038434)));
        items.add(new OverlayItem("5","5",new GeoPoint(37.638329, 25.038494)));
        final ItemizedIconOverlay myItemizedIconOverlay = new ItemizedIconOverlay(items, getResources().getDrawable(R.drawable.ic_map_mission_proximal), new ItemizedIconOverlay.OnItemGestureListener() {
            @Override
            public boolean onItemSingleTapUp(int index, Object item) {
//                items.get(index).setMarker(getResources().getDrawable(R.drawable.arximastoras));
                map.getController().animateTo(items.get(index).getPoint());
                return false;
            }

            @Override
            public boolean onItemLongPress(int index, Object item) {
                return false;
            }
        }, map.getContext());
        map.getOverlays().add(myItemizedIconOverlay);
/*
        Marker marker = new Marker(map);
        marker.setPosition(new GeoPoint(37.638143, 25.039033));
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setIcon(getResources().getDrawable(R.drawable.ic_map_mission_proximal));
        map.getOverlays().add(marker);*/
        map.invalidate();


        final Context context = this.getActivity();
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();
        ScaleBarOverlay mScaleBarOverlay = new ScaleBarOverlay(map);
        mScaleBarOverlay.setCentred(true);
        //play around with these values to get the location on screen in the right place for your application
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels/2, 20);
        map.getOverlays().add(mScaleBarOverlay);

        //do stuff on zoom level changed
        map.addMapListener(new MapListener() {
            @Override
            public boolean onScroll(ScrollEvent event) {
                return false;
            }

            @Override
            public boolean onZoom(ZoomEvent event) {
//                Toast.makeText(ctx, String.valueOf(event.getZoomLevel()), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    public void onResume(){
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    public void onPause(){
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }


    private void getLocationPermission() {
        // declare the permissions to be checked / requested
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};

        // check permissions (if already granted)
        if (ContextCompat.checkSelfPermission(ctx,
                permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            //permission granted
            setUserLocationEnabled();
        } else { // not granted (from previous interaction)
            // request permissions
            ActivityCompat.requestPermissions((Activity) ctx, permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
            // a code is passed, that will be checked in onRequestPermissionResult callback
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // switch request code, to identify which which permission was requested this time..
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setUserLocationEnabled();
            }
        }
    }

    private void setUserLocationEnabled(){
        GpsMyLocationProvider gpsMyLocationProvider = new GpsMyLocationProvider(ctx);
        gpsMyLocationProvider.setLocationUpdateMinDistance(100); // [m]  // Set the minimum distance for location updates
        gpsMyLocationProvider.setLocationUpdateMinTime(10000);   // [ms] // Set the minimum time interval for location updates
        MyLocationNewOverlay mLocationOverlay = new MyLocationNewOverlay(gpsMyLocationProvider, map);
        mLocationOverlay.enableMyLocation();
//        mLocationOverlay.enableFollowLocation();
        map.getOverlays().add(mLocationOverlay);

    }

}
