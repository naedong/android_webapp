package kr.co.project.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;

import kr.co.project.R;
import kr.co.project.view.pay.MoneyChageFragment;
import kr.co.project.view.sign.util.PreferenceUtils;
import kr.co.project.view.sign.LoginFragments;
import kr.co.project.view.sign.SignUpFragment;
import kr.co.project.vo.MembLogin;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();


    private long backBtnTime = 0;
    private FragmentTransaction fragmentTransaction;
    private MembLogin connectIP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        PreferenceUtils.init(getApplicationContext());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            String packageName = this.getPackageName();
            WebView.setDataDirectorySuffix(packageName);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        connectIP = new ViewModelProvider(this).get(MembLogin.class);
        Log.i(TAG, getLocalIPAddress());
        connectIP.getConnectIp().setValue(getLocalIPAddress());

        Log.i(TAG, connectIP.getConnectIp().getValue()+"  IP valuchecking");
           setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate: "+PreferenceUtils.getUserId());

        if (TextUtils.equals(PreferenceUtils.getUserId(), "userId") || TextUtils.isEmpty(PreferenceUtils.getUserId()) ) {
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

                        PreferenceUtils.clearAll();
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
            case 3:
                fragmentTransaction.replace(R.id.main_frame, MoneyChageFragment.newInstance())
                        .commit();
                break;


        }
    }
    public static String getLocalIPAddress(){

        try {
            for(Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();){
                NetworkInterface intNetworkInterface = en.nextElement();
                for(Enumeration<InetAddress> enumIpAddr = intNetworkInterface.getInetAddresses(); enumIpAddr.hasMoreElements();){
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if(!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address){
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

}