package com.example.mpti_app.message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mpti_app.R;
import com.example.mpti_app.SignupActivity;
import com.example.mpti_app.fragment.PeopleFragment;
import com.example.mpti_app.model.ChatModel;
import com.example.mpti_app.model.NotificationModel;
import com.example.mpti_app.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;





public class MessageActivity extends AppCompatActivity {

    private String destinationUid;
    private TextView button;
    private EditText editText;
private Button back;
    private String uid;
    private  String chatRoomUid;


    private  UserModel destinatioinuserModel;
    private RecyclerView recyclerView;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");


    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;
    int peopleCount=0;

/*    @Override
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
*/
private UserModel destinationUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid(); // 채팅을 요구하는 아이디. 즉, 단말기가 로그인된 UID
        destinationUid = getIntent().getStringExtra("destinationUid");// 채팅을 당하는 아이디
        back= (Button) findViewById(R.id.back_btn);
        button = (TextView) findViewById(R.id.messageActivity_button);;
        editText = (EditText) findViewById(R.id.messageActivity_editText);


        recyclerView = (RecyclerView) findViewById(R.id.messageActivity_recyclerview);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatModel chatModel = new ChatModel();
                chatModel.users.put(uid,true);
                chatModel.users.put(destinationUid,true);


                if (chatRoomUid==null){
                    Toast.makeText(view.getContext(), "처음 채팅 시작할 시, 전송버튼을 한번 더 눌러주세요 :)", android.widget.Toast.LENGTH_SHORT).show();
                    button.setEnabled(false);
                    FirebaseDatabase.getInstance().getReference().child("chatrooms").push().setValue(chatModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            checkChatRoom();

                        }
                    });

                 //   checkChatRoom();
                    Log.d("button.setOnClickListener","null");
                }
                else
                {
                    if (editText.getText().toString().equals("")){
                        Log.d("button.setOnClickListener","아무것도 입력되지않았습니다");
                        Toast.makeText(view.getContext(), "아무것도 입력되지 않았습니다", android.widget.Toast.LENGTH_SHORT).show();
                        return;
                    }
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.showSoftInput(editText, 0);
                    ChatModel.Comment comment = new ChatModel.Comment();
                    comment.uid = uid;
                    comment.message = editText.getText().toString();
                    comment.timestamp= ServerValue.TIMESTAMP;
                    Log.d("button.setOnClickListener","comments");
                    FirebaseDatabase.getInstance().getReference().child("chatrooms").child(chatRoomUid).child("comments").push().setValue(comment).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            sendGcm();
                            editText.setText("");
                        }
                    });
                }
            }
        });
        checkChatRoom();


    }



    void sendGcm(){
        Gson gson = new Gson();


        NotificationModel notificationModel = new NotificationModel();
        notificationModel.to = destinationUserModel.pushToken;
        notificationModel.notification.title = "새로운 메세지가 도착했습니다.";
        notificationModel.notification.text= editText.getText().toString();

        Log.d("notificationModel.notification.text",notificationModel.notification.text);


        RequestBody requestBody = RequestBody.create(gson.toJson(notificationModel), MediaType.parse("application/json; charset=utf8"));

        Request request = new Request.Builder()
                .header("Content-Type", "application/json")
                .addHeader("Authorization", "key=")
                .url("https://fcm.googleapis.com/fcm/send")
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

            }
        });

    }



    void checkChatRoom(){
        FirebaseDatabase.getInstance().getReference().child("chatrooms").orderByChild("users/"+uid).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()){
                    ChatModel chatModel = item.getValue(ChatModel.class);
                    if(chatModel.users.containsKey(destinationUid)){
                        chatRoomUid = item.getKey();
                        button.setEnabled(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
                        recyclerView.setAdapter(new RecyclerViewAdapter());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        List<ChatModel.Comment> comments;
        public RecyclerViewAdapter() {
            comments = new ArrayList<>();



            FirebaseDatabase.getInstance().getReference().child("users").child(destinationUid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    destinationUserModel = dataSnapshot.getValue(UserModel.class);
                    getMessageList();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }


        void getMessageList(){

            databaseReference= FirebaseDatabase.getInstance().getReference().child("chatrooms").child(chatRoomUid).child("comments");
            valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    comments.clear();
                    Map<String,Object> readUsersMap = new HashMap<>();
                    for (DataSnapshot item : dataSnapshot.getChildren()){
                        String key = item.getKey();
                        ChatModel.Comment comment_origin = item.getValue(ChatModel.Comment.class);
                        ChatModel.Comment comment_modify = item.getValue(ChatModel.Comment.class);

                        comment_modify.readUsers.put(uid, true);

                        readUsersMap.put(key,comment_modify);
                        comments.add(comment_origin);
                    }

                    if(comments.size() == 0){ return;}

                    if (!comments.get(comments.size() - 1).readUsers.containsKey(uid)) {
                        FirebaseDatabase.getInstance().getReference().child("chatrooms").child(chatRoomUid).child("comments")
                            .updateChildren(readUsersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    notifyDataSetChanged();
                                    recyclerView.scrollToPosition(comments.size() - 1);
                                }
                            });
                    } else {
                        notifyDataSetChanged();
                        recyclerView.scrollToPosition(comments.size() - 1);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
            return new MessageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            MessageViewHolder messageViewHolder = ((MessageViewHolder)holder);

            //내가 보낸 메세지
            if (comments.get(position).uid.equals(uid)){
                Log.d("RecyclerView.ViewHoder : onBindViewHolder","내가 보낸 메세지");
                messageViewHolder.textView_message.setText(comments.get(position).message);
                int black = ContextCompat.getColor(MessageActivity.this, R.color.black);
                messageViewHolder.textView_message.setTextColor(black);
                messageViewHolder.linearLayout_destination.setVisibility(View.INVISIBLE);
                messageViewHolder.textView_message.setBackgroundResource(R.drawable.send_background);
                messageViewHolder.linearLayout_message.setGravity(Gravity.RIGHT);
                messageViewHolder.linearLayout_main.setGravity(Gravity.RIGHT);
                setReadCounter(position, messageViewHolder.textView_readCounter_left);


            }
            //상대방이 보낸 메세지
            else{


               if (destinationUserModel.userName.equals("탈퇴한 사용자")){

                    ((MessageViewHolder) holder).imageView_profile.setImageResource(R.drawable.ic_baseline_insert_emoticon_24);
                    int grey = ContextCompat.getColor(MessageActivity.this, R.color.grey);
                   messageViewHolder.textView_name.setTextColor(grey);
                   messageViewHolder.textView_name.setText("(탈퇴한 사용자)");
                }

               else{
                Glide.with(holder.itemView.getContext())
                        .load(destinationUserModel.profileImageUrl)
                        .apply(new RequestOptions().circleCrop())
                        .into(messageViewHolder.imageView_profile);
                Log.d("RecyclerView.ViewHoder : onBindViewHolder","상대방이 보낸 메세지"+destinationUserModel.userName);

                messageViewHolder.textView_name.setText(destinationUserModel.userName);}
                messageViewHolder.textView_message.setBackgroundResource(R.drawable.simple_bubble);
                int white = ContextCompat.getColor(MessageActivity.this, R.color.white);
                messageViewHolder.textView_message.setTextColor(white);
                messageViewHolder.textView_message.setText(comments.get(position).message);
                messageViewHolder.linearLayout_destination.setVisibility(View.VISIBLE);
                messageViewHolder.linearLayout_message.setGravity(Gravity.LEFT);
                messageViewHolder.linearLayout_main.setGravity(Gravity.LEFT);
                setReadCounter(position, messageViewHolder.textView_readCounter_right);

            }
            Log.d("before unixTime", "before");
            long unixTime = (long) comments.get(position).timestamp;
            Date date = new Date(unixTime);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
            String time = simpleDateFormat.format(date);
            messageViewHolder.textView_timestamp.setText(time);

        }
        void setReadCounter(int position, TextView textView){
            if (peopleCount ==0){
                FirebaseDatabase.getInstance().getReference().child("chatrooms").child(chatRoomUid).child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String,Boolean> users = (Map<String, Boolean>) dataSnapshot.getValue();
                        peopleCount = users.size();
                        int count = peopleCount - comments.get(position).readUsers.size();
                        if (count>0){
                            textView.setText(String.valueOf(count));
                            textView.setVisibility(View.VISIBLE);
                        }else{
                            textView.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            else{
                int count = peopleCount - comments.get(position).readUsers.size();
                if (count>0){
                    textView.setText(String.valueOf(count));
                    textView.setVisibility(View.VISIBLE);
                }else{
                    textView.setVisibility(View.INVISIBLE);
                }
            }
        }

        @Override
        public int getItemCount() {
            return comments.size();
        }
        private class MessageViewHolder extends  RecyclerView.ViewHolder{

            public ImageView imageView_profile;
            public TextView textView_message;
            public TextView textView_name;
            public LinearLayout linearLayout_destination;
            public LinearLayout linearLayout_main;
            public TextView textView_timestamp;
            public LinearLayout linearLayout_message;

            public TextView textView_readCounter_left;
            public TextView textView_readCounter_right;

            public MessageViewHolder(View view) {
                super(view);
                imageView_profile = (ImageView) view.findViewById(R.id.messageItem_imageview_profile);
                textView_message = view.findViewById(R.id.messageItem_textView_message);
                textView_name=(TextView)view.findViewById(R.id.messageItem_textView_name);
                linearLayout_destination =(LinearLayout)view.findViewById(R.id.messageItem_LinearLayout_destination);
                linearLayout_message =(LinearLayout)view.findViewById(R.id.item_message_linearLayout);
                linearLayout_main = (LinearLayout)view.findViewById(R.id.messageItem_linearlayout_main);
                textView_timestamp = (TextView) view.findViewById(R.id.messageItem_textview_timestap);
                textView_readCounter_left= (TextView) view.findViewById(R.id.messageItem_textview_readCounter_left);
                textView_readCounter_right= (TextView) view.findViewById(R.id.messageItem_textview_readCounter_right);
            }
        }
    }

    @Override
    public void onBackPressed(){
       // databaseReference.removeEventListener(valueEventListener); //오류가 나서 일단은 없앴음
        finish();
    }

    void hideKeyboard()
    {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }







}
