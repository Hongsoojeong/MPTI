package com.example.mpti_app.fragment.friendship;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.mpti_app.R;
import com.example.mpti_app.test.TestModel;

public class Friendship_q03 extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friendship_q03, container, false);

        TextView option1 = (TextView) view.findViewById(R.id.q01_option3Y);
        TextView option2 = (TextView) view.findViewById(R.id.q01_option3N);

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestModel.T++;
                Log.d("T",String.valueOf(TestModel.T));
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new Friendship_q04()).commit();
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestModel.F++;
                Log.d("F",String.valueOf(TestModel.F));
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new Friendship_q04()).commit();
            }
        });

        return view;
    }
}
