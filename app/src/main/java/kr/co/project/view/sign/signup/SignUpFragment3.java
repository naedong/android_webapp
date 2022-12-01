package kr.co.project.view.sign.signup;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kr.co.project.R;
import kr.co.project.main.BaseFragment;
import kr.co.project.view.sign.SignUpFragment;
import kr.co.project.vo.Maybe;

public class SignUpFragment3 extends BaseFragment {
    private EditText et_sign_name, et_sign_id, et_sign_pwd, et_sign_repwd;
    private boolean validate = false;
    private ImageView img_sign_pwd_chk;
    private Button btn_sign_id_chk;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        btn_sign_id_chk = view.findViewById(R.id.btn_sign_id_chk);
        et_sign_name = view.findViewById(R.id.et_sign_name);
        et_sign_id = view.findViewById(R.id.et_sign_id);


        et_sign_pwd = view.findViewById(R.id.et_sign_pwd);
        et_sign_repwd = view.findViewById(R.id.et_sign_repwd);
        img_sign_pwd_chk = view.findViewById(R.id.img_sign_pwd_chk);

        btn_sign_id_chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(et_sign_id.getText())) {
                    btn_sign_id_chk.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_check_24, 0);
                    Maybe.value ++;
                    Log.i("sign3", "maybe value"+Maybe.value);
                   }
                else{
                    btn_sign_id_chk.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.ic_baseline_cancel_24,0);
                }
            }
        });

        et_sign_repwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Toast.makeText(getContext(), et_sign_repwd.getText()+"이거 비밀번호", Toast.LENGTH_SHORT).show();
                Log.i("[SignUp3]", et_sign_pwd.getText()+"입력한ㄹㅇㅁ 비밀번호");
                Log.i("[SignUp3]", et_sign_repwd.getText()+"이거 비밀번호");
                Log.i("[SignUp3]", s+"이거");
//                Toast.makeText(getContext(), s+"이거", Toast.LENGTH_SHORT).show();

                if(TextUtils.equals(s, et_sign_pwd.getText())){
                    img_sign_pwd_chk.setImageResource(R.drawable.ic_baseline_check_24);

                }
                else if(!TextUtils.equals(s, et_sign_pwd.getText())){
                    img_sign_pwd_chk.setImageResource(R.drawable.ic_baseline_cancel_24);
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
