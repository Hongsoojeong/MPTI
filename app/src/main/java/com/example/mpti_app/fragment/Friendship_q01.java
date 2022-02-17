package com.example.mpti_app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mpti_app.model.ChatModel;
import com.example.mpti_app.test.TestModel;


import androidx.annotation.Nullable;

import com.example.mpti_app.R;

public class Friendship_q01 extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friendship_q01, container, false);
        TextView option1 = (TextView) view.findViewById(R.id.q01_option1Y);
        TextView option2 = (TextView) view.findViewById(R.id.q01_option1N);

        final int[] j = {TestModel.J};
        final int[] p = {TestModel.P};

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new Friendship_q02()).commit();
                p[0]++;
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new Friendship_q02()).commit();
                j[0]++;
            }
        });

        return view;
    }
}
