package kr.co.project.view.pay.js;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.preference.Preference;

import kr.co.project.view.sign.util.PreferenceUtils;
import kr.co.project.vo.SignLiveModel;

public class JsInterface {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        Context context
                ;
        Activity activity
                ;
        WebView webView;


        public JsInterface(Context context, WebView webView){
                this.context = context;
                this.webView = webView;
                this.activity = (Activity) context;
        }

        @JavascriptInterface
        public void appFunction(String msg){
                String id = msg;
                Toast.makeText(context, "",Toast.LENGTH_LONG).show();


                        //
//                        activity.runOnUiThread(() -> {
//                                webView.loadUrl("javascript:appInterface()");
//                        });
//                }

        }


//        @JavascriptInterface
//        public void appFunction(String msg){
//                Toast.makeText(context, "in app"+msg, Toast.LENGTH_LONG).show();
//
//                activity.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                                webView.loadUrl("javascript:jsFunction('app msg')");
//                        }
//                });
//        }
//        @JavascriptInterface
//        public void fcm(String msg){
//                Toast.makeText(context, "in app"+msg, Toast.LENGTH_LONG).show();
//                sharedPreferences = context.getSharedPreferences("etaa", Context.MODE_MULTI_PROCESS);
//                Toast.makeText(activity.getApplicationContext(), sharedPreferences.getString("fdm", ""), Toast.LENGTH_SHORT).show();
//                editor = sharedPreferences.edit();
//                editor.putString("fdm", "");
//                editor.apply();
//        }
//        @JavascriptInterface
//        public void on(String msg){
//
//                sharedPreferences = context.getSharedPreferences("etaa", Context.MODE_MULTI_PROCESS);
//
//
//                Toast.makeText(activity.getApplicationContext(), sharedPreferences.getString("et", ""), Toast.LENGTH_SHORT).show();
//                editor = sharedPreferences.edit();
//                editor.putString("et", msg);
//                editor.apply();
//                activity.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                                webView.loadUrl("javascript:on('app msg')");
//                        }
//
//                });
//        }@JavascriptInterface
//        public void off(String msg){
//                Toast.makeText(context, "in app"+msg, Toast.LENGTH_LONG).show();
//                sharedPreferences = context.getSharedPreferences("etaa", Context.MODE_MULTI_PROCESS);
//
//
//                Toast.makeText(activity.getApplicationContext(), sharedPreferences.getString("et", ""), Toast.LENGTH_SHORT).show();
//                editor = sharedPreferences.edit();
//                editor.putString("et", "");
//                editor.apply();
//                activity.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                                webView.loadUrl("javascript:off('app msg')");
//                        }
//                });
//        }
}
