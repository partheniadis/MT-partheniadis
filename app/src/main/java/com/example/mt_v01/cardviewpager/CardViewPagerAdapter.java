package com.example.mt_v01.cardviewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.example.mt_v01.R;
import com.example.mt_v01.cardviewpager.animators.AlphaTransformer;
import com.example.mt_v01.helperclasses.Challenge;
import com.example.mt_v01.helperclasses.CustomTextView;

import java.util.List;

public class CardViewPagerAdapter extends PagerAdapter {
    private List<Challenge> list;
    private Context context;
    private LayoutInflater inflater;
    private CustomTextView typos_apostolis;
    private CustomTextView challenge_area;
    private CustomTextView experience_points;
    private CustomTextView question;
    private ImageView image;


    public CardViewPagerAdapter(Context context, List<Challenge> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.card_challenge_map, container, false);
        image = view.findViewById(R.id.card_image);
        typos_apostolis = view.findViewById(R.id.card_typos_apostolis);
        challenge_area = view.findViewById(R.id.text_area_of_challenge);
        experience_points = view.findViewById(R.id.card_experience_points);
        question = view.findViewById(R.id.card_question);

        image.setImageResource(list.get(position).getImage());
        question.setText(list.get(position).getQuestion());
        typos_apostolis.setText(list.get(position).getMission());
        challenge_area.setText(list.get(position).getArea());
        experience_points.setText(String.valueOf(list.get(position).getExperience_points()));

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


}
