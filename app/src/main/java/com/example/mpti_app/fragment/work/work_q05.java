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
import com.example.mpti_app.fragment.friendship.Friendship_q06;
import com.example.mpti_app.test.TestModel;

public class work_q05 extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work_q05, container, false);

        TextView option1 = (TextView) view.findViewById(R.id.q03_option5Y);
        TextView option2 = (TextView) view.findViewById(R.id.q03_option5N);

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestModel.N++;
                Log.d("N",String.valueOf(TestModel.N));
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new work_q06()).commit();
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestModel.S++;
                Log.d("S",String.valueOf(TestModel.S));
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new work_q06()).commit();
            }
        });

        return view;
    }
}
