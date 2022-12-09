package kr.co.project.view.sign.signup;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import kr.co.project.R;
import kr.co.project.api.RetrofitAPI;
import kr.co.project.config.RetrofitConfig;
import kr.co.project.databinding.FragmentThirdBinding;
import kr.co.project.main.BaseFragment;
import kr.co.project.vo.Maybe;
import kr.co.project.vo.MembSign;
import kr.co.project.vo.SignLiveModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment3 extends BaseFragment {
    private static String TAG = SignUpFragment3.class.getSimpleName();
    private boolean validate = false;
    private  @NonNull FragmentThirdBinding binding;
    private Maybe maybe;
    private SignLiveModel signLiveModel;
    private MembSign membSign;
    private int chekcName = 0;
    private RetrofitConfig retrofitConfig;
    private RetrofitAPI retrofitAPI;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =  FragmentThirdBinding.inflate(inflater, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        binding.btnSignIdChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrofitAPI.IdChk(String.valueOf(binding.etSignId.getText())).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                        Log.i(TAG, call+"call");
                        Log.i(TAG, response+ "response");
                if(!TextUtils.isEmpty(binding.etSignId.getText()) && response.body() == false) {
                    binding.btnSignIdChk.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_check_24, 0);
                    maybe.value.setValue(maybe.value.getValue().intValue()+1);
                    signLiveModel.getUserId().setValue(binding.etSignId.getText().toString());
                }
                else{
                    binding.btnSignIdChk.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.ic_baseline_cancel_24,0);
                }

                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Log.i(TAG, call+"call");
                        Log.i(TAG, t+ "t");
                    }
                });

//                if(!TextUtils.isEmpty(binding.etSignId.getText())) {
//                    binding.btnSignIdChk.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_check_24, 0);
//                    maybe.value.setValue(maybe.value.getValue().intValue()+1);
//                }
//                else{
//                    binding.btnSignIdChk.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.ic_baseline_cancel_24,0);
//                }
            }
        });
        setOnName();
        binding.etSignRepwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Toast.makeText(getContext(), binding.etSignRepwd.getText()+"이거 비밀번호", Toast.LENGTH_SHORT).show();
                Log.i(TAG, binding.etSignPwd.getText()+"입력한ㄹㅇㅁ 비밀번호");
                Log.i(TAG, binding.etSignRepwd.getText()+" 비밀번호");
                Log.i(TAG, s+"이거");
//                Toast.makeText(getContext(), s+"이거", Toast.LENGTH_SHORT).show();

                if(TextUtils.equals(s, binding.etSignPwd.getText())){
                    binding.imgSignPwdChk.setImageResource(R.drawable.ic_baseline_check_24);
                    maybe.value.setValue(maybe.value.getValue().intValue() + 1);
                    Log.i(TAG, maybe.value.getValue().intValue()+"");
                    signLiveModel.getUserPwd().setValue(binding.etSignPwd.getText().toString());
                }
                else if(!TextUtils.equals(s, binding.etSignPwd.getText())){
                    binding.imgSignPwdChk.setImageResource(R.drawable.ic_baseline_cancel_24);
                    if(maybe.value.getValue().intValue() != 0 && binding.etSignPwd.getText().length() != binding.etSignRepwd.getText().length()){
//                        maybe.value.setValue(maybe.value.getValue().intValue() - 1);

                      //  maybe.value.getValue().intValue();
                       if (maybe.value.getValue() > 1) maybe.value.setValue(maybe.value.getValue() -1);
                       else maybe.value.setValue(maybe.value.getValue());
                    }
                }
            }
        });

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        maybe = new ViewModelProvider(requireActivity()).get(Maybe.class);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signLiveModel = new ViewModelProvider(requireActivity()).get(SignLiveModel.class);
        membSign = new MembSign();
        retrofitConfig = RetrofitConfig.getInstance();
        retrofitAPI = retrofitConfig.getRetrofit();

    }

    public void setOnName(){
          binding.etSignName.addTextChangedListener(new TextWatcher() {
              @Override
              public void beforeTextChanged(CharSequence s, int start, int count, int after) {

              }

              @Override
              public void onTextChanged(CharSequence s, int start, int before, int count) {

              }

              @Override
              public void afterTextChanged(Editable s) {
                  if (!TextUtils.isEmpty(binding.etSignName.getText()) && binding.etSignName.getText().length() > 2) {
                      chekcName ++;
                      checkName(chekcName);
                  } else if (TextUtils.isEmpty(binding.etSignName.getText())&& binding.etSignName.getText().length() < 2) {
                      maybe.value.setValue(maybe.value.getValue().intValue() - 1);
                        chekcName = 0;
                  }
              }
          });

    }

    public void checkName(int a){
        if(a == 1 ){
            maybe.value.setValue(maybe.value.getValue().intValue() + 1);
            signLiveModel.getUserName().setValue(binding.etSignName.getText().toString());
//            membSign.setMembNm(binding.etSignName.getText().toString());
//            membSign.setMembId(binding.etSignId.getText().toString());
//            membSign.setMembPwd(binding.etSignPwd.getText().toString());
//            membSign.setMembStatusCd(10);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

































}
