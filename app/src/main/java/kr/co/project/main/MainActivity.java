package kr.co.project.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import kr.co.project.R;
import kr.co.project.util.PreferenceUtils;
import kr.co.project.view.sign.LoginFragments;
import kr.co.project.view.sign.SignUpFragment;

public class MainActivity extends AppCompatActivity {


    private ViewPager2 mPager;
    private int num_page = 3;
    private FragmentStateAdapter pageAdapter;
    private long backBtnTime = 0;
    private FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);



        PreferenceUtils.init(getApplicationContext());
        PreferenceUtils.setConnected(true);



        Log.i("[Main Activity]", "실행확인d");

        if (TextUtils.isEmpty(PreferenceUtils.getUserId())) {
            Log.i("[Main Activity]", "실행확인");
            ChangeFragment(1);
        }

    }




    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;
        AlertDialog.Builder build = new AlertDialog.Builder(this);

        build.setTitle("종료")
                .setCancelable(false)
                .setMessage("종료하시겠습니까?")
                .setIcon(R.drawable.open_graph_logo)
                .setNegativeButton("종료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.finishAffinity(MainActivity.this);
                        System.exit(0);
                    }
                })
                .setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                }).create()
                .show();
    }

    public void ChangeFragment(int index){
        FragmentManager manager = getSupportFragmentManager();
        fragmentTransaction = manager.beginTransaction();

        switch (index){
            case 1:
                fragmentTransaction.replace(R.id.main_frame, LoginFragments.newInstance())
                        .commit();
                break;
            case 2:
                fragmentTransaction.replace(R.id.main_frame, SignUpFragment.newInstance())
                        .commit();
                break;


        }
    }


}