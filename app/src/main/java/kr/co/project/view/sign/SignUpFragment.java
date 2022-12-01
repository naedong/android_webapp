package kr.co.project.view.sign;

import android.animation.ArgbEvaluator;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import kr.co.project.R;
import kr.co.project.adapter.BookPageAdapter;
import kr.co.project.main.BaseFragment;
import kr.co.project.vo.Maybe;

public class SignUpFragment extends BaseFragment {

    private ViewPager2 mPager;
    private int num_page = 3;
    private ProgressBar pbSign;
    private int MEDIUM_DISTANCE = 400;
    private int FAR_DISTANCE = 600;
    private int[] colors = null;
    private ArgbEvaluator argbEvaluator;
    private View dots;
    private  BookPageAdapter bookAdapter ;
    private long backBtnTime = 0;
    private FragmentTransaction fragmentTransaction;
    private ImageView  potato, onion, pickel, carrots, half_circle
            , sun, mars, venus, moon, saturn, leaf, potint_leaf, sanawbar, ears_tree
            , upper_purple_rose, purple_rose, points_leaf;
    private View night_dots;

    public static SignUpFragment newInstance(){
        SignUpFragment sf = new SignUpFragment();
        return sf;
    }

    private void initColorsList(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            colors[0] = getContext().getColor(R.color.yellow);
            colors[1] = getContext().getColor(R.color.organge);
            colors[2] = getContext().getColor(R.color.sky);
        }

    }

    private void initPager() {
        BookPageAdapter bookAdapter = new BookPageAdapter(getActivity(), num_page);
        mPager.setAdapter(bookAdapter);
        viewScroll(bookAdapter);
    }

    private void viewScroll(BookPageAdapter bookAdapter) {
        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                changePageBackgroundSwipe(position, positionOffset, positionOffsetPixels);
                showDot(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

            }
        });
    }

    private void showDot(int position, float positionOffset) {
        switch (position){
            case 0 :
                animateDots(positionOffset);
                break;

            case 1 :
                animationStart(positionOffset);
                break;
        }
    }

    private void animationStart(float positionOffset) {
        dots.animate().alpha(1 - positionOffset).setDuration(0).start();
        hideVegi(positionOffset);
        showSpaceObject(positionOffset);
    }

    private void showSpaceObject(float positionOffset) {
        mars.animate().translationX(0 - (MEDIUM_DISTANCE - (MEDIUM_DISTANCE * positionOffset)))
                .setDuration(0).start();
        moon.animate().translationX((MEDIUM_DISTANCE - (MEDIUM_DISTANCE * positionOffset)))
                .setDuration(0).start();
        saturn.animate().translationX((MEDIUM_DISTANCE - (MEDIUM_DISTANCE * positionOffset)))
                .setDuration(0).start();
        venus.animate().translationY(0 - (FAR_DISTANCE - (FAR_DISTANCE * positionOffset)))
                .setDuration(0).start();
        sun.animate().translationY(0 - (FAR_DISTANCE - (FAR_DISTANCE * positionOffset)))
                .setDuration(0).start();

    }

    private void hideVegi(float positionOffset) {
        night_dots.animate().alpha(positionOffset).setDuration(0).start();
        potato.animate().translationX(0 - ((MEDIUM_DISTANCE * positionOffset))).setDuration(0).start();
        onion.animate().translationX(((MEDIUM_DISTANCE * positionOffset))).setDuration(0).start();
        pickel.animate().translationX(((MEDIUM_DISTANCE * positionOffset))).setDuration(0).start();
        carrots.animate().translationY(0 - ((FAR_DISTANCE * positionOffset))).setDuration(0).start();
        half_circle.animate().translationY(0 - ((FAR_DISTANCE * positionOffset))).setDuration(0).start();

    }


    private void animateDots(float positionOffset) {
        hidePlantesAnimaion(positionOffset);
        dots.animate().alpha(positionOffset).setDuration(0).start();
        showObjectAnimaion(positionOffset);

    }

    private void showObjectAnimaion(float positionOffset) {
        potato.animate().translationX(0 - (MEDIUM_DISTANCE - (MEDIUM_DISTANCE * positionOffset)))
                .setDuration(0).start();
        onion.animate().translationX((MEDIUM_DISTANCE - (MEDIUM_DISTANCE * positionOffset)))
                .setDuration(0).start();
        pickel.animate().translationX((MEDIUM_DISTANCE - (MEDIUM_DISTANCE * positionOffset)))
                .setDuration(0).start();
        carrots.animate().translationY(0 - (FAR_DISTANCE - (FAR_DISTANCE * positionOffset)))
                .setDuration(0).start();
        half_circle.animate()
                .translationY(0 - (FAR_DISTANCE - (FAR_DISTANCE * positionOffset))).setDuration(0)
                .start();
    }

    private void hidePlantesAnimaion(float positionOffset) {

        leaf.animate().translationX((0 - (MEDIUM_DISTANCE * positionOffset))).setDuration(0).start();
        sanawbar.animate().translationX(((MEDIUM_DISTANCE * positionOffset))).setDuration(0).start();
        ears_tree.animate().translationX(((MEDIUM_DISTANCE * positionOffset))).setDuration(0).start();
        upper_purple_rose.animate().translationY(0 - ((FAR_DISTANCE * positionOffset))).setDuration(0).start();
        purple_rose.animate().translationY(0 - ((FAR_DISTANCE * positionOffset))).setDuration(0).start();
        points_leaf.animate().translationY(0 - ((FAR_DISTANCE * positionOffset))).setDuration(0).start();

    }

    private void changePageBackgroundSwipe(int position, float positionOffset, int positionOffsetPixels)
    {
        if(position < bookAdapter.getItemCount() - 1 && position < colors.length - 1){
            mPager.setBackgroundColor(
                    (Integer) argbEvaluator.evaluate(
                            positionOffset,
                            colors[position],
                            colors[position + 1]
                    )
            );
        }
        else{
            mPager.setBackgroundColor(colors[colors.length - 1]);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        argbEvaluator = new ArgbEvaluator();

    }

    public void initProgress(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            pbSign.setProgress(Maybe.value , false);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign, container, false);
        bookAdapter = new BookPageAdapter(getActivity(), 3);
        colors = new int[3];
        Maybe.value = 0;
        mPager = view.findViewById(R.id.pager);
        dots = view.findViewById(R.id.dots);
        sun = view.findViewById(R.id.sun);
        moon = view.findViewById(R.id.moon);
        mars = view.findViewById(R.id.mars);
        saturn = view.findViewById(R.id.saturn);
        potato = view.findViewById(R.id.potato);
        venus = view.findViewById(R.id.venus);
        night_dots = view.findViewById(R.id.night_dots);
        onion = view.findViewById(R.id.onion);
        pickel = view.findViewById(R.id.pickel);
        carrots = view.findViewById(R.id.carrots);
        half_circle = view.findViewById(R.id.right_half_circle);
        leaf = view.findViewById(R.id.leaf);
        potint_leaf = view.findViewById(R.id.points_leaf);
        pbSign = view.findViewById(R.id.pb_sign);
        sanawbar = view.findViewById(R.id.sanawbar);
        ears_tree = view.findViewById(R.id.ears_tree);
        upper_purple_rose = view.findViewById(R.id.upper_purple_rose);
        purple_rose = view.findViewById(R.id.purple_rose);
        points_leaf = view.findViewById(R.id.points_leaf);
        initColorsList();
        initPager();
        initProgress();
       // initProgress();

//        mPager.setCurrentItem(3);
//        mPager.setClipToPadding(true);
//
//        mPager.setOrientation(ViewPager2
//                .ORIENTATION_HORIZONTAL);
//
//
//
//        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
//                if(positionOffsetPixels == 0 ){
//                    mPager.setCurrentItem(position);
//                }
//
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//            }
//        });



        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.i("[signup]", "스타트확인"+Maybe.value);

        initProgress();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("[signup]", "실행확인"+Maybe.value);
        
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("[signup]", "퍼지 확인");
        initProgress();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("[signup]", "죽음 확인");

    }
}
