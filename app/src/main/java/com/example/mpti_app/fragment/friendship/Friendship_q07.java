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

public class Friendship_q07 extends Fragment {
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friendship_q07, container, false);

        TextView option1 = (TextView) view.findViewById(R.id.q01_option7Y);
        TextView option2 = (TextView) view.findViewById(R.id.q01_option7N);

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestModel.P++;
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new Friendship_q08()).commit();
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestModel.J++;
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new Friendship_q08()).commit();
            }
        });

        return view;
    }
}