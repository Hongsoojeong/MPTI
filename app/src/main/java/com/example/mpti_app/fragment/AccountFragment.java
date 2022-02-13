package com.example.mpti_app.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.mpti_app.LoginActivity;
import com.example.mpti_app.MainActivity;
import com.example.mpti_app.R;
import com.example.mpti_app.message.MessageActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AccountFragment extends Fragment {
    @Nullable
    private FirebaseAuth firebaseAuth;
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_account, container,false);

       Button logout=(Button) view.findViewById(R.id.accountFragment_butto_logout);



       logout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(view.getContext(), LoginActivity.class);
               startActivity(intent);
               firebaseAuth = FirebaseAuth.getInstance();
               firebaseAuth.signOut();
           }
       });



       Button button = (Button) view.findViewById(R.id.accountFragment_butto_comment);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               showDialog(view.getContext());




           }
       });
        return view;


    }




    void showDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_comment,null);
        EditText editText = view.findViewById(R.id.commentDialog_edittext);





        builder.setView(view).setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Map<String,Object> stringObjectMap = new HashMap<>();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                stringObjectMap.put("comment",editText.getText().toString());
                FirebaseDatabase.getInstance().getReference("users").child(uid).updateChildren(stringObjectMap);
            }
        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();

    }
}