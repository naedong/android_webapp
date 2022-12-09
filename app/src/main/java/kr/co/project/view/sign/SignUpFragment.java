package kr.co.project.view.sign;


import android.animation.ArgbEvaluator;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.gson.Gson;
import com.skydoves.progressview.TextForm;

import kr.co.project.R;
import kr.co.project.adapter.BookPageAdapter;
import kr.co.project.api.RetrofitAPI;
import kr.co.project.config.RetrofitConfig;
import kr.co.project.databinding.FragmentFirstBinding;
import kr.co.project.databinding.FragmentSecondBinding;
import kr.co.project.databinding.FragmentSignBinding;
import kr.co.project.databinding.FragmentThirdBinding;
import kr.co.project.main.BaseFragment;
import kr.co.project.main.MainActivity;
import kr.co.project.view.sign.signup.address.AddressFragment;
import kr.co.project.vo.Maybe;
import kr.co.project.vo.MembSign;
import kr.co.project.vo.PublicData;
import kr.co.project.vo.SignDTO;
import kr.co.project.vo.SignLiveModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {
    private static String TAG = SignUpFragment.class.getSimpleName();

    private int num_page = 2;
    private MembSign membSign;
    private int MEDIUM_DISTANCE = 400;
    private int FAR_DISTANCE = 600;
    private int[] colors = null;
    private ArgbEvaluator argbEvaluator;
    private Maybe maybe;
    private  BookPageAdapter bookAdapter ;
    private long backBtnTime = 0;
    private RetrofitConfig retrofitConfig;
    private RetrofitAPI retrofitAPI;
    private SignLiveModel signLiveModel;
    private FragmentSignBinding binding;
    private Toast tast;
    private SignUpFragment sf;



    private void initColorsList(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            colors[0] = getContext().getColor(R.color.yellow);
            colors[1] = getContext().getColor(R.color.organge);
            colors[2] = getContext().getColor(R.color.sky);
        }

    }

    private void initPager() {
        BookPageAdapter bookAdapter = new BookPageAdapter(getActivity(), num_page);
        binding.pager.setAdapter(bookAdapter);
        viewScroll(bookAdapter);
    }

    private void viewScroll(BookPageAdapter bookAdapter) {
        binding.pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
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
        binding.dots.animate().alpha(1 - positionOffset).setDuration(0).start();
        hideVegi(positionOffset);
        showSpaceObject(positionOffset);
    }

    private void showSpaceObject(float positionOffset) {
        binding.mars.animate().translationX(0 - (MEDIUM_DISTANCE - (MEDIUM_DISTANCE * positionOffset)))
                .setDuration(0).start();
        binding.moon.animate().translationX((MEDIUM_DISTANCE - (MEDIUM_DISTANCE * positionOffset)))
                .setDuration(0).start();
        binding.saturn.animate().translationX((MEDIUM_DISTANCE - (MEDIUM_DISTANCE * positionOffset)))
                .setDuration(0).start();
        binding.venus.animate().translationY(0 - (FAR_DISTANCE - (FAR_DISTANCE * positionOffset)))
                .setDuration(0).start();
        binding.sun.animate().translationY(0 - (FAR_DISTANCE - (FAR_DISTANCE * positionOffset)))
                .setDuration(0).start();

    }

    private void hideVegi(float positionOffset) {
        binding.nightDots.animate().alpha(positionOffset).setDuration(0).start();
        binding.potato.animate().translationX(0 - ((MEDIUM_DISTANCE * positionOffset))).setDuration(0).start();
        binding.onion.animate().translationX(((MEDIUM_DISTANCE * positionOffset))).setDuration(0).start();
        binding.pickel.animate().translationX(((MEDIUM_DISTANCE * positionOffset))).setDuration(0).start();
        binding.carrots.animate().translationY(0 - ((FAR_DISTANCE * positionOffset))).setDuration(0).start();
        binding.rightHalfCircle.animate().translationY(0 - ((FAR_DISTANCE * positionOffset))).setDuration(0).start();

    }


    private void animateDots(float positionOffset) {
        hidePlantesAnimaion(positionOffset);
        binding.dots.animate().alpha(positionOffset).setDuration(0).start();
        showObjectAnimaion(positionOffset);

    }

    private void showObjectAnimaion(float positionOffset) {
        binding.potato.animate().translationX(0 - (MEDIUM_DISTANCE - (MEDIUM_DISTANCE * positionOffset)))
                .setDuration(0).start();
        binding.onion.animate().translationX((MEDIUM_DISTANCE - (MEDIUM_DISTANCE * positionOffset)))
                .setDuration(0).start();
        binding.pickel.animate().translationX((MEDIUM_DISTANCE - (MEDIUM_DISTANCE * positionOffset)))
                .setDuration(0).start();
        binding.carrots.animate().translationY(0 - (FAR_DISTANCE - (FAR_DISTANCE * positionOffset)))
                .setDuration(0).start();
        binding.rightHalfCircle.animate()
                .translationY(0 - (FAR_DISTANCE - (FAR_DISTANCE * positionOffset))).setDuration(0)
                .start();
    }
    public static SignUpFragment newInstance(){
        SignUpFragment sf = new SignUpFragment();
        return sf;
    }
    private void hidePlantesAnimaion(float positionOffset) {

        binding.leaf.animate().translationX((0 - (MEDIUM_DISTANCE * positionOffset))).setDuration(0).start();
        binding.sanawbar.animate().translationX(((MEDIUM_DISTANCE * positionOffset))).setDuration(0).start();
        binding.earsTree.animate().translationX(((MEDIUM_DISTANCE * positionOffset))).setDuration(0).start();
        binding.upperPurpleRose.animate().translationY(0 - ((FAR_DISTANCE * positionOffset))).setDuration(0).start();
        binding.purpleRose.animate().translationY(0 - ((FAR_DISTANCE * positionOffset))).setDuration(0).start();
        binding.pointsLeaf.animate().translationY(0 - ((FAR_DISTANCE * positionOffset))).setDuration(0).start();

    }

    private void changePageBackgroundSwipe(int position, float positionOffset, int positionOffsetPixels)
    {
        if(position < bookAdapter.getItemCount() - 1 && position < colors.length - 1){
            binding.pager.setBackgroundColor(
                    (Integer) argbEvaluator.evaluate(
                            positionOffset,
                            colors[position],
                            colors[position + 1]
                    )
            );
        }
        else{
            binding.pager.setBackgroundColor(colors[colors.length - 1]);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        membSign = new MembSign();
        argbEvaluator = new ArgbEvaluator();
        retrofitConfig = RetrofitConfig.getInstance();
        retrofitAPI = retrofitConfig.getRetrofit();
        signLiveModel = new ViewModelProvider(requireActivity()).get(SignLiveModel.class);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
    }

    public void initProgress(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.pbSign.setProgress(maybe.value.getValue().floatValue());
            if(maybe.value.getValue().intValue() == 6){
                binding.pbSign.setLabelText("회원가입 버튼 클릭");
            }
            else binding.pbSign.setLabelText(maybe.value.getValue().intValue() + "/ 6");

        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


//        TextForm textForm = new TextForm.Builder(getContext())
//                .setTextSize(20f)
//                .setTextColorResource(R.color.white)
//                .setTextTypeface(R.font.calibri)
//                .build();

        binding = FragmentSignBinding.inflate(inflater, container, false);
        maybe = new ViewModelProvider(requireActivity()).get(Maybe.class);
//        binding.pbSign.setLabelSize(20f);
//        binding.pbSign.setLabelTypeface(R.font.calibri);

//        binding.pbSign.applyTextForm(textForm);

        bookAdapter = new BookPageAdapter(getActivity(), 3);
         colors = new int[3];
        initColorsList();
        initPager();
        onRegist();

        onBack();

        maybe.value.observe(getViewLifecycleOwner(), integer ->  {
            initProgress();
        });
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


        onClickSignUp();
        return binding.getRoot();
    }

    private void onClickSignUp() {

        if(maybe.value.getValue().intValue() >= 6){

            membSign.setMembId(signLiveModel.getUserId().getValue());
            membSign.setMembPwd(signLiveModel.getUserPwd().getValue());
            membSign.setMobileNo(signLiveModel.getMobile().getValue());
            membSign.setMembStatusCd(10);
            membSign.setMembCls("사용자");

            membSign.setMembNm(signLiveModel.getUserName().getValue());
            PublicData publicData = new PublicData();
            publicData.setZipCd(signLiveModel.getZip().getValue());
            publicData.setZipAddr(signLiveModel.getZipaddr().getValue());
            publicData.setDetailAddr(signLiveModel.getDetaild().getValue());
            Log.i(TAG, "onClickSignUp: detail"+signLiveModel.getDetaild().getValue());
            publicData.setEmailAddr(signLiveModel.getEmail().getValue());
            membSign.setPublicData(publicData);
        Log.i(TAG, membSign.getMembId());
            Log.i(TAG, membSign.getMembPwd());
            Log.i(TAG, membSign.getMobileNo());

            binding.btnSignStart.setOnClickListener(v ->{
            retrofitAPI.insertUser(membSign).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {


                    LayoutInflater inflater1 = getLayoutInflater();
                    View layout = inflater1.inflate(R.layout.toast_board, (ViewGroup) v.findViewById(R.id.toast_layout));
                    TextView textView = layout.findViewById(R.id.tx_toast_board);
                    textView.setText("회원가입에 성공하였습니다.");

                            if (response.isSuccessful()){
                                if(response.body()){

                                    tast = Toast.makeText(getContext().getApplicationContext(), "회원가입에 성공하였습니다.", Toast.LENGTH_LONG);
                                    tast.setGravity(Gravity.CENTER,0,0);
                                    tast.setView(layout);
                                    tast.show();


                                    FragmentManager fm = getActivity().getSupportFragmentManager();
                                    FragmentTransaction ft = fm.beginTransaction();
                                    LoginFragments lf = null;
                                    ft.replace(R.id.main_frame , lf.newInstance());
                                    ft.addToBackStack(null);
                                    ft.commit();
                                }
                                else {
                                    tast = Toast.makeText(getContext().getApplicationContext(), "회원가입에 살패하였습니다.", Toast.LENGTH_LONG);
                                    tast.setGravity(Gravity.CENTER,0,0);
                                    tast.setView(layout);
                                    tast.show();


                                }
                            }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    LayoutInflater inflater1 = getLayoutInflater();
                    View layout = inflater1.inflate(R.layout.toast_board, (ViewGroup) v.findViewById(R.id.toast_layout));
                    TextView textView = layout.findViewById(R.id.tx_toast_board);
                    textView.setText("회원가입에 성공하였습니다.");

                    tast = Toast.makeText(getContext().getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG);
                    tast.setGravity(Gravity.CENTER,0,0);
                    tast.setView(layout);
                    tast.show();

                }
            });
        });
        }
    }

    private void onRegist() {


    }

    public void onBack(){
        LoginFragments lf = null;
            binding.btnSignBack.setOnClickListener(v -> {

                getActivity().getSupportFragmentManager().beginTransaction().remove(this)
                        .replace(R.id.main_frame, lf.newInstance()).commit();
                getActivity().getSupportFragmentManager().popBackStack();
            });
        }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("[signup]", "스타트확인");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("[signup]", "실행확인");
        initProgress();
        
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

            binding = null;

    }
}
