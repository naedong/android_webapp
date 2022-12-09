package kr.co.project.view.sign;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import kr.co.project.R;
import kr.co.project.api.RetrofitAPI;
import kr.co.project.config.RetrofitConfig;
import kr.co.project.databinding.FragmentLoginBinding;
import kr.co.project.main.MainActivity;
import kr.co.project.service.MyFirebaseMessagingService;
import kr.co.project.view.sign.util.OnSwipeTouchListener;
import kr.co.project.view.sign.util.PreferenceUtils;

import kr.co.project.vo.MembLogin;
import kr.co.project.vo.MembSign;
import kr.co.project.vo.SignLiveModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragments extends Fragment {
    private static String TAG = LoginFragments.class.getSimpleName();
    private FragmentLoginBinding binding;
    private MembLogin.MembLogins membLogin;
    private static FirebaseDatabase firebaseDatabase;
    private static DatabaseReference userInfo;
    private  DatabaseReference chatRef;
    private NavHostFragment navHostFragment;
    private SignLiveModel signLiveModel;
    private RetrofitAPI retrofitAPI;
    private RetrofitConfig retrofitConfig;
    int count = 0;
    private long backBtnTime = 0;
    private MembLogin membLogins;
    public static LoginFragments newInstance(){
        LoginFragments lf = new LoginFragments();
        return lf;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.login);
//        navController = navHostFragment.getNavController();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        signLiveModel = new ViewModelProvider(getActivity()).get(SignLiveModel.class);


        if (user != null) {
            // do your stuff
            Log.d("FIREBASE 문제", "현재 접속하실 수 없습니다.");
        } else {
            mAuth.signInAnonymously();
        }

        oncheck();

        PreferenceUtils.init(getActivity().getApplicationContext());
        PreferenceUtils.setConnected(true);
        membLogins = new ViewModelProvider(getActivity()).get(MembLogin.class);
        retrofitConfig = RetrofitConfig.getInstance();
        retrofitAPI = retrofitConfig.getRetrofit();
    }

    private void oncheck() {
        firebaseDatabase= FirebaseDatabase.getInstance();

    }

    @SuppressLint({"ClickableViewAccessibility", "SuspiciousIndentation"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         binding = FragmentLoginBinding.inflate(inflater, container, false);
         Log.i("[LoginFragment]", "실행확인");


        binding.imgSign.setOnTouchListener(new OnSwipeTouchListener(getContext().getApplicationContext()){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return super.onTouch(v, event);
            }

            @Override
            public void onSwipeRight() {
                if (count == 0) {
                    binding.imgSign.setImageResource(R.drawable.night);
                    binding.txSign.setText("E4net");
                    count = 1;
                } else {
                    binding.imgSign.setImageResource(R.drawable.ship);
                    binding.txSign.setText("Project");
                    count = 0;
                }
            }

            @Override
            public void onSwipeLeft() {
                if (count == 0) {
                    binding.imgSign.setImageResource(R.drawable.night);
                    binding.txSign.setText("E4net");
                    count = 1;
                } else {
                    binding.imgSign.setImageResource(R.drawable.ship);
                    binding.txSign.setText("Project");
                    count = 0;
                }
            }

            @Override
            public void onSwipeTop() {

                super.onSwipeTop();
                ((MainActivity)getActivity()).ChangeFragment(3);

            }

            @Override
            public void onSwipeBottom() {
                super.onSwipeBottom();
            }
        });
        getLogin();

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).ChangeFragment(2);
            }
        });


        return binding.getRoot();
    }

    private void onAuto(String loginId) {
        binding.cAutoLogin.setOnClickListener(v -> {
            if(((CheckBox)v).isChecked()){
                PreferenceUtils.setUserId(membLogin.getMembId());
                PreferenceUtils.setConnected(false);
            }
            else{
                SignLiveModel signLiveModel = new ViewModelProvider(getActivity()).get(SignLiveModel.class);
                signLiveModel.getUserId().setValue(loginId);
            }
        });

    }


    public void getLogin(){
        binding.btnSignin.setOnClickListener(v -> {

            Log.i(TAG, binding.etId.getText().toString()+"id값");
            membLogin= new MembLogin.MembLogins();
            membLogin.setMembId(binding.etId.getText().toString());
            membLogin.setMembPwd(binding.etPwd.getText().toString());
            membLogin.setIp(membLogins.getConnectIp().getValue());

            Log.i(TAG, ""+membLogin.getIp());
            Log.i(TAG, ""+membLogin.getMembId());
            retrofitAPI.webLogin(membLogin).enqueue(new Callback<MembSign>() {
                @Override
                public void onResponse(Call<MembSign> call, Response<MembSign> response) {
                        if (response.isSuccessful()){
                            if(response.body() != null){
                                Log.i(TAG, response.body()+"body 값");
                                onAuto(membLogin.getMembId());
                                onLogin(membLogin.getMembId());
//                                getNavController().navigate(R.id.action_loginFragments_to_moneyChageFragment);
                                ((MainActivity)getActivity()).ChangeFragment(3);
                            }

                        }
                }

                private void onLogin(String id) {

                    if(TextUtils.isEmpty(PreferenceUtils.getUserId())) {
                        MyFirebaseMessagingService myFirebaseMessagingService = new MyFirebaseMessagingService(id);
                        Log.e(TAG, "onLogin: 1" + PreferenceUtils.getToken());
                        Log.e(TAG, "onLogin: 1" + PreferenceUtils.getToken());
                        myFirebaseMessagingService.SendFCM(id+"님 환영합니다", "로그인 되었습니다");
                    }
                    else if(!TextUtils.isEmpty(PreferenceUtils.getUserId())){
                        MyFirebaseMessagingService myFirebaseMessagingService = new MyFirebaseMessagingService();
                        Log.e(TAG, "onLogin: 2"+PreferenceUtils.getToken() );

                        myFirebaseMessagingService.SendFCM(PreferenceUtils.getUserId(), "로그인 되었습니다");

                    }
                }

                @Override
                public void onFailure(Call<MembSign> call, Throwable t) {

                }
            });

        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.i("[LoginFragment]", "실행확인11");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

//    @NonNull
//    @Override
//    public NavController getNavController() {
//        if(navController == null){
//            try {
//                throw  new Exception("NavController");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        Log.i(TAG, "getNavController: navController 확인"+navController);
//
//        Log.i(TAG, "getNavController: navController 확인"+navHostFragment.getNavController());
//        return navController;
//    }
}
