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
import com.example.mt_v01.helperclasses.Challenge;

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
    private List<Challenge> challenges_list = new ArrayList<>();
    private CardViewPagerAdapter cardViewPagerAdapter;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_chartis, container,false );
        ctx = view.getContext();

        //challenges


        //Create the challenges card view pager
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        viewPager = (ViewPager) view.findViewById(R.id.cardView);
        viewPager.setPageMargin(20);
        viewPager.setOffscreenPageLimit(3);
        //TODO: instead of my class, use the proper challenge class from Panagiotis.
        //VRYSI
        challenges_list.add(new Challenge(R.drawable.ch_marmarenia_vrisi, "Ψάξτε τις επιγραφές της κρήνης. Ποια η χρονολογία της 1ης, 2ης, και 3ης φάσης κατασκευής της;",getResources().getString(R.string.challenge_simplirwsis),getResources().getString(R.string.area_plateia_xwriou),5,
                37.639168, 25.042064));
        challenges_list.add(new Challenge(R.drawable.ch_marmarenia_vrisi_fiali, "Ποια είναι η χρονολογία της κατασκευής της φιάλης, η οποία αναγράφεται με ελληνικούς αριθμούς;",getResources().getString(R.string.challenge_simplirwsis),getResources().getString(R.string.area_plateia_xwriou),5,
                37.639223, 25.042090));
        challenges_list.add(new Challenge(R.drawable.ch_marmarenia_vrisi, "Ποιο γεγονός μαρτυρεί η επιγραφή στη μεσαία κολόνα της πρόσοψης;",getResources().getString(R.string.challenge_multiple_choice_text),getResources().getString(R.string.area_plateia_xwriou),5,
                37.639201, 25.042070));
        challenges_list.add(new Challenge(R.drawable.ch_plistres, "Αναζητήστε λίγο μακρύτερα (στο δρόμο αριστερά) τις «πλύστρες» του χωριού, όπου οι γυναίκες έκαναν τη μπουγάδα τους, με νερό που ερχόταν από την κρήνη. Πότε κατά την επιγραφή κατασκεύαστηκαν;",getResources().getString(R.string.challenge_simplirwsis),getResources().getString(R.string.area_plystres),5,
                37.6393839058,25.0422025335));
        challenges_list.add(new Challenge(R.drawable.ch_marmarenia_vrisi_fiali, "Σε τι χρησιμεύει η φιάλη μεταξύ των κρουνών;",getResources().getString(R.string.challenge_multiple_choice_text),getResources().getString(R.string.area_plateia_xwriou),5,
                37.639230, 25.042064));
        challenges_list.add(new Challenge(R.drawable.ch_pirgos_platanos, "Απέναντι από τη μαρμαρένια κρήνη είναι ο Πλάτανος της πλατείας. Κοιτώντας τον Πλάτανο αναζητήστε την επιγραφή με την ημερομηνία του \"ΕΥΚΑΛΥΠΤΟΥ\"",getResources().getString(R.string.challenge_simplirwsis),getResources().getString(R.string.area_plateia_xwriou),5,
                37.639155, 25.042083));
        challenges_list.add(new Challenge(R.drawable.ch_marmarenia_vrisi, "Αναγνωρίζετε τα στοιχεία μπαρόκ της πρώτης φάσης της μαρμαρένιας κρήνης;",getResources().getString(R.string.challenge_type_anazhthsh),getResources().getString(R.string.challenge_tap_on_photo),5,
                37.639197, 25.042102));
        challenges_list.add(new Challenge(R.drawable.ch_marmarenia_vrisi, "Ποιά από τα στοιχεία της πρώτης φάσης της μαρμαρένιας κρήνης, ο χαρακτήρας τους είναι φυσικός και ποιων υπερφυσικός;",getResources().getString(R.string.challenge_tap_on_photo),getResources().getString(R.string.area_plateia_xwriou),5,
                37.639266, 25.042114));
        challenges_list.add(new Challenge(R.drawable.ch_marmarenia_vrisi, "Πού βρίσκονται και ποια είναι τα νεοκλασικά στοιχεία που ανήκουν στη δεύτερη και την τρίτη κατασκευαστική φάση της μαρμαρένιας κρήνης;",getResources().getString(R.string.challenge_tap_on_photo),getResources().getString(R.string.area_plateia_xwriou),5,
                37.639223, 25.042022));

        //LATOMEIO
        challenges_list.add(new Challenge(R.drawable.ch_spastira, "Ανεβαίνοντας το δρομάκι προς το Νταμάρι του Αγίου Λευτερίου, συναντάτε μια μηχανή. Πως (πιστεύετε ότι) ονομάζεται;",getResources().getString(R.string.challenge_simplirwsis),getResources().getString(R.string.area_plateia_xwriou),5,
                37.638143, 25.039033));
        challenges_list.add(new Challenge(R.drawable.ch_gefyraki_siderenio_latomeio, "Περνώντας το σιδερένιο γεφυράκι αποκαλύπτεται ένα… ",getResources().getString(R.string.challenge_simplirwsis),getResources().getString(R.string.area_plateia_xwriou),5,
                37.637878, 25.038593));
        challenges_list.add(new Challenge(R.drawable.ch_katastegi, "Κατεβαίνοντας το επίπεδο δίπλα στο θεατράκι  μπορείς να βρεις την παλαιότερη χαραγμένη σ’ αυτό χρονολογία πάνω από το λιθόκτιστο καμαράκι;",getResources().getString(R.string.challenge_multiple_choice_text),getResources().getString(R.string.area_plateia_xwriou),5,
                37.637917, 25.038434));
        challenges_list.add(new Challenge(R.drawable.ch_latomeio_mixanima, "Στον σκαμμένο βράχο (κούτελο) δίπλα στο καμαράκι, υπάρχει ένα όνομα που έχει χαραχθεί και είναι...;",getResources().getString(R.string.challenge_simplirwsis),getResources().getString(R.string.area_plystres),5,
                37.638291, 25.038606));
        challenges_list.add(new Challenge(R.drawable.ch_latomeio_mixanima, "Ο δρόμος εδώ είναι υπερυψωμένος, ανάμεσα από επίπεδα που έχουν κατεβεί από τη λατόμηση. Στην αρχή του, αριστερά, θα δεις λίθινα κτίσματα. Σε τί χρησίμευαν;",getResources().getString(R.string.challenge_multiple_choice_text),getResources().getString(R.string.area_plateia_xwriou),5,
                37.637891, 25.038434));
        challenges_list.add(new Challenge(R.drawable.ch_latomeio_mixanima, "Τι χρώμα μάρμαρο υπάρχει στο νταμάρι;",getResources().getString(R.string.challenge_simplirwsis),getResources().getString(R.string.area_plateia_xwriou),5,
                37.638172, 25.038777));

 /*       //MOXTHOS K DOXA
        challenges_list.add(new Challenge(R.drawable.ch_marmarenia_vrisi, "Ανεβαίνοντας το δρομάκι προς το Νταμάρι του Αγίου Λευτερίου, συναντάτε μια μηχανή. Πως (πιστεύετε ότι) ονομάζεται;",getResources().getString(R.string.challenge_simplirwsis),getResources().getString(R.string.area_plateia_xwriou),5,
                37.638143, 25.039033));
        challenges_list.add(new Challenge(R.drawable.ch_marmarenia_vrisi_fiali, "Περνώντας το σιδερένιο γεφυράκι αποκαλύπτεται ένα… ",getResources().getString(R.string.challenge_simplirwsis),getResources().getString(R.string.area_plateia_xwriou),5,
                37.637878, 25.038593));
        challenges_list.add(new Challenge(R.drawable.ch_marmarenia_vrisi, "Κατεβαίνοντας το επίπεδο δίπλα στο θεατράκι  μπορείς να βρεις την παλαιότερη χαραγμένη σ’ αυτό χρονολογία πάνω από το λιθόκτιστο καμαράκι;",getResources().getString(R.string.challenge_multiple_choice_text),getResources().getString(R.string.area_plateia_xwriou),5,
                37.637917, 25.038434));
        challenges_list.add(new Challenge(R.drawable.ch_plistres, "Στον σκαμμένο βράχο (κούτελο) δίπλα στο καμαράκι, υπάρχει ένα όνομα που έχει χαραχθεί και είναι...;",getResources().getString(R.string.challenge_simplirwsis),getResources().getString(R.string.area_plystres),5,
                37.638291, 25.038606));
        challenges_list.add(new Challenge(R.drawable.ch_marmarenia_vrisi_fiali, "Ο δρόμος εδώ είναι υπερυψωμένος, ανάμεσα από επίπεδα που έχουν κατεβεί από τη λατόμηση. Στην αρχή του, αριστερά, θα δεις λίθινα κτίσματα. Σε τί χρησίμευαν;",getResources().getString(R.string.challenge_multiple_choice_text),getResources().getString(R.string.area_plateia_xwriou),5,
                37.637891, 25.038434));
        challenges_list.add(new Challenge(R.drawable.ch_pirgos_platanos, "Τι χρώμα μάρμαρο υπάρχει στο νταμάρι;",getResources().getString(R.string.challenge_simplirwsis),getResources().getString(R.string.area_plateia_xwriou),5,
                37.638172, 25.038777));*/


        cardViewPagerAdapter = new CardViewPagerAdapter(ctx, challenges_list);
        viewPager.setAdapter(cardViewPagerAdapter);
        viewPager.setPageMargin((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                11, getResources().getDisplayMetrics()));
