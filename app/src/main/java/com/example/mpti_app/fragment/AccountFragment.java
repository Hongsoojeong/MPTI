package com.example.mpti_app.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.disklrucache.DiskLruCache;
import com.bumptech.glide.request.RequestOptions;
import com.example.mpti_app.LoginActivity;
import com.example.mpti_app.MainActivity;
import com.example.mpti_app.R;
import com.example.mpti_app.SignupActivity;
import com.example.mpti_app.fragment.friendship.Friendship_q01;
import com.example.mpti_app.message.MessageActivity;
import com.example.mpti_app.model.ChatModel;
import com.example.mpti_app.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {
    @Nullable
    private static final  int PICK_FROM_ALBUM = 10;
    private Button revoke;
    private Switch aSwitch;
    private Button editMbti;
    private Button editProfile;
    private FirebaseAuth firebaseAuth;
    private CircleImageView profile;
    private Uri imageUri;
    private TextView friendship;
    private TextView love;
    private TextView work;
    private int change=0;
    UserModel userModel;
    private TextView mpti;
    private  TextView stateMessage;
    private ValueEventListener valueEventListener;
    private DatabaseReference databaseReference;

    @Override



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_account, container, false);

        revoke = (Button) view.findViewById(R.id.revoke);
        Button logout = (Button) view.findViewById(R.id.accountFragment_butto_logout);
        editMbti = (Button) view.findViewById(R.id.accountFragment_button_mpti);
        profile = (CircleImageView) view.findViewById(R.id.AccountFragment_profile_image);
        stateMessage = (TextView) view.findViewById(R.id.state_message);
        mpti = (TextView) view.findViewById(R.id.Fragment_account_profile_mpti);
        friendship = (TextView) view.findViewById(R.id.friendship_result);
        love = (TextView) view.findViewById(R.id.love_result);
        work = (TextView) view.findViewById(R.id.workship_result);
   //     editProfile = (Button) view.findViewById(R.id.accountFragment_butto_changeImage);
       //  StorageReference storageRef = storage.getReference("image.jpg");
        // 스토리지 공간을 참조해서 이미지를 가져옴

       //  Glide.with(view).load(storageRef).into(image);
       //  Glide를 사용하여 이미지 로드


        FirebaseStorage storage = FirebaseStorage.getInstance();// FirebaseStorage 인스턴스 생성
        String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();


          StorageReference storageRef = storage.getReferenceFromUrl("gs://mpti-app.appspot.com/userImages/" + myUid);
          storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
              @Override
              public void onComplete(@NonNull Task<Uri> task) {
                  if (task.isSuccessful()) {
                      // Glide 이용하여 이미지뷰에 로딩
                      Log.d("onCOmplete databaseReference(userModel.profileImageUrl) :",userModel.profileImageUrl);
                      Glide.with(view.getContext())
                              .load(userModel.profileImageUrl)
                              .override(1024, 980)
                              .apply(new RequestOptions().circleCrop())
                              .into(profile);
                  } else {
                      // URL을 가져오지 못하면 토스트 메세지
                  }
              }
          });




        FirebaseDatabase.getInstance().getReference().child("users").child(myUid).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userModel = dataSnapshot.getValue(UserModel.class);

                Log.d("userModel.comment : ", String.valueOf(userModel.comment));
                if (userModel.comment.equals("") ){
                    int grey = ContextCompat.getColor(view.getContext(), R.color.grey);
                    stateMessage.setTextColor(grey);
                    stateMessage.setText("상태 메세지를 설정해보세요! :)");
                }
                else {
                    stateMessage.setText(userModel.comment);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        FirebaseDatabase.getInstance().getReference().child("users").child(myUid).child("comment").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });





//        editProfile.setOnClickListener(new View.OnClickListener() {
  //          @Override
  //          public void onClick(View view) {
  //              Intent intent = new Intent(Intent.ACTION_PICK);
  //              intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
   //             startActivityForResult(intent, PICK_FROM_ALBUM);
