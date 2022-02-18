package com.example.mpti_app.fragment.friendship;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.mpti_app.R;
import com.example.mpti_app.test.TestModel;

public class Friendship_q10 extends Fragment {
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friendship_q10, container, false);

        TextView option1 = (TextView) view.findViewById(R.id.q01_option10Y);
        TextView option2 = (TextView) view.findViewById(R.id.q01_option10N);

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestModel.N++;
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new Friendship_q11()).commit();
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestModel.S++;
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new Friendship_q11()).commit();
            }
        });

        return view;
    }
}