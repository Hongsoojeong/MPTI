package com.example.mpti_app.fragment.work;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.mpti_app.R;
import com.example.mpti_app.fragment.MainFragment;
import com.example.mpti_app.test.TestModel;

public class work_Result extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work_result, container, false);
        TextView textView = (TextView) view.findViewById(R.id.friendship_result);
        TextView couple = (TextView) view.findViewById(R.id.mbti_couple);
        Button button = (Button)view.findViewById(R.id.result_ok);

        String result_mpti;

        if (TestModel.E > TestModel.I){
            result_mpti="E";
        }
        else {
            result_mpti="I";
        }

        if (TestModel.N > TestModel.S){
            result_mpti+="N";
        }
        else {
            result_mpti+="S";
        }

        if (TestModel.F > TestModel.T){
            result_mpti+="F";
        }
        else {
            result_mpti+="T";
        }

        if (TestModel.P > TestModel.J){
            result_mpti+="P";
        }
        else {
            result_mpti+="J";
        }

        TestModel.E=0;
        TestModel.I=0;
        TestModel.N=0;
        TestModel.S=0;
        TestModel.T=0;
        TestModel.F=0;
        TestModel.P=0;
        TestModel.J=0;

        textView.setText(result_mpti);
        Log.d("result_mpti :",result_mpti);

        if (result_mpti.equals("INFP"))
            couple.setText("ENFJ & ENTJ");
        if (result_mpti.equals("ENFP"))
            couple.setText("INFJ & INTJ");
        if (result_mpti.equals("INFJ"))
            couple.setText("ENFP & ENTP");
        if (result_mpti.equals("ENFJ"))
            couple.setText("INFP & ISFP");
        if (result_mpti.equals("INTJ"))
            couple.setText("ENFP & ISFP");
        if (result_mpti.equals("ENTJ"))
            couple.setText("INFP & INTP");
        if (result_mpti.equals("INTP"))
            couple.setText("ENTJ & ESTJ");
        if (result_mpti.equals("ENTP"))
            couple.setText("INFJ & INTJ");
        if (result_mpti.equals("ISFP"))
            couple.setText("ENFJ & ESFJ & ESTJ");
        if (result_mpti.equals("ESFP"))
            couple.setText("ISFJ & ISTJ");
        if (result_mpti.equals("ISTP"))
            couple.setText("ESFJ & ESTJ");
        if (result_mpti.equals("ESTP"))
            couple.setText("ISFJ");
        if (result_mpti.equals("ISFJ"))
            couple.setText("ESFP & ESTP");
        if (result_mpti.equals("ESFJ"))
            couple.setText("ISFP & ISTP");
        if (result_mpti.equals("ISTJ"))
            couple.setText("ESFP");
        if (result_mpti.equals("ESTJ"))
            couple.setText("INTP & ISFP & ISTP");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new MainFragment()).commit();
            }
        });



        return view;
    }


}
