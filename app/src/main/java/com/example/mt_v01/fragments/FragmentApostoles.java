package com.example.mt_v01.fragments;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mt_v01.R;

public class FragmentApostoles extends Fragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            //just change the fragment_dashboard
            //with the fragment you want to inflate
            //like if the class is HomeFragment it should have R.layout.home_fragment
            //if it is DashboardFragment it should have R.layout.fragment_dashboard
            return inflater.inflate(R.layout.fragment_apostoles, container,false );
        }

}


