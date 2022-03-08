package com.example.mpti_app.fragment.love;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.mpti_app.R;
import com.example.mpti_app.fragment.AccountFragment;
import com.example.mpti_app.fragment.MainFragment;
import com.example.mpti_app.test.TestModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class love_Result extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_love_result, container, false);
        TextView textView = (TextView) view.findViewById(R.id.friendship_result);
        TextView couple = (TextView) view.findViewById(R.id.mbti_couple);
        TextView love_explain = (TextView) view.findViewById(R.id.love_explain);
        Button button = (Button)view.findViewById(R.id.result_ok);
        Button save =(Button) view.findViewById(R.id.result_save);

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
            love_explain.setText("첫만남에 결혼까지 꿈꾸는 금사빠 당신,\n속도를 한 템포만 늦추어보아요:)");
            couple.setText("ENFJ & ENTJ");}
        if (result_mpti.equals("ENFP"))
        {
            love_explain.setText("좋아하는 사람 앞에서는 웃음보따리가 되는 당신,\n해바라기 같은 매력의 소유자이시군요:)");
            couple.setText("INFJ & INTJ");}

        if (result_mpti.equals("INFJ"))
        {   love_explain.setText("스스로의 생각에 잠 못 이루는 당신,\n생각보다 연애는 간단할 수도 있어요:)");
            couple.setText("ENFP & ENTP");}

        if (result_mpti.equals("ENFJ"))
        {   love_explain.setText("무엇이든 완벽해야 직성이 풀리는 당신,\n부족한 부분을 서로의 존재로 채워가는 충만한 연애는 어떨까요?");
            couple.setText("INFP & ISFP");}

        if (result_mpti.equals("INTJ"))
        {   love_explain.setText("스윗하면서도 스토커 같은 달콤살벌한 당신,\n지금 상대방은 당신을 어떻게 보고 있을까요?");
            couple.setText("ENFP & ISFP");}

        if (result_mpti.equals("ENTJ"))
        {   love_explain.setText("마성의 츤데레 매력으로 상대방을 헷갈리게 하는 당신,\n가끔은 자신의 마음을 솔직하게 표현해보는 건 어떨까요?");
            couple.setText("INFP & INTP");}

        if (result_mpti.equals("INTP"))
        {   love_explain.setText("좋아하는 사람에게는 뭐든 OK 열정남녀 당신,\n매력은 적당한 밀당에서 나오는 법이랍니다:)");
            couple.setText("ENTJ & ESTJ");}

        if (result_mpti.equals("ENTP"))
        {   love_explain.setText("자존심이 엄청나게 강한 당신,\n혹시 지금도 누군가 당신의 연락을 기다리고 있는 건 아닌가요?");
            couple.setText("INFJ & INTJ");}

        if (result_mpti.equals("ISFP"))
        {   love_explain.setText("엄청난 열정으로 무엇이든 퍼주는 당신,\n자신의 몫도 챙겨가며 연애하고 있는 거죠?");
            couple.setText("ENFJ & ESFJ & ESTJ");}

        if (result_mpti.equals("ESFP"))
        {   love_explain.setText("한 없이 적극적인 연애불도저 당신,\n상대방과 나란히 속도를 맞추는 연애를 하고 있나요?");
            couple.setText("ISFJ & ISTJ");}

        if (result_mpti.equals("ISTP"))
        {   love_explain.setText("지금 느끼는 감정을 있는 그대로 말하는 노필터 당신,\n매력은 적당한 밀당에서 나오는 법이랍니다:)");
            couple.setText("ESFJ & ESTJ");}

        if (result_mpti.equals("ESTP"))
        {   love_explain.setText("풋풋함이 어색한 연애로봇 당신,\n서로의 애칭을 짓는 것부터 차근차근 시작해보아요:)");
            couple.setText("ISFJ");}

        if (result_mpti.equals("ISFJ"))
        {   love_explain.setText("부끄럼이 많은 짝사랑의 아이콘인 당신,\n지금 보고 있는 그 사람도 같은 마음일 수도 있답니다:)");
            couple.setText("ESFP & ESTP");}

        if (result_mpti.equals("ESFJ"))
        {   love_explain.setText("좋아하는 사람 앞에서는 능력자! 귀여운 허세남녀 당신,\n가끔은 상대방에 도움을 요청해보는 건 어떨까요?");
            couple.setText("ISFP & ISTP");}

        if (result_mpti.equals("ISTJ"))
        {   love_explain.setText("자꾸 쳐다보다 아이컨택하길 수만번,\n이미 상대방은 당신의 마음을 눈치채지 않았을까요?");
            couple.setText("ESFP");}

        if (result_mpti.equals("ESTJ"))
        {   love_explain.setText("상대방에게 귀 기울이는 굿리스너 당신,\n유머감각은 챙기고 있나요?");
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
                Toast.makeText(view.getContext(), "저장이 완료되었습니다. \n프로필을 통해 확인해보세요 :)", Toast.LENGTH_SHORT).show();
                stringObjectMap.put("love",finalResult_mpti);
                FirebaseDatabase.getInstance().getReference("users").child(uid).updateChildren(stringObjectMap);
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new AccountFragment()).commit();

            }
        });

        return view;
    }


}
