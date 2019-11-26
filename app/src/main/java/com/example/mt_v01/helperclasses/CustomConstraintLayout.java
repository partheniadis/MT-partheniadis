package com.example.mt_v01.helperclasses;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.StyleableRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.mt_v01.R;

public class CustomConstraintLayout extends ConstraintLayout {

    @StyleableRes
    int index0 = 0;
    @StyleableRes
    int index1 = 1;
    @StyleableRes
    int index2 = 2;

    CustomTextViewBold missionTitle;
    TextViewTinosRegular challengesCompletedText;
    TextViewTinosRegular experienceCollectedText;
    ImageView backgroundPolygon;


    public CustomConstraintLayout(Context context) {
        super(context);
    }

    public CustomConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.apostoli_polygon_item, this);
        int[] sets = {R.attr.challengeTitle, R.attr.challengesCompletedText, R.attr.experienceCollectedText, R.attr.backgroundColorPolygon, R.attr.backgroundColorPolygon};

        TypedArray typedArray = context.obtainStyledAttributes(attrs, sets);
        CharSequence title = typedArray.getText(index0);
        CharSequence challengesCompleted = typedArray.getText(index1);
        CharSequence experienceCollected = typedArray.getText(index2);
        typedArray.recycle();

        initComponents();

        setMissionText(title);
        setChallengesText(challengesCompleted);
        setExperienceText(experienceCollected);

    }

    private void initComponents() {
        missionTitle = (CustomTextViewBold) findViewById(R.id.the_mission_title);
        challengesCompletedText = (TextViewTinosRegular) findViewById(R.id.progress_epipedo5);
        experienceCollectedText = (TextViewTinosRegular) findViewById(R.id.progress_epipedo6);
        backgroundPolygon = findViewById(R.id.apostoli_polygon);


    }

    //TODO:add logic to display out of total challenges.
    public void setExperienceText(CharSequence experienceCollected) {
        experienceCollectedText.setText(experienceCollected);
    }

    public void setChallengesText(CharSequence challengesCompleted) {
        challengesCompletedText.setText(challengesCompleted);
    }

    public void setMissionText(CharSequence title) {
        missionTitle.setText(title);
    }

    public CharSequence getExperienceCollectedText(){
        return experienceCollectedText.getText();
    }

    public CharSequence getChallengesCompletedText(){
        return challengesCompletedText.getText();
    }

    public CharSequence getMissionText(){
        return missionTitle.getText();
    }

    public Integer getBackgroundPolygonTint(Integer backgroundPolygon){
        return backgroundPolygon;
    }

    public void setBackgroundPolygonTint(Integer newBackgroundPolygon){
        backgroundPolygon.setBackgroundTintList(ColorStateList.valueOf(newBackgroundPolygon));
    }

    public ImageView getPolygonImageView(){
        return backgroundPolygon;
    }

    public Drawable getBackgroundPolygonDrawable(){
        return backgroundPolygon.getDrawable();
    }

    public void setBackgroundPolygonDrawable(Drawable drawable) {
//        drawable = DrawableCompat.wrap(drawable);
//        DrawableCompat.setTint(drawable, Color.RED);
//        // ...or a tint list
//        DrawableCompat.setTintList(drawable, );
//        // ...and a different tint mode
//        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_OVER);

        backgroundPolygon.setImageDrawable(drawable);
    }
}
