package com.example.mpti_app.fragment.friendship;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.example.mpti_app.LoginActivity;
import com.example.mpti_app.R;
import com.example.mpti_app.fragment.AccountFragment;
import com.example.mpti_app.fragment.ChatFragment;
import com.example.mpti_app.fragment.MainFragment;
import com.example.mpti_app.fragment.PeopleFragment;
import com.example.mpti_app.test.TestModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Friendship_Result extends Fragment {
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friendship_result, container, false);
        TextView textView = (TextView) view.findViewById(R.id.friendship_result);
        TextView couple = (TextView) view.findViewById(R.id.mbti_couple);
        Button button = (Button)view.findViewById(R.id.result_ok);
        Button save = (Button) view.findViewById(R.id.result_save);
        TextView friendship_explain = (TextView) view.findViewById(R.id.friendship_explain);

        String result_mpti;

        if (TestModel.E > TestModel.I){
            result_mpti="E";
        }
        else {
            result_mpti="I";
        }

        if (TestModel.N > TestModel.S){
            result_mpti+="N";
        }
        else {
            result_mpti+="S";
        }

        if (TestModel.F > TestModel.T){
            result_mpti+="F";
        }
        else {
            result_mpti+="T";
        }

        if (TestModel.P > TestModel.J){
            result_mpti+="P";
        }
        else {
            result_mpti+="J";
        }

        TestModel.E=0;
        TestModel.I=0;
        TestModel.N=0;
        TestModel.S=0;
        TestModel.T=0;
        TestModel.F=0;
        TestModel.P=0;
        TestModel.J=0;

        textView.setText(result_mpti);
        Log.d("result_mpti :",result_mpti);

        if (result_mpti.equals("INFP"))
        {
            friendship_explain.setText("아무리 긴 고민이라도 친구의 이야기라면 모두 들어주는 인내왕 당신,\n정작 당신의 고민은 어떻게 해결하고 있나요?");
            couple.setText("ENFJ & ENTJ");}
        if (result_mpti.equals("ENFP"))
        {
            friendship_explain.setText("흥이 넘쳐흐르는 상큼발랄한 텐션을 가진 당신,\n오늘은 이번 달 몇 번째 약속인가요?");
            couple.setText("INFJ & INTJ");}

        if (result_mpti.equals("INFJ"))
        {   friendship_explain.setText("친구들의 미세한 표정도 모두 잡아내는 배려와 세심의 아이콘 당신,\n차가운 것 같아 보여도 차분하게 고민을 해결해주는 해결사군요:) ");
            couple.setText("ENFP & ENTP");}

        if (result_mpti.equals("ENFJ"))
        {   friendship_explain.setText("모든 사람들과 친구가 될 준비가 돼있는 댕댕이 같은 당신,\n친구만큼 자신도 돌보고 있나요? ");
            couple.setText("INFP & ISFP");}

        if (result_mpti.equals("INTJ"))
        {   friendship_explain.setText("친구들과의 대화에서도 지정능력을 확장시키려는 당신,\n당신과의 대화에 지쳐가는 친구들은 없나요?");
            couple.setText("ENFP & ISFP");}

        if (result_mpti.equals("ENTJ"))
        {   friendship_explain.setText("늘 자신감 있고 시원시원한 결단력을 가진 리더유형의 당신,\n솔직한 피드백 뒤에 따뜻한 한마디도 덧붙여보는 건 어때요?");
            couple.setText("INFP & INTP");}

        if (result_mpti.equals("INTP"))
        {   friendship_explain.setText("조용하고 멍한 표정 뒤 수만가지 생각을 가진 당신,\n대화가 맞는 친구들과 만나면 1박2일도 대화가능해요:)");
            couple.setText("ENTJ & ESTJ");}

        if (result_mpti.equals("ENTP"))
        {   friendship_explain.setText("늘 새로운 소재로 대화를 이끌어가는 MC 재질의 당신,\n가끔 이야기가 삼천포로 빠지지는 않나요?");
            couple.setText("INFJ & INTJ");}

        if (result_mpti.equals("ISFP"))
        {   friendship_explain.setText("자신의 영역이 분명한 당신,\n약속 잡기는 귀찮지만 막상 나가면 끝까지 가는군요:)");
            couple.setText("ENFJ & ESFJ & ESTJ");}

        if (result_mpti.equals("ESFP"))
        {   friendship_explain.setText("옷깃만 스쳐도 인연으로 만드는 사교계 핵인싸 당신,\n너무 심한 장난은 위험하답니다:)");
            couple.setText("ISFJ & ISTJ");}

        if (result_mpti.equals("ISTP"))
        {   friendship_explain.setText("어떤 유형의 친구라도 편견없이 받아들여주는 당신,\n때론 무미건조해 보이지만 당신에게 위안을 얻는 친구도 많을거예요:)");
            couple.setText("ESFJ & ESTJ");}

        if (result_mpti.equals("ESTP"))
        {   friendship_explain.setText("지루한 일은 NO! 늘 재미를 찾아 유랑하는 당신,\n재미를 위해 충동적인 실수를 하고 있지는 않나요?");
            couple.setText("ISFJ");}

        if (result_mpti.equals("ISFJ"))
        {   friendship_explain.setText("인싸중에 아싸! 아싸중에 인싸! 야누스 같은 당신,\n한번에 한 친구와 만나는 시간이 더 즐겁죠?");
            couple.setText("ESFP & ESTP");}

        if (result_mpti.equals("ESFJ"))
        {   friendship_explain.setText("타고난 말빨로 사소한 썰도 재밌게 풀어내는 MC 재질의 당신,\n친구들의 반응에 너무 상처받고 있지는 않나요?");
            couple.setText("ISFP & ISTP");}

        if (result_mpti.equals("ISTJ"))
        {   friendship_explain.setText("친구무리를 적당한 온도로 융합해주는 우직한 매력의 당신,\n주위에 당신의 바른생활을 본받고 싶어하는 친구가 많군요:)");
            couple.setText("ESFP");}

        if (result_mpti.equals("ESTJ"))
        {   friendship_explain.setText("뛰어난 상황판단과 조직력으로 훌륭한 상담가 역할을 하는 당신,\n솔직한 피드백 뒤에 따뜻한 한마디도 덧붙여보는 건 어때요?");
            couple.setText("INTP & ISFP & ISTP");}


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new MainFragment()).commit();
            }
        });


        String finalResult_mpti = result_mpti;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> stringObjectMap = new HashMap<>();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                stringObjectMap.put("friendship",finalResult_mpti);
                Toast.makeText(view.getContext(), "저장이 완료되었습니다. \n프로필을 통해 확인해보세요 :)", Toast.LENGTH_SHORT).show();
                FirebaseDatabase.getInstance().getReference("users").child(uid).updateChildren(stringObjectMap);
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new AccountFragment()).commit();

            }
        });

        return view;
    }
}