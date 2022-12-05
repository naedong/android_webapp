package kr.co.project.view.sign.signup;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import kr.co.project.R;
import kr.co.project.api.RetrofitAPI;
import kr.co.project.config.RetrofitConfig;
import kr.co.project.databinding.FragmentFirstBinding;
import kr.co.project.view.sign.SignUpFragment;
import kr.co.project.view.sign.signup.address.AddressFragment;
import kr.co.project.vo.AddressData;
import kr.co.project.vo.Maybe;
import kr.co.project.vo.MembSign;
import kr.co.project.vo.SignDTO;
import kr.co.project.vo.SignLiveModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment1 extends Fragment {

    private static String TAG = SignUpFragment1.class.getSimpleName();
    private FragmentFirstBinding binding;
    private Maybe maybe;
    private int count = 0;
    private ArrayAdapter<CharSequence> adapter ;
    private ArrayAdapter<CharSequence> emailadapter ;
    private String email, phone, verification;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private RetrofitConfig retrofitConfig;
    private RetrofitAPI retrofitAPI;
    private SignDTO signDTO;
    private MembSign membSign;
    private SignLiveModel signLiveModel;
    private Toast tast;
    private AddressData addressData;
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        maybe = new ViewModelProvider(requireActivity()).get(Maybe.class);
        addressData = new ViewModelProvider(requireActivity()).get(AddressData.class);
        membSign = new MembSign();
        signLiveModel = new ViewModelProvider(requireActivity()).get(SignLiveModel.class);
        signDTO = new SignDTO();
        retrofitConfig = RetrofitConfig.getInstance();
        retrofitAPI = retrofitConfig.getRetrofit();
        addressData.zipCd.setValue(0);
        addressData.fulladdress.setValue("");
        adapter = ArrayAdapter.createFromResource(getContext(),R.array.number_array,R.layout.spinner_item);
        emailadapter = ArrayAdapter.createFromResource(getContext(), R.array.email_array , R.layout.spinner_item);
        adapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
        emailadapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        retrofitConfig = RetrofitConfig.getInstance();
        retrofitAPI = retrofitConfig.getRetrofit();

//      ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.number_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        binding.siSignEEmail.setAdapter(emailadapter);
        binding.siSignEEmail.setSelection(0);

        binding.siSignPhone.setAdapter(adapter);
        binding.siSignPhone.setSelection(0);
        binding.siSignPhone.setPrompt("전화번호를 선택하시오");
        setOnItem();
        setCheckAddress();
        setAddress();

        Log.i(TAG, addressData.zipCd.getValue().intValue()+"값화깅ㄴ");

        Log.i(TAG, addressData.fulladdress.getValue()+"값화깅ㄴ");
        addressData.zipCd.observe(getViewLifecycleOwner(), Integer ->  {
            initZipCd();
        });
        addressData.fulladdress.observe(getViewLifecycleOwner(), String ->  {
            initFullAddress();
        });

        binding.etSignCEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(binding.siSignEEmail.getSelectedItemPosition() == 0){
                        onCheck();
                }
            }
        });


        binding.etSignEPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                phone = binding.siSignPhone.getSelectedItem().toString()+binding.etSignCPhone.getText()+binding.etSignEPhone.getText();
                Log.i(TAG, phone+"phone");
                signDTO.setCertificationNumber(null);
                signDTO.setPhone(phone);
                signLiveModel.getMobile().setValue(phone);
            }
        });
        onPhoneCheck();
        onPhoneSign();


        return binding.getRoot();
    }

    private void onPhoneSign() {
        verification = binding.etSigbCertificationNumber.getText().toString();
    }

    private void onPhoneCheck() {

        binding.btnSignPhone.setOnClickListener(view -> {

            if (count > 0) {
                retrofitAPI.SmsVerification(signDTO).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful()){
                            if(response.body() == true){
                                if(!TextUtils.isEmpty(phone) && phone.length() == 11 && binding.etSigbCertificationNumber.getText(). length() == 6){

                                    binding.btnSignPhone.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_check_24, 0);
                                    maybe.value.setValue(maybe.value.getValue() + 1);

                                    LayoutInflater inflater1 = getLayoutInflater();
                                    View layout = inflater1.inflate(R.layout.toast_board, (ViewGroup) view.findViewById(R.id.toast_layout));
                                    TextView textView = layout.findViewById(R.id.tx_toast_board);
                                    textView.setText("인증에 성공하였습니다.");

                                    tast = Toast.makeText(getContext().getApplicationContext(), "인증에 성공하였습니다.", Toast.LENGTH_LONG);
                                    tast.setGravity(Gravity.CENTER,0,0);
                                    tast.setView(layout);
                                    tast.show();


                                }

                            }

                            else {
                                binding.btnSignPhone.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_cancel_24, 0);
                                Log.i(TAG, "인증 실패" + response.body());
                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
                });
            }

            retrofitAPI.sendSms(signDTO).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.isSuccessful()) {
                        Log.i(TAG, response + "리스폰스 체킹" + response.body());
                        if(response.body() == true) {
                            count++;

                        }
                        else {
                            binding.btnSignPhone.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_cancel_24, 0);
                            Log.i(TAG, "인증 실패" + response.body());
                        }
                    }


                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.e(TAG, t.getLocalizedMessage());
                    // 되는 거 확인 후 막아놓음
                    // 무료 요금제 종료
                    Log.e(TAG, t.getMessage()+"에러 메세지");

                    Log.e(TAG, t.getLocalizedMessage());
                }
            });
        });
    }


    private void setLive() {

        addressData.zipCd.observe(getViewLifecycleOwner(), Integer ->  {
            initZipCd();
        });
        addressData.fulladdress.observe(getViewLifecycleOwner(), String ->  {
            initFullAddress();
        });


    }

    private void initZipCd() {
        binding.etSignZip.setText(
                addressData.zipCd.getValue().intValue()+"");
        signLiveModel.getZip().setValue(binding.etSignZip.getText().toString());
    }

    private void initFullAddress() {

        binding.etSignAddress.setText(
                addressData.fulladdress.getValue()
        );

        if(!TextUtils.isEmpty(binding.etSignZip.getText()) && !TextUtils.isEmpty(binding.etSignAddress.getText())){
            maybe.value.setValue(maybe.value.getValue().intValue() + 1);

            signLiveModel.getZipaddr().setValue(addressData.fulladdress.getValue());
            signLiveModel.getDetaild().setValue(binding.etSignDetailAddress.getText().toString());
        }
    }

    private void setCheckAddress() {
//        addressData.zipCd.observe(getViewLifecycleOwner(), String ->  {
//            setZip();
//        });
//        addressData.fulladdress.observe(getViewLifecycleOwner(), String ->  {
//            setFullAddress();
//        });

    }

