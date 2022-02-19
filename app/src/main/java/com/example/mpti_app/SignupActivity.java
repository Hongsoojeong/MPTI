package com.example.mpti_app;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Instrumentation;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mpti_app.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        profile = (ImageView) findViewById(R.id.sinupActivity_imageview_profile);


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                launcher.launch(intent);

            }
        });





        email = (EditText) findViewById(R.id.signupActivity_email);
        name = (EditText) findViewById(R.id.signupActivity_name);
        password = (EditText) findViewById(R.id.signupActivity_password);
        signup = (Button) findViewById(R.id.signupActivity_button_signup);

        ProgressBar progress = (ProgressBar) findViewById(R.id.progress1);
        TextInputLayout textinputlayout = (TextInputLayout) findViewById(R.id.mbti);

        final boolean[] email_fill = {false};

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                email_fill[0] =true;
            }
        });



        final boolean[] name_fill = {false};

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DialogSingleChoice();
            }

        });



        final boolean[] password_fill = {false};

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (password.getText().length()>=6){
                password_fill[0] =true;}

                if (email_fill[0]==true || email_fill[0]==true || password_fill[0]==true){
                    signup.setVisibility(View.VISIBLE);
                }
            }
        });







            signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (email.getText().length() == 0 || name.getText().length() == 0 || password.getText().length() == 0){
                    Toast.makeText(SignupActivity.this, "아무것도 입력되지 않았습니다", Toast.LENGTH_SHORT).show();
                    return;
                }

                progress.setVisibility(View.VISIBLE); //프로그래스 바 보이도록
                Log.d("onClick : ", String.valueOf(email.getText()));
                Log.d("onClick : ",String.valueOf(name.getText()));
                Log.d("onClick : ",String.valueOf(password.getText()));

                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                final String uid = task.getResult().getUser().getUid();
                                Log.d("imageUri :", String.valueOf(imageUri));
                                FirebaseStorage.getInstance().getReference().child("userImages").child(uid).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                        @SuppressWarnings("VisibleForTests")
                                       // String imageUrl = task.getResult().getStorage().getDownloadUrl().toString();


                                        Task<Uri> uriTask = task.getResult().getStorage().getDownloadUrl();
                                        while(!uriTask.isSuccessful());
                                        Uri downloadUrl = uriTask.getResult();
                                        String imageUrl = String.valueOf(downloadUrl);



                                        UserModel userModel = new UserModel();
                                        userModel.userName = name.getText().toString();
                                        userModel.profileImageUrl = imageUrl;
                                        userModel.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                        FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                SignupActivity.this.finish();
                                            }
                                        });

                                    }
                                });
                            }
                        });

            }

        });


    }


    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult> ()
            {
                @Override
                public void onActivityResult(ActivityResult data)
                {
                    Log.d("TAG", "data : " + data);
                    Log.d("TAG", "intent : "+data.getData());
                    if (data.getResultCode() == RESULT_OK )
                    {
                        Intent intent = data.getData();
                        profile.setImageURI(intent.getData()); // 가운데 뷰를 바꿈
                        imageUri = intent.getData();// 이미지 경로 원본
                        Log.d("TAG suuccess", "intent : "+data.getData());

                    }
                }
            });


     void DialogSingleChoice(){

         final CharSequence[] oItems = {"ISFP","ISTP","ISFJ","ISTJ","INFP","INTP","INFJ","INTJ","ENFP","ENTP","ENFJ","ENTJ","ESFP","ESTP","ESFJ","ESTJ"};

         AlertDialog.Builder oDialog = new AlertDialog.Builder(this,
                 android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);

         oDialog.setTitle("MBTI를 선택해주세요 :)")
                 .setItems(oItems, new DialogInterface.OnClickListener()
                 {
                     @Override
                     public void onClick(DialogInterface dialog, int which)
                     {
                         name.setText(oItems[which]);
                         Toast.makeText(getApplicationContext(),
                                 oItems[which], Toast.LENGTH_LONG).show();
                     }
                 })
                 .setCancelable(false)
                 .show();

    }


        }


