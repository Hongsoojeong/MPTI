package com.example.mpti_app.fragment.friendship;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mpti_app.LoginActivity;
import com.example.mpti_app.test.TestModel;


import androidx.annotation.Nullable;

import com.example.mpti_app.R;

public class Friendship_q01 extends Fragment {



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_friendship_q01, container, false);
        TextView option1 = (TextView) view.findViewById(R.id.q01_option1Y);
        TextView option2 = (TextView) view.findViewById(R.id.q01_option1N);

        TestModel.E=0;
        TestModel.I=0;
        TestModel.N=0;
        TestModel.S=0;
        TestModel.T=0;
        TestModel.F=0;
        TestModel.P=0;
        TestModel.J=0;


        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestModel.J++;
                Log.d("J",String.valueOf(TestModel.J));

                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new Friendship_q02()).commit();

            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestModel.P++;
                Log.d("P",String.valueOf(TestModel.P));
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new Friendship_q02()).commit();
            }
        });

        return view;
    }
}