//
    //        }

    //    });
        FirebaseDatabase.getInstance().getReference().child("users").child(myUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userModel = snapshot.getValue(UserModel.class);
                mpti.setText(userModel.userName);
                if (userModel.friendship.equals("")){}
                else
                {friendship.setText(userModel.friendship);}

                if (userModel.work.equals("")){}
                else
                {work.setText(userModel.work);}

                if (userModel.love.equals("")){}
                else
                {love.setText(userModel.love);}

    }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editMbti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogSingleChoice(view.getContext());
            }
        });



       logout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(view.getContext(), LoginActivity.class);
               startActivity(intent);
               Map<String,Object> stringObjectMap = new HashMap<>();
               stringObjectMap.put("pushToken",""); //로그아웃 시 메세지를 받지못하게 하려고
               FirebaseDatabase.getInstance().getReference("users").child(myUid).updateChildren(stringObjectMap);
               firebaseAuth = FirebaseAuth.getInstance();
               firebaseAuth.signOut();

           }
       });


       revoke.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View view) {

               Dialog revoke_dialog;

               revoke_dialog = new Dialog(view.getContext());       // Dialog 초기화
               revoke_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
               revoke_dialog.setContentView(R.layout.dialog_revoke);             // xml 레이아웃 파일과 연결

               revoke_dialog.show(); // 다이얼로그 띄우기
               revoke_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


               Button yes = (Button) revoke_dialog.findViewById(R.id.yesBtn);
               Button no = (Button) revoke_dialog.findViewById(R.id.noBtn);

               yes.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                       Log.d("yes.setOnClickListener : ",String.valueOf(FirebaseAuth.getInstance().getCurrentUser()));
                       FirebaseAuth.getInstance().getCurrentUser().delete();

                       Map<String,Object> stringObjectMap = new HashMap<>();
                       stringObjectMap.put("userName","탈퇴한 사용자");
                       FirebaseDatabase.getInstance().getReference("users").child(myUid).updateChildren(stringObjectMap);

                       Log.d("yes.setOnClickListener(before): ",String.valueOf(FirebaseAuth.getInstance().getCurrentUser()));



                   }

               });



               no.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       revoke_dialog.dismiss();
                   }
               });


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

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_FROM_ALBUM && resultCode == Activity.RESULT_OK) {
            profile.setImageURI(data.getData()); // 가운데 뷰를 바꿈
            imageUri = data.getData();// 이미지 경로 원본
            String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            databaseReference= FirebaseDatabase.getInstance().getReference().child("users").child(myUid).child("profileImageUrl");
         //   FirebaseDatabase.getInstance().getReference().child("users").child(myUid).child("profileImageUrl").setValue(imageUri);
            Log.d("databaseReference :",String.valueOf(imageUri));
            Log.d("databaseReference :",String.valueOf(databaseReference));
            userModel.profileImageUrl = String.valueOf(imageUri);
            Log.d("onActivity databaseReference(userModel.profileImageUrl) :",userModel.profileImageUrl);
        }
    }

    void showDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_comment,null);
        EditText editText = view.findViewById(R.id.commentDialog_edittext);

        editText.setHint("상태메세지를 설정해주세요:)");

        builder.setView(view).setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Map<String,Object> stringObjectMap = new HashMap<>();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                stringObjectMap.put("comment",editText.getText().toString());
                FirebaseDatabase.getInstance().getReference("users").child(uid).updateChildren(stringObjectMap);
                Toast.makeText(view.getContext(), "상태메세지가 변경되었습니다.\n 새로고침하여 확인해보세요! :)", Toast.LENGTH_SHORT).show();
                stateMessage.setText(userModel.comment);

            }
        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();

    }

    void DialogSingleChoice(Context context){

        final CharSequence[] oItems = {"ISFP","ISTP","ISFJ","ISTJ","INFP","INTP","INFJ","INTJ","ENFP","ENTP","ENFJ","ENTJ","ESFP","ESTP","ESFJ","ESTJ"};
        AlertDialog.Builder oDialog = new AlertDialog.Builder(context,android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);

        oDialog.setTitle("MBTI를 선택해주세요 :)")
                .setItems(oItems, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(context, "MPTI가 변경되었습니다.\n 새로고침하여 확인해보세요! :)", Toast.LENGTH_SHORT).show();
                        Map<String,Object> stringObjectMap = new HashMap<>();
                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        stringObjectMap.put("userName",oItems[which]);
                        FirebaseDatabase.getInstance().getReference("users").child(uid).updateChildren(stringObjectMap);
                        mpti.setText(oItems[which]);
                    }
                })
                .setCancelable(false)
                .show();
    }
}


