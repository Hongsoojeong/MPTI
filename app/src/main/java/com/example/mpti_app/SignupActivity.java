package com.example.mpti_app;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Instrumentation;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    private boolean image=false;
    private static final  int PICK_FROM_ALBUM = 10;
    private EditText email;
    private EditText name;
    private EditText password;

    private Button signup;

    private ImageView profile;
    private Uri imageUri=null;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        profile = (ImageView) findViewById(R.id.sinupActivity_imageview_profile);



        Dialog revoke_dialog;

        revoke_dialog = new Dialog(this);       // Dialog 초기화
        revoke_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        revoke_dialog.setContentView(R.layout.dialog_revoke);             // xml 레이아웃 파일과 연결

        revoke_dialog.show(); // 다이얼로그 띄우기
        revoke_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView text = (TextView) revoke_dialog.findViewById(R.id.dialog_text);
        Button yes = (Button) revoke_dialog.findViewById(R.id.yesBtn);
        Button no = (Button) revoke_dialog.findViewById(R.id.noBtn);

        text.setText("1. 프로필 사진을 설정하셔야 가입이 가능합니다\n※ 프로필사진은 한번밖에 설정이 안되니,\n신중하게 선택해주세요 :)\n\n2. 비밀번호는 6자 이상 설정해주세요");
        yes.setText("확인");
        no.setText("");


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                revoke_dialog.dismiss();

            }

        });



        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });








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

                if (email_fill[0]==true&&name.getText().toString().length()>0 && password_fill[0]==true){
                    Log.d("after imageUri", String.valueOf(imageUri));
                    signup.setVisibility(View.VISIBLE);
                }
                else{
                    Log.d("after imageUri", String.valueOf(imageUri));
                    signup.setVisibility(View.INVISIBLE);
                }
            }
        });








            signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Pattern p = Pattern.compile("^[a-zA-Z0-9]+@[a-zA-Z0-9]+$");
                Matcher m = p.matcher((email).getText().toString());

                if(imageUri==null) {
                    Toast.makeText(SignupActivity.this, "이미지를 선택해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isEmail(email.getText().toString())==false){
                    Toast.makeText(SignupActivity.this, "이메일 형식으로 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().length()<6){
                    Toast.makeText(SignupActivity.this, "6자 이상으로 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

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

                                if (!task.isSuccessful()) {
                                    try {
                                        task.getResult();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Log.d("Fail_register_email", e.getMessage());
                                        Toast.makeText(SignupActivity.this, "이미있는 이메일 형식입니다 다시 입력해주세요", Toast.LENGTH_LONG).show();
                                        finish();//인텐트 종료
                                        overridePendingTransition(0, 0);//인텐트 효과 없애기
                                        Intent intent = getIntent(); //인텐트
                                        startActivity(intent); //액티비티 열기
                                        overridePendingTransition(0, 0);//인텐트 효과 없애기
                                    }
                                    progress.setVisibility(View.INVISIBLE);
                                    return;
                                }


                                progress.setVisibility(View.VISIBLE);
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
                        Log.d("imageUri", String.valueOf(imageUri));

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

    public static boolean isEmail(String email){
        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches())
            return true;
        return false; }



        }


