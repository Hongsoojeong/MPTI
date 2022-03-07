package com.example.mpti_app.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;

import com.example.mpti_app.LoginActivity;
import com.example.mpti_app.R;
import com.example.mpti_app.fragment.MainFragment;
import com.example.mpti_app.fragment.friendship.Friendship_q01;
import com.example.mpti_app.fragment.love.love_q01;
import com.example.mpti_app.fragment.work.work_q01;

public class ShopImagePagerAdapter extends PagerAdapter {

    private static final String TAG = "ShopNewsPagerAdapter";
    private int[] images = {R.drawable.friendship_test,R.drawable.love_test,R.drawable.work_test};
    private Context context;

    public ShopImagePagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (View)object;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pager_shop_news_image, container , false);

        ImageView imageView = view.findViewById(R.id.img_shop_news);
        imageView.setImageResource(images[position]);
        container.addView(view);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position==0){

                }
                else if (position==1){

                }
                else{

                }
            }
        });

        return view;
    }
}