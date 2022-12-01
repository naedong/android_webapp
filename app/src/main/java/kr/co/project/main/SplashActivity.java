package kr.co.project.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.wajahatkarim3.easyflipviewpager.CardFlipPageTransformer;

import java.util.ArrayList;

import kr.co.project.R;

public class SplashActivity extends AppCompatActivity {

    ViewPager pokerViewPager;
    private PokerPagerAdapter pagerAdapter;
    private int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        pokerViewPager = findViewById(R.id.pokerViewPager);
        pagerAdapter = new PokerPagerAdapter(this);
        pokerViewPager.setAdapter(pagerAdapter);

        CardFlipPageTransformer cardFlipPageTransformer = new CardFlipPageTransformer();
        cardFlipPageTransformer.setScalable(false);
        cardFlipPageTransformer.setFlipOrientation(CardFlipPageTransformer.VERTICAL);
        pokerViewPager.setPageTransformer(true, cardFlipPageTransformer);

    }

    public class PokerPagerAdapter extends PagerAdapter
    {
        Context context;
        LayoutInflater mLayoutInflater;
        ArrayList pages = new ArrayList<>();

        public PokerPagerAdapter(Context context) {
            this.context = context;
            mLayoutInflater = LayoutInflater.from(context);

            pages.add(new Object());
            pages.add(new Object());
        }

        @Override
        public int getCount() {
            return pages.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        // This method should create the page for the given position passed to it as an argument.
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            View rootView = mLayoutInflater.inflate(R.layout.card_image_layout, container, false);
            AppCompatImageView imgCardSide = rootView.findViewById(R.id.imgCardSide);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(position == 0){
                        pokerViewPager.setCurrentItem(1, true);
                    }
                    else{

                    }

                }
            }, 3000);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(position == 1 && count < 2){
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{

                    }

                }
            }, 6000);


            imgCardSide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position == 0)
                    {
                        count = 1;
                        pokerViewPager.setCurrentItem(1, true);
                    }
                    else if(position == 1)
                    {
                        count = 2;
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
            int[] sides = {R.drawable.splashfront, R.drawable.splashback};
            imgCardSide.setImageResource(sides[position]);
            container.addView(rootView);
            return rootView;

        }

        // Removes the page from the container for the given position.
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}