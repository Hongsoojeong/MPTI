package com.example.mpti_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ReportFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;


import com.example.mpti_app.fragment.AccountFragment;
import com.example.mpti_app.fragment.ChatFragment;
import com.example.mpti_app.fragment.MainFragment;
import com.example.mpti_app.fragment.PeopleFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.mainactivity_bottomnavigationview);
        getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new MainFragment()).commit(); //첫화면을 친구목록에 뜨도록



        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch (item.getItemId()){
                   case R.id.action_people:
                       getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new PeopleFragment()).commit();
                       return true;
                   case R.id.action_chat:
                       getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new ChatFragment()).commit();
                       return true;
                   case R.id.action_account:
                       getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new AccountFragment()).commit();
                       return true;
                   case R.id.action_test:
                       getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new MainFragment()).commit();
                       return true;

               }
                return false;
            }

        });
        passPushTokenToServer();
    }

    void passPushTokenToServer(){
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        Map<String,Object> map = new HashMap<>();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.d("PassPushToken onComplete :","false");
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        map.put("pushToken",token);
                        FirebaseDatabase.getInstance().getReference().child("users").child(uid).updateChildren(map);

                        // Log and toast
                    }
                });


    }

    }