//    private void setFullAddress() {
//        binding.etSignAddress.setText(addressData.fulladdress.getValue());
//    }
//
//    private void setZip() {
//
//        binding.etSignZip.setText(addressData.zipCd.getValue());
//    }

    private void setAddress() {
        binding.btnSignAddress.setOnClickListener(v -> {



            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.main_frame , AddressFragment.newInstance());
            ft.addToBackStack(null);
            ft.commit();
 //           navController.navigate(R.id.action_signUpFragment12_to_addressFragment2);

        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setAddress();

    }







    private void setOnItem(){
        binding.siSignPhone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                binding.siSignPhone.setSelection(position);
                Log.i(TAG, "셀렉트값 확인 "+position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                binding.siSignPhone.setSelection(0);

            }
        });


        binding.siSignEEmail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                binding.siSignEEmail.setSelection(position);
                binding.etSignCEmail.setText(binding.siSignEEmail.getSelectedItem().toString());
                Log.i(TAG, position+"내용2"+binding.siSignEEmail.getSelectedItem().toString());
                onCheck();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                binding.siSignEEmail.setSelection(0);

            }
        });


    }


    public void onCheck(){
        email = binding.etSignEmail.getText()+"@"+binding.etSignCEmail.getText();
        Log.i(TAG, email+"email");
        if(!TextUtils.isEmpty(email) && email.contains("@naver.com") | email.contains("@daum.net") | email.contains("@google.com")){
            maybe.value.setValue(maybe.value.getValue() + 1);
        }
        signLiveModel.getEmail().setValue(email);

//        signLiveModel.get
        Log.i(TAG, binding.etSignZip.getText()+"");


    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }




}
