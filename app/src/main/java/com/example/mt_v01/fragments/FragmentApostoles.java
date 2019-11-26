package com.example.mt_v01.fragments;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mt_v01.R;
import com.example.mt_v01.helperclasses.CustomConstraintLayout;
import com.otaliastudios.zoom.ZoomLayout;

public class FragmentApostoles extends Fragment {

    private View view;
    private Context ctx;
    private ConstraintLayout apostolesLayout;
    private CustomConstraintLayout apostoli1,apostoli2,apostoli3,apostoli4,apostoli5;
    private ZoomLayout zoomLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        view = inflater.inflate(R.layout.fragment_apostoles, container,false );
        ctx = view.getContext();
        zoomLayout = view.findViewById(R.id.zoom_layout);

        final CustomConstraintLayout apostoli1 = view.findViewById(R.id.apostoli1);
        final CustomConstraintLayout apostoli2 = view.findViewById(R.id.apostoli2);
        final CustomConstraintLayout apostoli3 = view.findViewById(R.id.apostoli3);
        final CustomConstraintLayout apostoli4 = view.findViewById(R.id.apostoli4);
        final CustomConstraintLayout apostoli5 = view.findViewById(R.id.apostoli5);

        setUpApostoli(apostoli1,
                "ΠΕΡΙΔΙΑΒΑΙΝΟΝΤΑΣ \nΤΗΝ ΜΑΡΜΑΡΟΥΠΟΛΗ",
                "1",
                "11",
                "110",
                "350");
        setUpApostoli(apostoli2,
                "ΠΕΡΙΔΙΑΒΑΙΝΟΝΤΑΣ \nΤΗΝ ΜΑΡΜΑΡΟΥΠΟΛΗ",
                "1",
                "11",
                "110",
                "350");
        setUpApostoli(apostoli3,
                "ΠΕΡΙΔΙΑΒΑΙΝΟΝΤΑΣ \nΤΗΝ ΜΑΡΜΑΡΟΥΠΟΛΗ",
                "1",
                "11",
                "110",
                "350");
        setUpApostoli(apostoli4,
                "ΠΕΡΙΔΙΑΒΑΙΝΟΝΤΑΣ \nΤΗΝ ΜΑΡΜΑΡΟΥΠΟΛΗ",
                "1",
                "11",
                "110",
                "350");
        setUpApostoli(apostoli5,
                "ΠΕΡΙΔΙΑΒΑΙΝΟΝΤΑΣ \nΤΗΝ ΜΑΡΜΑΡΟΥΠΟΛΗ",
                "1",
                "11",
                "110",
                "350");

        return view;
    }

    private void setUpApostoli(final CustomConstraintLayout apostoli, String missionTitle, String challengedCompletedAtMission, String totalChallengesAtMission, String experienceCollectedAtMission, String totalExperienceNeededAtMission) {

//        apostoli.setBackgroundPolygonTint(R.color.colorAccent);
        apostoli.setMissionText(missionTitle);
        apostoli.setChallengesText(challengedCompletedAtMission+"/"+totalChallengesAtMission);
        apostoli.setExperienceText(experienceCollectedAtMission+"/"+totalExperienceNeededAtMission+"XP");
        apostoli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] viewCoords = new int[2];
                apostoli.getPolygonImageView().getLocationOnScreen(viewCoords);
                Toast.makeText(ctx,"Clicked on a mission!",Toast.LENGTH_SHORT).show();
                zoomLayout.moveTo( 2.5f, viewCoords[0], viewCoords[1],true);
            }
        });

    }

}


