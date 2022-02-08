package com.example.mpti_app;

import androidx.activity.result.ActivityResultRegistry;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mpti_app.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class SignupActivity extends AppCompatActivity {

    private static final  int PICK_FROM_ALBUM = 10;
    private EditText email;
    private EditText name;
    private EditText password;
    private Button signup;

    private ImageView profile;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        String splash_background = mFirebaseRemoteConfig.getString("splash_background");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(Color.parseColor(splash_background));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        profile = (ImageView)findViewById(R.id.sinupActivity_imageview_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            }
        });

        email = (EditText)findViewById(R.id.signupActivity_email);
        name = (EditText)findViewById(R.id.signupActivity_name);
        password = (EditText)findViewById(R.id.signupActivity_password);
        signup = (Button) findViewById(R.id.signupActivity_button_signup);

        signup.setBackgroundColor(Color.parseColor(splash_background));

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString()==null || name.getText().toString()==null || password.getText().toString()==null){
                    return;
                }
                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                UserModel userModel = new UserModel();
                                userModel.userName = name.getText().toString();
                                String uid = task.getResult().getUser().getUid();
                                FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel);
                            }
                        });
            }
        });


    }
}
