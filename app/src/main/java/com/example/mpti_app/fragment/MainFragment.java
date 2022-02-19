package com.example.mpti_app.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;


import com.example.mpti_app.R;
import com.example.mpti_app.fragment.friendship.Friendship_q01;
import com.example.mpti_app.fragment.love.love_q01;
import com.example.mpti_app.fragment.work.work_q01;
import com.example.mpti_app.test.ShopImagePagerAdapter;

import me.relex.circleindicator.CircleIndicator;


public class MainFragment extends Fragment {

    private static final String TAG = "ShopNewsActivity";
    private ViewPager pager;
    private ShopImagePagerAdapter pagerAdapter;
    private CircleIndicator indicator;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_test, container, false);

        Context context = view.getContext();

        Button friendship = (Button) view.findViewById(R.id.friendship_test_button);
        Button love = (Button) view.findViewById(R.id.love_test_button);
        Button work = (Button) view.findViewById(R.id.work_test_button);

        pager = (ViewPager) view.findViewById(R.id.pager_images);
        pagerAdapter = new ShopImagePagerAdapter(context);
        pager.setAdapter(pagerAdapter);

        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        indicator.setViewPager(pager);




        friendship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new Friendship_q01()).commit();
            }
        });

        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new love_q01()).commit();
            }
        });

        work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new work_q01()).commit();
            }
        });



        return view;
    }
}
