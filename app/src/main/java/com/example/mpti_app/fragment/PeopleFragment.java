package com.example.mpti_app.fragment;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mpti_app.R;
import com.example.mpti_app.message.MessageActivity;
import com.example.mpti_app.model.UserModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.Inflater;



public class PeopleFragment extends Fragment  {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {




        View view = inflater.inflate(R.layout.fragment_people, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.peoplefragment_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        recyclerView.setAdapter(new PeopleFragmentRecyclerViewAdapter());








    Dialog revoke_dialog;

    revoke_dialog = new Dialog(view.getContext());       // Dialog ?????????
    revoke_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // ????????? ??????
    revoke_dialog.setContentView(R.layout.dialog_revoke);             // xml ???????????? ????????? ??????

    revoke_dialog.show(); // ??????????????? ?????????
    revoke_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


    TextView text = (TextView) revoke_dialog.findViewById(R.id.dialog_text);
    Button yes = (Button) revoke_dialog.findViewById(R.id.yesBtn);
    Button no = (Button) revoke_dialog.findViewById(R.id.noBtn);

    text.setText("MPTI??? ?????? ?????? ???????????????. \n???????????? MPTI??? ?????? ?????? MPTI ???????????????\n???????????? ??????????????????!\n\n??? 1. ?????? ??? ??????????????? ??? ??????\n   ?????? ????????? ?????? ??? ????????????\n   2. ?????? ????????? ????????? ???????????? ??????, \n    ??????????????? ?????? ??????????????????.");
    yes.setText("??????");
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
            revoke_dialog.dismiss();
        }
    });



        return view;


    }


    class PeopleFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        List<UserModel> userModels;


        public PeopleFragmentRecyclerViewAdapter() {





            userModels = new ArrayList<>();
            String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {



                    userModels.clear();

                    for (DataSnapshot snapshot: datasnapshot.getChildren()){
                        UserModel userModel = snapshot.getValue(UserModel.class);
                        if (userModel.uid.equals("")){
                            continue;
                        }
                        if (userModel.uid.equals(myUid)){
                            continue;
                        }
                        else{

                        }
                        if (userModel.userName.equals("????????? ?????????")){

                        }
                        else{
                        userModels.add(userModel);
                        }
                    }

                    notifyDataSetChanged();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend,parent,false);
            return new CustomViewHolder(view);
        }





        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder,  final int position) {

            Glide.with
                    (holder.itemView.getContext())
                    .load(userModels.get(position).profileImageUrl)
                    .apply(new RequestOptions().circleCrop())
                    .into(((CustomViewHolder)holder).imageView);



            if (userModels.get(position).userName.equals("????????? ?????????")){
                ((CustomViewHolder)holder).imageView.setImageResource(R.drawable.ic_baseline_insert_emoticon_24);
            }

            else {
                ((CustomViewHolder) holder).textView.setText(userModels.get(position).userName);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), MessageActivity.class);
                    intent.putExtra("destinationUid", userModels.get(position).uid);
                    startActivity(intent);
                }
            });

            if (userModels.get(position).comment!=null)
            ((CustomViewHolder)holder).textView_comment.setText(userModels.get(position).comment);


            if (holder.itemView.getContext() != null) {
                PeopleFragmentRecyclerViewAdapter adapter = new PeopleFragmentRecyclerViewAdapter();
                //       if (((CustomViewHolder) holder).search.equals(null)) {
                Log.d("customViewHolder","null??? ???????");

            }


        }

        @Override
        public int getItemCount() {
                return userModels.size();
        }


        private class CustomViewHolder extends RecyclerView.ViewHolder{
           public TextView textView;
           public ImageView imageView;
           public TextView textView_comment;

            public CustomViewHolder(@NonNull View view) {
                super(view);
                imageView = view.findViewById(R.id.frienditem_imageview);
                textView = (TextView) view.findViewById(R.id.frienditem_textview);
                textView_comment = (TextView) view.findViewById(R.id.frienditem_textview_comment);

            }
        }

    }
}
