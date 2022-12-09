package kr.co.project.view.pay;

import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.webkit.CookieManager;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import kr.co.project.R;
import kr.co.project.adapter.BookPageAdapter;
import kr.co.project.databinding.FragmentMoneychargeBinding;
import kr.co.project.databinding.FragmentSignBinding;
import kr.co.project.main.BaseFragment;
import kr.co.project.view.pay.js.JsInterface;
import kr.co.project.view.sign.LoginFragments;

public class MoneyChageFragment extends Fragment {
    private static String TAG = MoneyChageFragment.class.getSimpleName();
    private FragmentMoneychargeBinding binding;
    private View view1;
    private  ImageView imageView;
    private Animation set, rotate;
    private TranslateAnimation anim;
    private  Dialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: 실행확인");
    }


    @Nullable
    @Override
    @SuppressLint("SetJavaScriptEnabled")
    @SuppressWarnings("deprecation")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMoneychargeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Log.i(TAG, "onCreateView: ");
        ani();
        return view;
    }

    private void ani() {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view1 = layoutInflater.inflate(R.layout.loading, binding.frameLayout, true);
        imageView = view1.findViewById(R.id.spin_kit);
        set = new AnimationSet(true);
        anim = new TranslateAnimation(0,0,0,-50);
        rotate = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);

        anim.setDuration(1000);
        dialog = new Dialog(getContext());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.loading);
        dialog.setCancelable(false);
        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(rotate);
    }



    @SuppressLint("JavascriptInterface")
    private void initWebView(Bundle bundle) {
        Log.i(TAG, "initWebView: ");
        CookieManager cookieManager = CookieManager.getInstance();

//        cookieManager.flush();
//        binding.webView.clearCache(true);
//        binding.webView.clearHistory();
//        binding.webView.setBackgroundColor(0x00000000);
//        binding.webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
//        binding.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        binding.webView.getSettings().setDatabaseEnabled(true);
//        binding.webView.getSettings().setDomStorageEnabled(true);
//        binding.webView.getSettings().setDefaultTextEncodingName("UTF-8");
//        binding.webView.getSettings().setAllowContentAccess(true);
//        binding.webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        binding.webView.setWebChromeClient(new WebChromeClient());
//        binding.webView.getSettings().setDomStorageEnabled(true);

        binding.webView.setWebViewClient(new MyBrowser());
        WebSettings webSettings = binding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);

        cookieManager.removeSessionCookies(new ValueCallback<Boolean>() {
            @Override
            public void onReceiveValue(Boolean value) {

                Log.i(TAG, "onReceiveValue: "+value);
            }
        });

        cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
            @Override
            public void onReceiveValue(Boolean value) {
                Log.i(TAG, "onReceiveValue: check"+value);
            }
        });


        if(bundle != null) {


            binding.webView.restoreState(bundle);
        }
            else {


            binding.webView.loadUrl("https://7328-221-148-126-58.jp.ngrok.io/charge");
            binding.webView.setVisibility(View.VISIBLE);
            binding.webView.addJavascriptInterface(new JsInterface(getContext(),binding.webView), "web");
        }
//        binding.webView.addJavascriptInterface(new JsInterface(this, binding.webView), "web");

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "onViewCreated: " );
        initWebView(savedInstanceState);
    }

//    private void initWebView(Bundle savedInstanceState)
//    {
//    binding.webView.setWebViewClient(new WebViewClient(){
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//            Log.d("[webview]", "shouldOverrideUrlLoadIng"+request.getUrl());
//            Log.d("[webview]", "shouldOverrideUrlLoadIng"+request);
//
//            return super.shouldOverrideUrlLoading(view, request);
//        }
//
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            Log.d("[webview]", "shouldOverrideUrlLoadIng"+url);
//            super.onPageStarted(view, url, favicon);
//
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            Log.d("[webview]", "shouldOverrideUrlLoadIng"+url);
//            Log.d("[webview]", "shouldOverrideUrlLoadIng"+url);
//            super.onPageFinished(view, url);
//        }
//
//    });
//        binding.webView.setWebChromeClient(new WebChromeClient());
//
//
//        binding.webView.getSettings().setSupportZoom(true);  // 줌 설정 여부
//        binding.webView.getSettings().setBuiltInZoomControls(true);  // 줌 확대/축소 버튼 여부
//
//        WebSettings webSettings = binding.webView.getSettings();// 자바스크립트 사용여부
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setLoadWithOverviewMode(true);
////        webview.addJavascriptInterface(new AndroidBridge(), "android");
////        binding.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true); // javascript가 window.open()을 사용할 수 있도록 설정
////        binding.webView.getSettings().setSupportMultipleWindows(true); // 멀티 윈도우 사용 여부
//
////        binding.webView.getSettings().setDomStorageEnabled(true);
//        binding.webView.loadUrl("https://0b81-118-36-251-47.jp.ngrok.io/");
//    }

    @Override
    public void onResume() {
        super.onResume();
        setReenterTransition(true);
        Log.e(TAG, "onResume: " );
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: " );
        super.onDestroy();
        binding.webView.destroy();
    }

    public static MoneyChageFragment newInstance(){
        Log.e(TAG, "newInstance: ");
        MoneyChageFragment moneyChageFragment = null;
        if(moneyChageFragment ==null )moneyChageFragment = new MoneyChageFragment();
        return moneyChageFragment;
    }

    @Override
    public void onDestroyView()
    {
        Log.e(TAG, "onDestroyView: " );
        super.onDestroyView();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: " );
    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Log.d("[webview]", "shouldOverrideUrlLoadIng"+request.getUrl());
            Log.d("[webview]", "shouldOverrideUrlLoadIng"+request);

            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d("[webview]", "shouldOverrideUrlLoadIng"+url);
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.d("[webview]", "shouldOverrideUrlLoadIng"+url);
            Log.d("[webview]", "shouldOverrideUrlLoadIng"+url);
            super.onPageFinished(view, url);
            dialog.dismiss();
            binding.frameLayout.setVisibility(View.GONE);
        }


        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//            handler.proceed();
            Log.e(TAG, "onReceivedSslError: "+error );

            Log.e(TAG, "onReceivedSslError: "+view.getSettings() );
            Log.e(TAG, "onReceivedSslError: "+handler );
        }

    }


}
