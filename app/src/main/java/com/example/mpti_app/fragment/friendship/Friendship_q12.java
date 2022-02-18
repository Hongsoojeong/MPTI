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

public class Friendship_q12 extends Fragment {
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friendship_q12, container, false);

        TextView option1 = (TextView) view.findViewById(R.id.q01_option12Y);
        TextView option2 = (TextView) view.findViewById(R.id.q01_option12N);

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestModel.I++;
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new Result()).commit();
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestModel.E++;
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new Result()).commit();
            }
        });

        return view;
    }
}