package com.example.mt_v01;

import android.os.Bundle;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private NoSwipePager viewPager;
    private BottomBarAdapter pagerAdapter;
    private BottomNavigationView navView;
    private RoundCornerProgressBar progressTwo;
    FragmentApostoles fragmentApostoles = new FragmentApostoles();
    FragmentChartis fragmentChartis = new FragmentChartis();
    FragmentErgaliothiki fragmentErgaliothiki = new FragmentErgaliothiki();
    FragmentRithmisis fragmentRithmisis = new FragmentRithmisis();


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.null_item:
                    return true;
                case R.id.apostoles:
                    navView.setItemIconTintList(getResources().getColorStateList(R.color.bottom_nav_color_apostoles));
                    navView.setItemTextColor(getResources().getColorStateList(R.color.colorApostoles));
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.chartis:
                    navView.setItemIconTintList(getResources().getColorStateList(R.color.bottom_nav_color_chartis));
                    navView.setItemTextColor(getResources().getColorStateList(R.color.colorChartis));
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.ergaleiothiki:
                    navView.setItemIconTintList(getResources().getColorStateList(R.color.bottom_nav_color_ergaliothiki));
                    navView.setItemTextColor(getResources().getColorStateList(R.color.colorErgaliothiki));
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.rithmiseis:
                    navView.setItemIconTintList(getResources().getColorStateList(R.color.bottom_nav_color_rithmisis));
                    navView.setItemTextColor(getResources().getColorStateList(R.color.colorRithmisis));
                    viewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_coord);

        progressTwo = (RoundCornerProgressBar) findViewById(R.id.progress_two);
        progressTwo.setProgressColor(getResources().getColor(R.color.color_progress));
        //        updateProgressTwoColor();

        viewPager = findViewById(R.id.viewPager);
        navView = findViewById(R.id.bottom_navigation);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.apostoles);

        //optimisation
        viewPager.setOffscreenPageLimit(4);
        viewPager.setPagingEnabled(false);

        pagerAdapter = new BottomBarAdapter(getSupportFragmentManager());
        pagerAdapter.addFragments(fragmentApostoles);
        pagerAdapter.addFragments(fragmentChartis);
        pagerAdapter.addFragments(fragmentErgaliothiki);
        pagerAdapter.addFragments(fragmentRithmisis);
        viewPager.setAdapter(pagerAdapter);

    }

    public void toast(View view) {
        Toast.makeText(getApplicationContext(),"Γειά μαστοράκο...", Toast.LENGTH_SHORT).show();
    }

    private void updateProgressTwoColor() {
        float progress = progressTwo.getProgress();
        if(progress <= 30) {
            progressTwo.setProgressColor(getResources().getColor(R.color.custom_progress_red_progress));
        } else if(progress > 30 && progress <= 60) {
            progressTwo.setProgressColor(getResources().getColor(R.color.custom_progress_orange_progress));
        } else if(progress > 60) {
            progressTwo.setProgressColor(getResources().getColor(R.color.custom_progress_green_progress));
        }
    }

    private void increaseProgressTwo() {
        progressTwo.setProgress(progressTwo.getProgress() + 1);
        updateProgressTwoColor();
    }

    public void goBack(View view) {
        finish();
    }
}
