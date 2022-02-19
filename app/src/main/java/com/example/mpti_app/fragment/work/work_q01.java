package com.example.mpti_app.fragment.work;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.mpti_app.R;
import com.example.mpti_app.fragment.friendship.Friendship_q02;
import com.example.mpti_app.test.TestModel;

public class work_q01 extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_work_q01, container, false);
        TextView option1 = (TextView) view.findViewById(R.id.q03_option1Y);
        TextView option2 = (TextView) view.findViewById(R.id.q03_option1N);

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
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new work_q02()).commit();
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestModel.P++;
                Log.d("P",String.valueOf(TestModel.P));
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new work_q02()).commit();
            }
        });

        return view;
    }

}
