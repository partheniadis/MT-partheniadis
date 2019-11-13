package com.example.mt_v01.cardviewpager.animators;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class AlphaTransformer implements ViewPager.PageTransformer {
    private float MINALPHA = 0.5f;

    /**
     * position value characteristics:
     * Assuming the page is from 0 to 1, then:
     * The first page position changes to [0, -1]
     * The second page position changes to [1,0]
     *
     * @param page
     * @param position
     */
    @Override
    public void transformPage(View page, float position) {
        if (position < -1 || position > 1) {
            page.setAlpha(MINALPHA);
        } else {
            //opaque -> translucent
            if (position < 0) {//[0,-1]
                page.setAlpha(MINALPHA + (1 + position) * (1 - MINALPHA));
            } else {//[1,0]
                //Translucent->opaque
                page.setAlpha(MINALPHA + (1 - position) * (1 - MINALPHA));
            }
        }
    }
}
