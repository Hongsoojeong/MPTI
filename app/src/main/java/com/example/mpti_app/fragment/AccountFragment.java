package com.example.mpti_app.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.disklrucache.DiskLruCache;
import com.bumptech.glide.request.RequestOptions;
import com.example.mpti_app.LoginActivity;
import com.example.mpti_app.MainActivity;
import com.example.mpti_app.R;
import com.example.mpti_app.message.MessageActivity;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class AccountFragment extends Fragment {
    @Nullable
    private static final  int PICK_FROM_ALBUM = 10;
    private FirebaseAuth firebaseAuth;
    private ImageView profile;
    private Uri imageUri;
    UserModel userModel;

    private ValueEventListener valueEventListener;
    private DatabaseReference databaseReference;


    @Override



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        Button logout = (Button) view.findViewById(R.id.accountFragment_butto_logout);

        profile = (ImageView) view.findViewById(R.id.AccountFragment_profile_image);


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
                    Glide.with(view.getContext())
                            .load(task.getResult())
                            .override(1024, 980)
                            .into(profile);
                } else {
                    // URL을 가져오지 못하면 토스트 메세지
                }
            }
        });



        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, PICK_FROM_ALBUM);





            }
        });





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








        }
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
