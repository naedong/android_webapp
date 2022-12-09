package kr.co.project.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import kr.co.project.view.pay.MoneyChageFragment;
import kr.co.project.view.sign.SignUpFragment;
import kr.co.project.view.sign.signup.SignUpFragment1;
import kr.co.project.view.sign.signup.SignUpFragment2;
import kr.co.project.view.sign.signup.SignUpFragment3;

public class BookPageAdapter extends FragmentStateAdapter {

    private int mCount;
    private List<SignUpFragment> fragments;
    private Fragment fragment;

    public BookPageAdapter(FragmentActivity fa, int count){
        super(fa);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
//        int index = getRealPosition(position);

        if(position == 1) {
            return  new SignUpFragment1();

        }
        else if (position == 2){
            return  new SignUpFragment2();
        }
        else if (position == 0 ){
            return  new SignUpFragment3();
        }
        else if (position ==4){
            return new MoneyChageFragment();
        }
    return null;
//        if(index== 2 ) return new EndFragment();
//        else if(index==1) return new MypageFragment();
//        else
//            return  new MyFragment();
    }


//    public int getRealPosition(int position) { return position % mCount; }

    @Override
    public int getItemCount() {
        return mCount;
    }
}
