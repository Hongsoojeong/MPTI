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

public class Friendship_q04 extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friendship_q04, container, false);

        TextView option1 = (TextView) view.findViewById(R.id.q01_option4Y);
        TextView option2 = (TextView) view.findViewById(R.id.q01_option4N);

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestModel.I++;
                Log.d("I",String.valueOf(TestModel.I));
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new Friendship_q05()).commit();
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestModel.E++;
                Log.d("E",String.valueOf(TestModel.E));
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new Friendship_q05()).commit();
            }
        });

        return view;
    }

}
