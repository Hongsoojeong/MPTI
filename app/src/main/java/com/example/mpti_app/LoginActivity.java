package com.example.mpti_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Button login;
    private Button signup;
    private Button changePw;
    private FirebaseRemoteConfig firebaseRemoteConfig;
    private EditText id;
    private EditText password;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    ProgressBar progress;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseRemoteConfig=firebaseRemoteConfig.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();//     firebaseAuth.signOut();

        id = (EditText) findViewById(R.id.loginActivity_edittext_id);
        password = (EditText) findViewById(R.id.loginActivity_edittext_password);


        progress = (ProgressBar) findViewById(R.id.login_progressBar);



        id.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

         }

         @Override
         public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
             if (id.getText().toString().contains("\n")) {
                 id.setNextFocusDownId(R.id.loginActivity_edittext_password);
             }
         }

         @Override
         public void afterTextChanged(Editable editable) {
             if (id.getText().toString().contains("\n")) {
                 id.setNextFocusDownId(R.id.loginActivity_edittext_password);
             }
         }
     });

        login = (Button)findViewById(R.id.loginActivity_button_login);
        signup = (Button)findViewById(R.id.loginActivity_button_signup);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //연결이 되지않아 강제로 넘기기





                if(id.getText().length() == 0 || password.getText().length() == 0){
                    Toast.makeText(LoginActivity.this, "아무것도 입력되지 않았습니다", Toast.LENGTH_SHORT).show();
                    Log.d("onClick :","아무것도 입력되지않았습니다");
                    return;
                } else {
                    Log.d("onClick :","loginEvent");
                    loginEvent();

                }



            }
        });




     /*   changePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                LayoutInflater layoutInflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view= layoutInflater.inflate(R.layout.dialog_comment,null);
                EditText editText = view.findViewById(R.id.commentDialog_edittext);
                editText.setHint("비밀번호를 재설정 해주세요");

                builder.setView(view).setPositiveButton("변경", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setChangePw(editText.getText().toString());
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();


            }
        });
*/





        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });

    }
    void loginEvent(){
        progress.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(id.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progress.setVisibility(View.VISIBLE);
                if(!task.isSuccessful()){ // 로그인 실패한 부분
                    progress.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LoginActivity.this, "로그인완료", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        //로그인 인터페이스 리스너
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    //로그인
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{

                    //로그아웃
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("onStart","사용전");




        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

Log.d("firebaseAuth.getCurrentUser()", String.valueOf(firebaseAuth.getCurrentUser()));
        if(currentUser != null){ // 자동로그인

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

       //     currentUser.reload(); //자동로그인 없애는는
                }


    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    /*void setChangePw(String pw){

        Log.d("setChange pw : ",pw);
        FirebaseAuth.getInstance().getCurrentUser().updatePassword(pw).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "비밀번호가 변경되었습니다.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
*/


}