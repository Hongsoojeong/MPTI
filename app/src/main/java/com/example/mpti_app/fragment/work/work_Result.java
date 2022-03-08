package com.example.mpti_app.fragment.work;

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

public class work_Result extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work_result, container, false);
        TextView textView = (TextView) view.findViewById(R.id.friendship_result);
        TextView couple = (TextView) view.findViewById(R.id.mbti_couple);
        TextView work_explain = (TextView) view.findViewById(R.id.work_explain);
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
            work_explain.setText("자기만의 내적 신념이 확고한 당신,\n뚜렷한 신념으로 이상적인 분위기를 조성하곤 한답니다:)");
            couple.setText("ENFJ & ENTJ");}
        if (result_mpti.equals("ENFP"))
        {
            work_explain.setText("상상력이 풍부한 당신,\n창의적인 활동을 추구하면서도 순발력이 뛰어난 장점이 있답니다:)");
            couple.setText("INFJ & INTJ");}

        if (result_mpti.equals("INFJ"))
        {   work_explain.setText("사람에 관한 뛰어난 통찰력을 가진 당신,\n공동체의 이익을 중요시하는 회사생활에 꼭 필요한 인재상이랍니다:)");
            couple.setText("ENFP & ENTP");}

        if (result_mpti.equals("ENFJ"))
        {   work_explain.setText("타인의 의견을 존중할 줄 아는 당신,\n동료들의 성장을 돕는데 적극적이랍니다:)");
            couple.setText("INFP & ISFP");}

        if (result_mpti.equals("INTJ"))
        {   work_explain.setText("독립적이고 의지가 강한 분석가 당신,\n상황을 거시적인 관점에서 보고 비전을 제시하곤 한답니다:)");
            couple.setText("ENFP & ISFP");}

        if (result_mpti.equals("ENTJ"))
        {   work_explain.setText("뛰어난 업무능력을 가진 사기캐 당신,\n시원시원한 결단력으로 동료들을 이끄는 리더쉽을 발휘한답니다:)");
            couple.setText("INFP & INTP");}

        if (result_mpti.equals("INTP"))
        {   work_explain.setText("지적 호기심이 높은 탐구가 당신,\n동료나 프로젝트의 잠재력과 가능성을 파악해내 보석을 발견하곤 한답니다:)");
            couple.setText("ENTJ & ESTJ");}

        if (result_mpti.equals("ENTP"))
        {   work_explain.setText("자신의 일을 사랑하는 덕업일치의 주인공 당신,\n뛰어난 상상력과 도전욕구가 합쳐져 새로운 결과물로 주변을 놀라게 하곤 한답니다:)");
            couple.setText("INFJ & INTJ");}

        if (result_mpti.equals("ISFP"))
        {   work_explain.setText("내 생활이 가장 중요한 워라밸 지킴이 당신,\n겸손한 매력으로 동료들과의 유연한 관계를 유지한답니다:)");
            couple.setText("ENFJ & ESFJ & ESTJ");}

        if (result_mpti.equals("ESFP"))
        {   work_explain.setText("호기심이 많은 당신,\n업무에 있어서도 자유로운 분위기에서 일하는 것을 좋아한답니다:)");
            couple.setText("ISFJ & ISTJ");}

        if (result_mpti.equals("ISTP"))
        {   work_explain.setText("여러 분위기에서 뛰어난 적응력을 보이는 당신,\n업무상황에서 생기는 여러 돌발상황들도 잘 해결한답니다:)");
            couple.setText("ESFJ & ESTJ");}

        if (result_mpti.equals("ESTP"))
        {   work_explain.setText("타협에 능속한 당신,\n현실적인 문제 해결을 잘해낸답니다:)");
            couple.setText("ISFJ");}

        if (result_mpti.equals("ISFJ"))
        {   work_explain.setText("성실하고 온화한 당신,\n동료들에게 협조를 잘하며 함께 일하는 동료들을 편안하게 해준답니다:)");
            couple.setText("ESFP & ESTP");}

        if (result_mpti.equals("ESFJ"))
        {   work_explain.setText("사람에 대한 많은 관심을 가진 친선도모형 당신,\n동료에게 친절하다는 평가를 받곤 한답니다:)");
            couple.setText("ISFP & ISTP");}

        if (result_mpti.equals("ISTJ"))
        {   work_explain.setText("책임감이 강한 당신,\n일에 있어 철저해 실수를 잘 하지 않는답니다:)");
            couple.setText("ESFP");}

        if (result_mpti.equals("ESTJ"))
        {   work_explain.setText("규칙을 준수하며 일하는 현실적인 당신,\n사실적 목표를 설정하고 그를 체계적으로 따르는 것에 능숙하답니다:)");
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
                stringObjectMap.put("work",finalResult_mpti);
                FirebaseDatabase.getInstance().getReference("users").child(uid).updateChildren(stringObjectMap);
                getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new AccountFragment()).commit();

            }
        });

        return view;
    }


}