//      viewPager.setPageTransformer(false, new AlphaTransformer()); //enable only if we want opacity to change on card view pager
        viewPager.setPageTransformer(false, new ScaleTransformer(ctx));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                map.getController().animateTo(new GeoPoint(challenges_list.get(position).getLatitude(),challenges_list.get(position).getLongitude()));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
        mCompassOverlay.setCompassCenter(view.getX()+40f,view.getY()+50f);
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

        int i=0;
        while(i<challenges_list.size()){
            items.add(new OverlayItem(String.valueOf(i),String.valueOf(i),new GeoPoint(challenges_list.get(i).getLatitude(),challenges_list.get(i).getLongitude())));
            i++;
        }
        final ItemizedIconOverlay myItemizedIconOverlay = new ItemizedIconOverlay(items, getResources().getDrawable(R.drawable.ic_map_mission_proximal), new ItemizedIconOverlay.OnItemGestureListener() {
            @Override
            public boolean onItemSingleTapUp(int index, Object item) {
//                items.get(index).setMarker(getResources().getDrawable(R.drawable.arximastoras));
                map.getController().animateTo(items.get(index).getPoint());
                viewPager.setCurrentItem(index);

                return false;
            }

            @Override
            public boolean onItemLongPress(int index, Object item) {

                return false;
            }
        }, map.getContext());
        map.getOverlays().add(myItemizedIconOverlay);
        map.invalidate();


        final Context context = this.getActivity();
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();
        ScaleBarOverlay mScaleBarOverlay = new ScaleBarOverlay(map);
        mScaleBarOverlay.setCentred(true);
        //play around with these values to get the location on screen in the right place for your application
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels/2, 50);
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
