package kr.co.project.view.sign;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kr.co.project.R;
import kr.co.project.main.MainActivity;
import kr.co.project.util.OnSwipeTouchListener;

public class LoginFragments extends Fragment {
    private ImageView imgSign;
    private TextView txSign;
    private AlertDialog.Builder builder;
    private EditText etId;
    private EditText etPwd;
    private Button btnSignUp;
    private Button btnSignIn;


    int count = 0;
    private long backBtnTime = 0;


    public static LoginFragments newInstance(){
        LoginFragments lf = new LoginFragments();
        return lf;
    }
    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        imgSign = view.findViewById(R.id.img_sign);
        txSign = view.findViewById(R.id.tx_sign);
        etId = view.findViewById(R.id.et_id);
        etPwd = view.findViewById(R.id.et_pwd);
        btnSignIn = view.findViewById(R.id.btn_signin);
        btnSignUp = view.findViewById(R.id.btn_signup);

        Log.i("[LoginFragment]", "실행확인");
        imgSign.setOnTouchListener(new OnSwipeTouchListener(getContext().getApplicationContext()){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return super.onTouch(v, event);
            }

            @Override
            public void onSwipeRight() {
                if (count == 0) {
                    imgSign.setImageResource(R.drawable.night);
                    txSign.setText("E4net");
                    count = 1;
                } else {
                    imgSign.setImageResource(R.drawable.ship);
                    txSign.setText("Project");
                    count = 0;
                }
            }

            @Override
            public void onSwipeLeft() {
                if (count == 0) {
                    imgSign.setImageResource(R.drawable.night);
                    txSign.setText("E4net");
                    count = 1;
                } else {
                    imgSign.setImageResource(R.drawable.ship);
                    txSign.setText("Project");
                    count = 0;
                }
            }

            @Override
            public void onSwipeTop() {
                super.onSwipeTop();
            }

            @Override
            public void onSwipeBottom() {
                super.onSwipeBottom();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).ChangeFragment(2);
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.i("[LoginFragment]", "실행확인11");
        super.onViewCreated(view, savedInstanceState);
    }
}
