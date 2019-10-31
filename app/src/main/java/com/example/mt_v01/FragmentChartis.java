package com.example.mt_v01;
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


import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.MapTileIndex;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

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
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        //setting this before the layout is inflated is a good idea
        //it 'should' ensure that the map has a writable location for the map cache, even without permissions
        //if no tiles are displayed, you can try overriding the cache path using Configuration.getInstance().setCachePath
        //see also StorageUtils
        //note, the load method also sets the HTTP User Agent to your application's package name, abusing osm's tile servers will get you banned based on this string



        //make map
        map = view.findViewById(R.id.map_view);
        // Create a custom tile source

        /*final ITileSource tileSource = new XYTileSource( "Humanitarian", 1, 20, 256, ".png",
                new String[] {
                        "http://a.tile.openstreetmap.fr/",
                        "http://b.tile.openstreetmap.fr/",
                        "http://c.tile.openstreetmap.fr/" },"© OpenStreetMap contributors");*/
        /*map.setTileSource(new OnlineTileSourceBase("USGS Topo", 0, 18, 256, "",
                new String[] { "http://basemap.nationalmap.gov/ArcGIS/rest/services/USGSTopo/MapServer/tile/" }) {
            @Override
            public String getTileURLString(long pMapTileIndex) {
                return getBaseUrl()
                        + MapTileIndex.getZoom(pMapTileIndex)
                        + "/" + MapTileIndex.getY(pMapTileIndex)
                        + "/" + MapTileIndex.getX(pMapTileIndex)
                        + mImageFilenameEnding;
            }
        });*/

        map.setTileSource(TileSourceFactory.MAPNIK);

        //created tiles with Maperitive, and placed them in Assets folder in "OSM" folder name
        //give start point to the map and add some functionality to it
        //handle permissions first, to show user location (shown in callback onRequestPermissionsResult)
        setUpMap(map);
        getLocationPermission();
        return view;
    }

    private void setUpMap(MapView map) {

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
                Toast.makeText(ctx, String.valueOf(event.getZoomLevel()), Toast.LENGTH_SHORT).show();
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
