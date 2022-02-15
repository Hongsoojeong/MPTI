package com.example.mpti_app.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mpti_app.LoginActivity;
import com.example.mpti_app.R;
import com.google.firebase.auth.FirebaseAuth;


public class MainFragment extends Fragment {

    private String param1 = null;
    private String param2 = null;



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_test, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageButton);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new Friendship_q01()).commit();
            }
        });
        return view;
    }
}
